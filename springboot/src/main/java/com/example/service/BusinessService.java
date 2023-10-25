package com.example.service;

import cn.hutool.core.collection.CollUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.mapper.BusinessMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商家相关的业务方法
 */
@Service
public class BusinessService {

    @Resource
    private BusinessMapper businessMapper;

    public void add(Business business) {
        Business params = new Business();
        params.setUsername(business.getUsername());
        List<Business> list = this.selectAll(params);
        // 如果用户名已存在，抛出自定义异常
        if (CollUtil.isNotEmpty(list)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        business.setRole(RoleEnum.BUSINESS.name());

        businessMapper.insert(business);
    }

    public List<Business> selectAll(Business business) {
        List<Business> list = businessMapper.selectAll(business);
        return list;
    }
}
