package com.sky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.common.core.domain.UserBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kc
 */
@Mapper
public interface SysUserMapper extends BaseMapper<UserBO> {

}
