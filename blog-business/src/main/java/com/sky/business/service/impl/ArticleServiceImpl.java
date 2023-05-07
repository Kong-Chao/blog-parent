package com.sky.business.service.impl;

import com.sky.business.mapper.ArticleMapper;
import com.sky.business.service.ArticleService;
import com.sky.business.vo.Result;
import com.sky.business.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kc
 * @create 2023-05-06 20:34
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result ListArticle(PageParams params) {
      //  articleMapper.selectPage();
        return null;
    }
}
