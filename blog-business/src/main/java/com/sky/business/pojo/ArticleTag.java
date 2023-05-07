package com.sky.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author kc
 * @create 2023-05-06 18:57
 */
@Data
@TableName("article_tag")
public class ArticleTag {
    @Id
    private Long id;

    /**
     * 文章id
     */
    @TableField("article_id")
    private Integer articleId;

    /**
     * 标签id
     */
    @TableField("tag_id")
    private Integer tagId;
}
