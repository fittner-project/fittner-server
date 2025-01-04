package kr.co.fittnerserver.mapper.common;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
    String selectUUID();
    String reservationId();
}
