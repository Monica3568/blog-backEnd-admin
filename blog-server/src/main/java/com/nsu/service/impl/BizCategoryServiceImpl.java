package com.nsu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.constants.SystemConstants;
import com.nsu.entity.BizArticle;
import com.nsu.entity.BizCategory;
import com.nsu.mapper.BizCategoryMapper;
import com.nsu.service.BizArticleService;
import com.nsu.service.BizCategoryService;
import com.nsu.utils.BeanCopyUtils;
import com.nsu.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(BizCategory)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 10:33:34
 */
@Service("bizCategoryService")
public class BizCategoryServiceImpl extends ServiceImpl<BizCategoryMapper, BizCategory> implements BizCategoryService {

    @Autowired
    private BizArticleService articleService;

    @Override
    public List<CategoryVo> getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<BizArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BizArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<BizArticle> articleList = articleService.list(wrapper);
        //获取文章的分类id，并且去重(set自动去重)
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<BizCategory> categories = listByIds(categoryIds);
        List<BizCategory> categoryList = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);

        return categoryVos;
    }
}

