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
@TableName("ms_tag")
public class Tag {
    @Id
    private Long id;

    /**
     * 账户
     */
    @TableField("article_id")
    private Integer articleId;

    /**
     * 是否是管理员
     */
    @TableField("tag_id")
    private Integer tagId;
}
