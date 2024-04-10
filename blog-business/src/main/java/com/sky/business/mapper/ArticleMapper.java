package com.sky.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.business.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kc
 * @create 2023-05-06 19:06
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
