package com.nsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.comm.Response;
import com.nsu.entity.BizCategory;
import com.nsu.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(BizCategory)表服务接口
 *
 * @author makejava
 * @since 2022-10-09 10:33:34
 */
public interface BizCategoryService extends IService<BizCategory> {

    List<CategoryVo> getCategoryList();
}

