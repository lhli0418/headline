package com.lh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.pojo.Type;
import com.lh.service.TypeService;
import com.lh.mapper.TypeMapper;
import com.lh.utils.Result;
import com.lh.vo.PortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 86191
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-09-27 00:09:56
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 查询所有类别数据
     * @return
     */
    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }


}




