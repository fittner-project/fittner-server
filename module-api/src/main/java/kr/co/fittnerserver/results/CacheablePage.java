package kr.co.fittnerserver.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 일반 Page를 redis 에서 cache가 가능하도록 CacheablePage로 변환
 * @param <T> Page 내용
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class CacheablePage<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CacheablePage(@JsonProperty("content") List<T> content,
                         @JsonProperty("number") int page,
                         @JsonProperty("size") int size,
                         @JsonProperty("totalElements") long total
    ) {
        super(content, PageRequest.of(page, size), total);
    }

    public CacheablePage(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}
