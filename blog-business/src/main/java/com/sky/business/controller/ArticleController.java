package com.sky.business.controller;

import com.sky.business.service.ArticleService;
import com.sky.business.vo.Result;
import com.sky.business.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author kc
 * @create 2023-05-06 19:27
 * json数据进行交互
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 首页文章列表
     * @param params
     * @return
     */
    @PostConstruct
    public Result listArticles(@RequestBody PageParams params){
        return articleService.ListArticle(params);
    }


}
