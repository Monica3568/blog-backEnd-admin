package com.nsu.controller;

import com.nsu.comm.Response;
import com.nsu.service.BizArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Monica
 * @Date 2022/10/9 9:41
 **/

@RestController
@RequestMapping("article")
public class BizArticleController {
    @Autowired
    private BizArticleService articleService;

    @GetMapping("/articleList")
    public Response articleList(Integer pageNum,Integer pageSize,Long categoryId){

        return new Response().success(articleService.articleList(pageNum,pageSize,categoryId));

    }

    @PostMapping("/hotArticleList")
    public Response hotArticleList(){
        return new Response().success(articleService.hotArticleList());
    }

    @GetMapping("/{id}")
    public Response getArticleDetail(@PathVariable("id") Long id){
        return new Response().success(articleService.getArticleDetail(id));
    }
}
