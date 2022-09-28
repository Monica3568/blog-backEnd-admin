package com.nsu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nsu.mapper.BizBlogMapper;
import com.nsu.entity.BizBlog;
import com.nsu.service.BizBlogService;
import com.nsu.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 博客表(BizBlog)表服务实现类
 *
 * @author Monica
 * @since 2022-09-28 16:21:36
 */
@Service("bizBlogService")
public class BizBlogServiceImpl implements BizBlogService {
    @Resource
    private BizBlogMapper bizBlogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    @Override
    public BizBlog queryById(String uid) {
        return this.bizBlogMapper.queryById(uid);
    }

    /**
     * 分页查询
     *
     * @param bizBlog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<BizBlog> queryByPage(BizBlog bizBlog, PageRequest pageRequest) {
        long total = this.bizBlogMapper.count(bizBlog);
        return new PageImpl<>(this.bizBlogMapper.queryAllByLimit(bizBlog, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param bizBlog 实例对象
     * @return 实例对象
     */
    @Override
    public BizBlog insert(BizBlog bizBlog) {
        bizBlog.setCreateTime(new Date());
        bizBlog.setUid(UUIDUtils.getUUID());
        this.bizBlogMapper.insert(bizBlog);
        return bizBlog;
    }

    /**
     * 修改数据
     *
     * @param bizBlog 实例对象
     * @return 实例对象
     */
    @Override
    public BizBlog update(BizBlog bizBlog) {
        this.bizBlogMapper.update(bizBlog);
        return this.queryById(bizBlog.getUid());
    }

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String uid) {
        return this.bizBlogMapper.deleteById(uid) > 0;
    }

    /**
     * 获取分页数据
     */
    @Override
    public PageInfo getList(Map map){
        int pageNum = 1;
        int pageSize = 10;
        if (!StringUtils.isEmpty(map.get("page"))) {
            pageNum = Integer.parseInt((String) map.get("page"));
        }
        if (!StringUtils.isEmpty(map.get("limit"))) {
            pageSize = Integer.parseInt((String) map.get("limit"));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BizBlog> list = bizBlogMapper.selectAllByMap(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
