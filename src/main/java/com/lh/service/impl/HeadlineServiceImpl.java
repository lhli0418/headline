package com.lh.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.pojo.Headline;
import com.lh.service.HeadlineService;
import com.lh.mapper.HeadlineMapper;
import com.lh.utils.JwtHelper;
import com.lh.utils.MD5Util;
import com.lh.utils.Result;
import com.lh.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author 86191
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-09-27 00:09:56
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */

    @Override
    public Result findNewPage(PortalVo portalVo) {

        //1.条件拼接 需要非空判断
        LambdaQueryWrapper<Headline> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(portalVo.getKeyWords()),Headline::getTitle,portalVo.getKeyWords())
                .eq(portalVo.getType()!= null,Headline::getType,portalVo.getType());

        //2.分页参数
        IPage<Headline> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());

        //3.分页查询
        //查询的结果 "pastHours":"3"   // 发布时间已过小时数 我们查询返回一个map
        //自定义方法
        headlineMapper.selectPageMap(page, portalVo);

        //4.结果封装
        //分页数据封装
        Map<String,Object> pageInfo =new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());

        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        // 响应JSON
        return Result.ok(pageInfoMap);
    }

    @Override
    public Result showHeadlineDetail(Integer hid) {
       Map headlineDetail = headlineMapper.selectDetailMap(hid);

       // 更改信息
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setVersion((Integer) headlineDetail.get("version"));
        headline.setPageViews((Integer) headlineDetail.get("pageViews")+1);
        headlineMapper.updateById(headline);

        Map<String,Object> headlineMap = new HashMap<>();
        headlineMap.put("headline",headlineDetail);
        return Result.ok(headlineMap);
    }

    @Override
    public Result publish(String token, Headline headline) {

        int i = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(i);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);
        return Result.ok(null);

    }

    @Override
    public Result updataHeadline(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);

        return Result.ok(null);

    }

}





