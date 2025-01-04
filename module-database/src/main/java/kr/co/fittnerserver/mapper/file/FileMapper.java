package kr.co.fittnerserver.mapper.file;

import kr.co.fittnerserver.domain.common.FileDto;
import kr.co.fittnerserver.entity.common.File;
import kr.co.fittnerserver.entity.common.FileGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void insertFileGroup(String fileGroupId);
    void insertFile(FileDto fileDto);
    File selectFile(String fileId);
    FileGroup selectFileGroup(String fileGroupId);
    void deleteFileGroup(String fileGroupId);
    void updateFile(String fileId, String fileGroupId);
    File selectClauseByClauseKind(String clauseKind);
}
