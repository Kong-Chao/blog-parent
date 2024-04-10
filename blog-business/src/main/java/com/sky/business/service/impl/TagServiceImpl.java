package com.sky.business.service.impl;

import com.sky.business.mapper.TagMapper;
import com.sky.business.pojo.Tag;
import com.sky.business.service.TagService;
import com.sky.business.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kc
 * @create 2023-05-07 17:34
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long ArticleId) {
        List<Tag> tags = tagMapper.findTagsByArticleId(ArticleId);
        return copyList(tags);
    }

    /**
     * 对象转移属性复制
     * @param
     * @return
     */
    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> articleVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            articleVoList.add(copy(tag));
        }
        return articleVoList;
    }

    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
}
