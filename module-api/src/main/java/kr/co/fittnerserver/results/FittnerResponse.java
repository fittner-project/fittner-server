package kr.co.fittnerserver.results;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter
public class FittnerResponse<T> {

    public static <T> ResponseEntity<ApiResponseMessage<T>> build(T resultObject) {
        return Result.ok(new ApiResponseMessage<>(resultObject));
    }

    public static <T> ResponseEntity<ApiResponseMessage<List<T>>> buildList(List<T> resultList) {
        ApiResponseMessage<List<T>> responseMessage = new ApiResponseMessage<>(resultList);
        return Result.ok(responseMessage);
    }

    public static <T> ResponseEntity<ApiResponseMessage<PageResponseDto<T>>> buildPage(Page<T> page) {

        int firstPage = ((page.getNumber()) / page.getPageable().getPageSize()) * page.getPageable().getPageSize() + 1;
        int lastPage = firstPage + page.getPageable().getPageSize() - 1;
        if (lastPage > page.getTotalPages()) {
            lastPage = page.getTotalPages();
        }

        List<T> items = page.getContent();
        PaginationInfo paginationInfo = PaginationInfo.builder()
                .totalRecordCount(page.getTotalElements())
                .totalPageCount(page.getTotalPages())
                .recordsPerPage(page.getSize())
                .pageSize(page.getPageable().getPageSize())
                .firstPage(firstPage)
                .lastPage(lastPage)
                .startPage(page.isFirst())
                .endPage(page.isLast())
                .currentPageNo(page.getNumber() + 1)
                .empty(page.isEmpty())
                .build();

        PageResponseDto pageResponseDto = new PageResponseDto(items, paginationInfo);
//
        ApiResponseMessage<PageResponseDto<T>> responseMessage = new ApiResponseMessage<>(pageResponseDto);
        return Result.ok(responseMessage);
    }

    public static <T> ResponseEntity<ApiResponseMessage<PageResponseDto<T>>> buildPage(Page<T> page, FittnerPageable pageInfo) {

        int firstPage = ((page.getNumber()) / pageInfo.getPageSize()) * pageInfo.getPageSize() + 1;
        int lastPage = firstPage + pageInfo.getPageSize() - 1;
        if (lastPage > page.getTotalPages()) {
            lastPage = page.getTotalPages();
        }

        List<T> items = page.getContent();
        PaginationInfo paginationInfo = PaginationInfo.builder()
                .totalRecordCount(page.getTotalElements())
                .totalPageCount(page.getTotalPages())
                .recordsPerPage(page.getSize())
                .pageSize(pageInfo.getPageSize())
                .firstPage(firstPage)
                .lastPage(lastPage)
                .startPage(page.isFirst())
                .endPage(page.isLast())
                .currentPageNo(page.getNumber() + 1)
                .empty(page.isEmpty())
                .build();

        PageResponseDto pageResponseDto = new PageResponseDto(items, paginationInfo);
//
        ApiResponseMessage<PageResponseDto<T>> responseMessage = new ApiResponseMessage<>(pageResponseDto);
        return Result.ok(responseMessage);
    }

    public static ResponseEntity<ApiResponseMessage<Object>> ok() {
        return Result.ok(new ApiResponseMessage<>("SUCCESS", "정상처리", "", ""));
    }
}
