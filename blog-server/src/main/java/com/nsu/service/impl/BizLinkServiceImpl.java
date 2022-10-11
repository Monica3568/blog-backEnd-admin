package com.nsu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.constants.SystemConstants;
import com.nsu.entity.BizLink;
import com.nsu.mapper.BizLinkMapper;
import com.nsu.service.BizLinkService;
import com.nsu.utils.BeanCopyUtils;
import com.nsu.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(BizLink)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 16:06:31
 */
@Service("bizLinkService")
public class BizLinkServiceImpl extends ServiceImpl<BizLinkMapper, BizLink> implements BizLinkService {

    @Override
    public  List<LinkVo> getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<BizLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BizLink::getStatus, SystemConstants.LINK_STATUS_APPROVED);
        List<BizLink> links = list(wrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return linkVos;
    }
}

