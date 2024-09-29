package com.lh.service;

import com.lh.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.utils.Result;
import com.lh.vo.PortalVo;

/**
* @author 86191
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-09-27 00:09:56
*/
public interface TypeService extends IService<Type> {

    Result findAllTypes();

}
