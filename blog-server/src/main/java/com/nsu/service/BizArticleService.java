package com.nsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.comm.Response;
import com.nsu.entity.BizArticle;
import com.nsu.vo.ArticleDetailVo;
import com.nsu.vo.HotArticleVo;
import com.nsu.vo.PageVo;

import java.util.List;

/**
 * @Author Monica
 * @Date 2022/10/9 9:39
 **/
public interface BizArticleService extends IService<BizArticle> {
    List<HotArticleVo> hotArticleList();

    PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDetailVo getArticleDetail(Long id);
}
