package com.lh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lh.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.vo.PortalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
* @author 86191
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-09-27 00:09:56
* @Entity com.lh.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    //自定义分页查询方法
    IPage<Map> selectPageMap(IPage<Headline> page, @Param("portalVo") PortalVo portalVo);

    Map selectDetailMap(Integer hid);
}




