package com.sky.business.service;

import com.sky.business.vo.Result;
import com.sky.business.vo.params.PageParams;

/**
 * @author kc
 * @create 2023-05-06 20:28
 */
public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param params
     * @return
     */
    public Result ListArticle(PageParams params);
}
