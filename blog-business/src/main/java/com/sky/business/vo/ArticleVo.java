package com.sky.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author kc
 * @create 2023-05-07 16:59
 */
@Data
public class ArticleVo {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 评论
     */
    private String summary;

    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 浏览数量
     */
    private Integer viewCounts;

    /**
     *作者id
     */
    private String author;

    /**
     * 内容Id
     */
    private Long bodyId;

    /**
     * 类别id
     */
    private Long categoryId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 是否置顶
     */
    private Integer weight;

    private List<TagVo> tags;
}
