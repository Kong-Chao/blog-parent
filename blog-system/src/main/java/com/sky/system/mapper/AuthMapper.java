package com.sky.system.mapper;

import com.sky.common.core.domain.entity.UserBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 */
@Mapper
public interface AuthMapper {

    UserBO selectByUserName(String username);

}
