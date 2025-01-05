package kr.co.fittnerserver.repository.common;

import kr.co.fittnerserver.dto.user.user.response.CenterFileResDto;
import kr.co.fittnerserver.entity.common.File;
import kr.co.fittnerserver.entity.common.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {

    @Query(value = """
                    SELECT new kr.co.fittnerserver.dto.user.user.response.CenterFileResDto(f.fileUrl)
                    FROM File f
                    WHERE f.fileGroup = :fileGroup
                    AND f.fileDeleteYn = :fileDeleteYn
            """)
    List<CenterFileResDto> getFileUrl(@Param(value = "fileGroup") FileGroup fileGroup,
                                      @Param(value = "fileDeleteYn") String fileDeleteYn);
}
