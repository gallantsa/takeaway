package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.mapper.BusinessMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 商家相关的业务方法
 */
@Service
public class BusinessService {

    @Resource
    private BusinessMapper businessMapper;

    /**
     * 新增商家
     * @param business
     */
    public void add(Business business) {
        Business dbBusiness = this.selectByUsername(business.getUsername());

        // 如果用户名已存在，抛出自定义异常
        if (ObjectUtil.isNotEmpty(dbBusiness)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        business.setRole(RoleEnum.BUSINESS.name());

        businessMapper.insert(business);
    }

    /**
     * 删除商家
     * @param id
     */
    public void deleteById(Integer id) {
        businessMapper.deleteById(id);
    }

    /**
     * 批量删除商家
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    /**
     * 修改商家
     * @param business
     */
    public void updateById(Business business) {
        // 根据id查询商家是否存在
        Business dbBusiness1 = selectById(business.getId());
        // 如果商家不存在，抛出自定义异常
        if (ObjectUtil.isEmpty(dbBusiness1)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        // 根据用户名查询其它商家是否存在用户名与修改后的当前商家相同
        Business dbBusiness2 = this.selectByUsername(business.getUsername());
        // 根据当前更新的商家的账号, 如果数据库存在其它商家的账号与当前更新后的商家的账号相同的情况, 则抛出自定义异常
        if (ObjectUtil.isNotEmpty(dbBusiness2) && !Objects.equals(dbBusiness2.getId(), business.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        businessMapper.updateById(business);
    }


    /**
     * 查询商家列表
     * @param business
     * @return
     */
    public List<Business> selectAll(Business business) {
        List<Business> list = businessMapper.selectAll(business);
        return list;
    }

    /**
     * 根据账号查询
     * @param username
     * @return
     */
    public Business selectByUsername(String username) {
        Business params = new Business();
        params.setUsername(username);
        List<Business> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Business selectById(Integer id) {
        Business params = new Business();
        params.setId(id);
        List<Business> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    /**
     * 分页查询商家列表
     * @param business
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Business> selectPage(Business business, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Business> list = businessMapper.selectAll(business);
        return PageInfo.of(list);
    }
}
