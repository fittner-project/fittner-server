package kr.co.fittnerserver.format;


import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.SQLException;

@Component
@Slf4j
public class QueryFormat extends JdbcEventListener implements MessageFormattingStrategy {
    @Override // 2
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(getClass().getName());
    }

    @Override // 3
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(category).append(" ").append(elapsed).append("ms");
        if(sql.startsWith("/*")){
            sb.append("\n"); //mybatis 한줄띄움
        }
        if (StringUtils.hasText(sql)) {
            sb.append(highlight(format(sql)));
        }
        return sb.toString();
    }

    private String format(String sql) {
        if (isDDL(sql)) {
            return FormatStyle.DDL.getFormatter().format(sql);
        } else if (isBasic(sql)) {
            return FormatStyle.BASIC.getFormatter().format(sql);
        }
        return sql;
    }

    private String highlight(String sql) {
        //로그파일에 ANSI 색상 코드가 같이 저장되므로 임시 주석
        //return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
        return sql;
    }

    private boolean isDDL(String sql) {
        return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment");
    }

    private boolean isBasic(String sql) {
        return sql.startsWith("select") || sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete");
    }
}
