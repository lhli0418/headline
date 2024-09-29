package com.lh.service;

import com.lh.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.utils.Result;
import com.lh.vo.PortalVo;

/**
* @author 86191
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-09-27 00:09:56
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 分夜查询数据
     * @param portalVo
     * @return
     */
    Result findNewPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    /**
     * 头条发布实现接口
     * @param token
     * @param headline 发布数据
     * @return
     */
    Result publish(String token, Headline headline);

    /**
     * 头条修改功能接口
     * @param headline
     * @return
     */
    Result updataHeadline(Headline headline);

}
