package com.nsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsu.entity.BizLink;
import com.nsu.vo.LinkVo;

import java.util.List;

/**
 * 友链(BizLink)表服务接口
 *
 * @author makejava
 * @since 2022-10-09 16:06:31
 */
public interface BizLinkService extends IService<BizLink> {

    List<LinkVo> getAllLink();
}

