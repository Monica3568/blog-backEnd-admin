package com.nsu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.constants.SystemConstants;
import com.nsu.entity.BizArticle;
import com.nsu.entity.BizCategory;
import com.nsu.mapper.BizArticleMapper;
import com.nsu.service.BizArticleService;
import com.nsu.service.BizCategoryService;
import com.nsu.utils.BeanCopyUtils;
import com.nsu.vo.ArticleDetailVo;
import com.nsu.vo.ArticleListVo;
import com.nsu.vo.HotArticleVo;
import com.nsu.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author Monica
 * @Date 2022/10/9 9:39
 **/
@Service
public class BizArticleServiceImpl extends ServiceImpl<BizArticleMapper, BizArticle> implements BizArticleService {

    @Autowired
    private BizCategoryService categoryService;

    @Override
    public List<HotArticleVo> hotArticleList() {
        //查询热门文章 封装成Response返回
        LambdaQueryWrapper<BizArticle> wrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        wrapper.eq(BizArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        wrapper.orderByDesc(BizArticle::getViewCount);
        //最多只查询10条
        Page<BizArticle> page = new Page(1, 10);
        page(page, wrapper);
        List<BizArticle> articles = page.getRecords();
        //bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (BizArticle article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return articleVos;
    }

    @Override
    public PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<BizArticle> wrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        wrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, BizArticle::getCategoryId, categoryId);
        // 状态是正式发布的
        wrapper.eq(BizArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        wrapper.orderByDesc(BizArticle::getIsTop);

        //分页查询
        Page<BizArticle> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        List<BizArticle> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(bizArticle -> {
                    bizArticle.setCategoryName(categoryService.getById(bizArticle.getCategoryId()).getName());
                    return bizArticle;
                })
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return pageVo;
    }

    @Override
    public ArticleDetailVo getArticleDetail(Long id) {

        BizArticle article = getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();

        BizCategory category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return articleDetailVo;
    }
}
