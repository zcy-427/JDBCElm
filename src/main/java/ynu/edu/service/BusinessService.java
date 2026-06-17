package ynu.edu.service;

import ynu.edu.entity.Business;

public interface BusinessService {
    /**
     * 商家登录
     * @param businessId 商家编号
     * @param password 商家密码
     * @return 登录成功的商家信息
     */
    Business login(Integer businessId, String password);

    /**
     * 根据商家编号查询商家信息
     * @param businessId 商家编号
     * @return 商家信息
     */
    Business getBusinessById(Integer businessId);

    /**
     * 修改商家信息
     * @param business 商家信息
     * @return 修改是否成功
     */
    boolean updateBusiness(Business business);

    /**
     * 修改商家登录密码
     * @param businessId 商家编号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改是否成功
     */
    boolean updatePassword(Integer businessId, String oldPassword, String newPassword);
}
