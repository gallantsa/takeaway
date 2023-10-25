package com.example.controller;

import com.example.common.Result;
import com.example.entity.Business;
import com.example.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商家管理相关接口
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Business> list = businessService.selectAll();
        return Result.success(list);
    }
}
