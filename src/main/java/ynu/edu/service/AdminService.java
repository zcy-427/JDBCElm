package ynu.edu.service;

import ynu.edu.entity.Business;

import java.util.List;

public interface AdminService {
    /**
     * 管理员登录
     * @param username 管理员用户名
     * @param password 管理员密码
     * @return 登录是否成功
     */
    boolean login(String username, String password);

    /**
     * 查询所有商家列表
     * @return 商家列表
     */
    List<Business> listBusinesses();

    /**
     * 按名称和地址关键词搜索商家
     * @param nameKeyword 商家名称关键词
     * @param addressKeyword 商家地址关键词
     * @return 符合条件的商家列表
     */
    List<Business> searchBusinesses(String nameKeyword, String addressKeyword);

    /**
     * 新增商家
     * @param business 商家信息
     * @return 新增商家的编号
     */
    Integer addBusiness(Business business);

    /**
     * 根据商家编号查询商家信息
     * @param businessId 商家编号
     * @return 商家信息
     */
    Business getBusinessById(Integer businessId);

    /**
     * 删除商家
     * @param businessId 商家编号
     * @return 删除是否成功
     */
    boolean deleteBusiness(Integer businessId);
}
