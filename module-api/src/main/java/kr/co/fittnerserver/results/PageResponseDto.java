package kr.co.fittnerserver.results;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> items;
    private PaginationInfo paginationInfo;
}
