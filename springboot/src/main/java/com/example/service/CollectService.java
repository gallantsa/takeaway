package com.example.service;

import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Collect;
import com.example.mapper.CollectMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务处理
 **/
@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;

    /**
     * 新增
     */
    public void add(Collect collect) {
        collectMapper.insert(collect);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            collectMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Collect collect) {
        collectMapper.updateById(collect);
    }

    /**
     * 根据ID查询
     */
    public Collect selectById(Integer id) {
        return collectMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Collect> selectAll(Collect collect) {
        return collectMapper.selectAll(collect);
    }

    /**
     * 分页查询
     */
    public PageInfo<Collect> selectPage(Collect collect, Integer pageNum, Integer pageSize) {
        // 拿到当前登录的用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) { // 如果当前登录的用户是商家, 则只能查询自己的信息
            collect.setBusinessId(currentUser.getId()); // 设置商家自己的id作为查询条件
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Collect> list = collectMapper.selectAll(collect);
        return PageInfo.of(list);
    }

}