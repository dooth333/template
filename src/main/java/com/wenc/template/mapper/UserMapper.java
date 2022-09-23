package com.wenc.template.mapper;

import com.wenc.template.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenc
 * @since 2022-09-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
