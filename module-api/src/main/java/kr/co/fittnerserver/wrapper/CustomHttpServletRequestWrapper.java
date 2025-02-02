package kr.co.fittnerserver.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String body;
    private final boolean isMultipart;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        //multipart/form-data 요청이면 필터에서 처리하지 않음
        this.isMultipart = request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart/");

        if (this.isMultipart) {
            this.body = null; // multipart 요청은 body를 저장하지 않음
            return;
        }

        //application/x-www-form-urlencoded 요청 처리
        if ("application/x-www-form-urlencoded".equalsIgnoreCase(request.getContentType())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            StringBuilder stringBuilder = new StringBuilder();
            parameterMap.forEach((key, values) -> {
                for (String value : values) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(key).append("=").append(value);
                }
            });
            this.body = stringBuilder.toString();
        } else {
            //일반 body 요청 처리
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
            this.body = stringBuilder.toString();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isMultipart) {
            return super.getInputStream(); // multipart 요청은 기존 InputStream 그대로 사용
        }

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(jakarta.servlet.ReadListener readListener) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (isMultipart) {
            return super.getReader(); //multipart 요청은 기존 Reader 그대로 사용
        }
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

    public String getBody() {
        return this.body;
    }
}
