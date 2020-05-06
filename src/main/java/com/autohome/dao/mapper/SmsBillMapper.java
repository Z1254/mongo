package com.autohome.dao.mapper;

import com.autohome.dao.domain.SmsBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zzn
 * @date 2020/5/5 1:20
 */
@Mapper
public interface SmsBillMapper {

    int insert(@Param("list") List<SmsBill> list);
}
