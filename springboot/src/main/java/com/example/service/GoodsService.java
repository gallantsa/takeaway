package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品信息业务处理
 **/
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private BusinessService businessService;

    @Resource
    private CategoryService categoryService;

    /**
     * 新增
     */
    public void add(Goods goods) {
        // 校验权限
        businessService.checkBusinessAuth();

        // 查询分类的数据
        Category category = categoryService.selectById(goods.getCategoryId());
        if (ObjectUtil.isNotEmpty(category)) {
            goods.setBusinessId(category.getBusinessId());
        }
        goodsMapper.insert(goods);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        // 校验权限
        businessService.checkBusinessAuth();
        goodsMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        // 校验权限
        businessService.checkBusinessAuth();
        for (Integer id : ids) {
            goodsMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Goods goods) {
        // 校验权限
        businessService.checkBusinessAuth();
        goodsMapper.updateById(goods);
    }

    /**
     * 根据ID查询
     */
    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Goods> selectAll(Goods goods) {
        // 拿到当前登录的用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) { // 如果当前登录的用户是商家, 则只能查询自己数据
            goods.setBusinessId(currentUser.getId()); // 设置商家自己的id作为查询条件
        }
        return goodsMapper.selectAll(goods);
    }

    /**
     * 分页查询
     */
    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        // 拿到当前登录的用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) { // 如果当前登录的用户是商家, 则只能查询自己数据
            goods.setBusinessId(currentUser.getId()); // 设置商家自己的id作为查询条件
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }

}