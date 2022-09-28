package com.nsu.controller;

import com.github.pagehelper.PageInfo;
import com.nsu.comm.Response;
import com.nsu.entity.BizBlog;
import com.nsu.service.BizBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 博客表(BizBlog)表控制层
 *
 * @author Monica
 * @since 2022-09-28 16:21:36
 */
@Api("博客表")
@RestController
@RequestMapping("bizBlog")
public class BizBlogController {
    /**
     * 服务对象
     */
    @Resource
    private BizBlogService bizBlogService;

    /**
     * 分页查询
     * @return 查询结果
     */
    @ApiOperation(value = "获取分页数据")
    @PostMapping("/getData")
    public Response list(@RequestParam Map map)  {
        PageInfo pageInfo = bizBlogService.getList(map);
        return new Response().success(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Response queryById(@PathVariable("id") String id) {
        return new Response().success(this.bizBlogService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param bizBlog 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Response add(BizBlog bizBlog) {
        return new Response().success(this.bizBlogService.insert(bizBlog));
    }

    /**
     * 编辑数据
     *
     * @param bizBlog 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public Response edit(BizBlog bizBlog) {
        return new Response().success(this.bizBlogService.update(bizBlog));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @PostMapping("delete")
    public Response deleteById(String id) {
        return new Response().success(this.bizBlogService.deleteById(id));
    }



}

