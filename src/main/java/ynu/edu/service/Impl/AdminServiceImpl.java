package ynu.edu.service.Impl;

import ynu.edu.entity.Business;
import ynu.edu.service.AdminService;

import java.util.Collections;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    /**
     * 管理员登录
     * @param username 管理员用户名
     * @param password 管理员密码
     * @return 登录是否成功
     */
    @Override
    public boolean login(String username, String password) {
        // TODO 后续接入 AdminDao 完成真实管理员登录校验
        return false;
    }

    /**
     * 查询所有商家列表
     * @return 商家列表
     */
    @Override
    public List<Business> listBusinesses() {
        // TODO 后续接入 BusinessDao 查询所有商家
        return Collections.emptyList();
    }

    /**
     * 按名称和地址关键词搜索商家
     * @param nameKeyword 商家名称关键词
     * @param addressKeyword 商家地址关键词
     * @return 符合条件的商家列表
     */
    @Override
    public List<Business> searchBusinesses(String nameKeyword, String addressKeyword) {
        // TODO 后续接入 BusinessDao 按名称和地址模糊查询商家
        return Collections.emptyList();
    }

    /**
     * 新增商家
     * @param business 商家信息
     * @return 新增商家的编号
     */
    @Override
    public Integer addBusiness(Business business) {
        // TODO 后续接入 BusinessDao 新增商家并返回自增编号
        return null;
    }

    /**
     * 删除商家
     * @param businessId 商家编号
     * @return 删除是否成功
     */
    @Override
    public boolean deleteBusiness(Integer businessId) {
        // TODO 后续接入 BusinessDao/FoodDao，并在 Service 层完成事务删除
        return false;
    }
}
