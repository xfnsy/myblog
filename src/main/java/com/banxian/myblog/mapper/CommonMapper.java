package com.banxian.myblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 通用mapper
 *
 * @author wangpeng
 * @datetime 2020/12/11 16:18
 */
public interface CommonMapper {

    @Select("show tables")
    List<String> showTables();

    @Select("select IFNULL(max(${idName}),0) from ${tableName}")
    int selectMaxId(@Param("tableName")String tableName, @Param("idName")String idName);

}
