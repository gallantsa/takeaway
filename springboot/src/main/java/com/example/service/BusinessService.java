package com.example.service;

import com.example.entity.Business;
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

    public List<Business> selectAll() {
        List<Business> list = businessMapper.selectAll();
        return list;
    }
}
