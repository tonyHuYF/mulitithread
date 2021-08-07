package com.tony.mulitithread.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tony.mulitithread.domain.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {

    List<FileInfo> queryList(@Param("start") Integer start, @Param("num") Integer num);
}
