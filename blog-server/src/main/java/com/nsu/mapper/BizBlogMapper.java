package com.nsu.mapper;

import com.nsu.entity.BizBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

/**
 * 博客表(BizBlog)表数据库访问层
 *
 * @author Monica
 * @since 2022-09-28 16:21:36
 */
@Mapper
public interface BizBlogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    BizBlog queryById(String uid);

    /**
     * 查询指定行数据
     *
     * @param bizBlog 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<BizBlog> queryAllByLimit(BizBlog bizBlog, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param bizBlog 查询条件
     * @return 总行数
     */
    long count(BizBlog bizBlog);

    /**
     * 新增数据
     *
     * @param bizBlog 实例对象
     * @return 影响行数
     */
    int insert(BizBlog bizBlog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BizBlog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BizBlog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BizBlog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BizBlog> entities);

    /**
     * 修改数据
     *
     * @param bizBlog 实例对象
     * @return 影响行数
     */
    int update(BizBlog bizBlog);

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 影响行数
     */
    int deleteById(String uid);

    List<BizBlog> selectAllByMap(Map map);

}

