package kr.co.fittnerserver.service.file;

import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.common.FileDto;
import kr.co.fittnerserver.dto.file.response.FileResDto;
import kr.co.fittnerserver.mapper.common.CommonMapper;
import kr.co.fittnerserver.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.uploadPath}")
    private String uploadPath;

    @Value("${file.url}")
    private String fileUrl;

    final FileMapper fileMapper;

    final CommonMapper commonMapper;

    @Transactional
    public List<FileResDto> uploadImage(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

        //응답부
        List<FileResDto> fileResponseDtoList = new ArrayList<>();

        //허용 파일
        List<String> allowFileType = Arrays.asList("gif", "png", "jpeg", "bmp", "pdf", "html");

        //파라미터 이름을 키로 파라미터에 해당하는 파일 정보를 값으로 하는 Map
        Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();

        //files.entrySet()의 요소
        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

        MultipartFile mFile;

        //파일이 업로드 될 경로를 지정
        String filePath = uploadPath;

        //파일명이 중복되었을 경우, 사용할 스트링 객체
        String saveFileName = "";
        String savaFilePath = "";

        //그룹 테이블 선 저장
        String fileGroupId = commonMapper.selectUUID();
        fileMapper.insertFileGroup(fileGroupId);

        //읽어 올 요소가 있으면 true, 없으면 false를 반환
        while (itr.hasNext()) {

            Map.Entry<String, MultipartFile> entry = itr.next();

            //entry에 값을 가져온다.
            mFile = entry.getValue();

            //실제 파일명
            String fileName = mFile.getOriginalFilename();

            //키로 저장될 파일ID(문자숫자 포함 랜덤 문자열 8자리)
            String fileId = RandomStringUtils.randomAlphanumeric(10);

            //확장자
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

            boolean allowFileCheck = allowFileType.contains(fileExt.toLowerCase());

            if(!allowFileCheck) {
                throw new CommonException(CommonErrorCode.NOT_ALLOW_FILE.getCode(),CommonErrorCode.NOT_ALLOW_FILE.getMessage()); //허용되지 않은 확장자입니다.
            }

            //저장될 경로와 파일명
            String saveFilePath = filePath + File.separator + fileName;

            //filePath에 해당되는 파일의 File 객체를 생성
            File fileFolder = new File(filePath);

            if (!fileFolder.exists()) {
                //부모 폴더까지 포함하여 경로에 폴더 생성
                if (fileFolder.mkdirs()) {
                    log.info("[file.mkdirs] Success ::: {}",filePath);
                } else {
                    log.error("[file.mkdirs] Fail ::: {}",filePath);
                }
            }

            //중복여부
            File saveFile = new File(saveFilePath);

            //saveFile이 File이면 true, 아니면 false
            //파일명이 중복일 경우 파일명(1).확장자, 파일명(2).확장자 와 같은 형태로 생성
            if (saveFile.isFile()) {
                boolean _exist = true;

                int index = 0;

                // 동일한 파일명이 존재하지 않을때까지 반복
                while (_exist) {
                    index++;

                    saveFileName = fileName.substring(0,fileName.lastIndexOf(".")) + "(" + index + ")." + fileExt;

                    String dictFile = filePath + File.separator + saveFileName;

                    _exist = new File(dictFile).isFile();

                    if (!_exist) {
                        savaFilePath = dictFile;
                    }
                }

                //생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서드를 이용해서 업로드처리
                mFile.transferTo(new File(savaFilePath));

                //DB저장
                FileDto fileDto = new FileDto();
                fileDto.setFileId(fileId);
                fileDto.setFileGroupId(fileGroupId);
                fileDto.setFileUrl(fileUrl+File.separator+fileId);
                fileDto.setFileName(saveFileName);
                fileDto.setFilePath(filePath);
                fileMapper.insertFile(fileDto);

                //응답부 추가
                FileResDto fileResDto = new FileResDto();
                fileResDto.setFileId(fileId);
                fileResDto.setGroupFileId(fileGroupId);
                fileResDto.setFileName(saveFileName);
                fileResDto.setFilePath(filePath);
                fileResDto.setFileUrl(fileUrl+File.separator+fileId);
                fileResponseDtoList.add(fileResDto);

            } else {
                //생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서드를 이용해서 업로드처리
                mFile.transferTo(saveFile);

                //DB저장
                FileDto fileDto = new FileDto();
                fileDto.setFileId(fileId);
                fileDto.setFileGroupId(fileGroupId);
                fileDto.setFileUrl(fileUrl+File.separator+fileId);
                fileDto.setFileName(saveFileName);
                fileDto.setFilePath(filePath);
                fileMapper.insertFile(fileDto);

                //응답부 추가
                FileResDto fileResDto = new FileResDto();
                fileResDto.setFileId(fileId);
                fileResDto.setGroupFileId(fileGroupId);
                fileResDto.setFileName(fileName);
                fileResDto.setFilePath(filePath);
                fileResDto.setFileUrl(fileUrl+File.separator+fileId);
                fileResponseDtoList.add(fileResDto);
            }
        }
        return fileResponseDtoList;
    }

    public ResponseEntity<byte[]> showImage(String fileId) throws Exception, IOException {

        kr.co.fittnerserver.entity.common.File fileInfo =  fileMapper.selectFile(fileId);

        File file = new File(fileInfo.getFilePath()+File.separator+fileInfo.getFileName());

        if(!file.isFile()){
            throw new Exception();
        }

        ResponseEntity<byte[]> result = null;

        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", Files.probeContentType(file.toPath()));
        result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        return result;
    }

    public ResponseEntity<byte[]> getPrivacyClause() throws Exception{

        kr.co.fittnerserver.entity.common.File fileInfo = fileMapper.selectClauseByClauseKind("00");

        File file = new File(fileInfo.getFilePath()+File.separator+fileInfo.getFileName());

        if(!file.isFile()){
            throw new Exception();
        }

        ResponseEntity<byte[]> result = null;

        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", Files.probeContentType(file.toPath()));
        result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        return result;
    }

}
