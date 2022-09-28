package com.nsu.service;

import com.github.pagehelper.PageInfo;
import com.nsu.entity.BizBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 博客表(BizBlog)表服务接口
 *
 * @author Monica
 * @since 2022-09-28 16:21:36
 */
public interface BizBlogService {

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    BizBlog queryById(String uid);

    /**
     * 分页查询
     *
     * @param bizBlog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<BizBlog> queryByPage(BizBlog bizBlog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param bizBlog 实例对象
     * @return 实例对象
     */
    BizBlog insert(BizBlog bizBlog);

    /**
     * 修改数据
     *
     * @param bizBlog 实例对象
     * @return 实例对象
     */
    BizBlog update(BizBlog bizBlog);

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    boolean deleteById(String uid);

    /**
     * 获取分页数据
     *
     * @param map
     * @return
     */
    PageInfo getList(Map map);
}
