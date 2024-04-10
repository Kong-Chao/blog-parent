package com.sky.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.business.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kc
 * @create 2023-05-06 19:14
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询所有标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);
}
