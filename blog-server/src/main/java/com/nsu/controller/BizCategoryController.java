package com.nsu.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.nsu.comm.Response;
import com.nsu.entity.BizCategory;
import com.nsu.service.BizCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 分类表(BizCategory)表控制层
 *
 * @author makejava
 * @since 2022-10-09 10:39:31
 */
@RestController
@RequestMapping("/category")
public class BizCategoryController {
    @Autowired
    BizCategoryService categoryService;

    @GetMapping("/getCategoryList")
    public Response getCategoryList(){
        return new Response().success(categoryService.getCategoryList());
    }

}

