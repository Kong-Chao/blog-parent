package com.sky.business.service;

import com.sky.business.vo.TagVo;

import java.util.List;

/**
 * @author kc
 * @create 2023-05-07 17:27
 */
public interface TagService {

    List<TagVo> findTagsByArticleId(Long ArticleId);
}
