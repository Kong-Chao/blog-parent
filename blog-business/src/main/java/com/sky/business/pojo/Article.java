package com.sky.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author kc
 * @create 2023-05-05 20:35
 */
@Data
@TableName("ms_article")
public class Article {

    @Id
    private Long id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 评论
     */
    @TableField("summary")
    private String summary;

    /**
     * 评论数量
     */
    @TableField("comment_counts")
    private int commentCounts;

    /**
     * 浏览数量
     */
    @TableField("view_counts")
    private int viewCounts;

    /**
     *作者id
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 内容Id
     */
    @TableField("body_id")
    private Long bodyId;

    /**
     * 类别id
     */
    @TableField("category_id")
    private Long categoryId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_date")
    private Date createDate;


}
