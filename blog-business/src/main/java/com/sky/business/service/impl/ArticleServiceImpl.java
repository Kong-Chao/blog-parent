package com.sky.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.business.mapper.ArticleMapper;
import com.sky.business.pojo.Article;
import com.sky.business.service.ArticleService;
import com.sky.business.service.SysUserService;
import com.sky.business.service.TagService;
import com.sky.business.vo.ArticleVo;
import com.sky.business.vo.Result;
import com.sky.business.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kc
 * @create 2023-05-06 20:34
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService userService;

    /**
     * 分页查询Article
     * @param params
     * @return
     */
    @Override
    public Result ListArticle(PageParams params) {
        Page<Article> page = new Page<>(params.getPage(), params.getPageSize());
        LambdaQueryWrapper<Article>  queryWrapper = new LambdaQueryWrapper<>();
        // 进行置顶排序
        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    /**
     * 对象转移属性复制
     * @param records
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,true,true));
        }
        return null;
    }

    /**
     *
     * @param article
     * @param isTag 是否需要标签
     * @param isAuthor 是否需要作者信息
     * @return
     */
    private ArticleVo copy(Article article, boolean isTag ,boolean isAuthor){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        // 并不是所有的接口的都需要标签、作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(userService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }
}
