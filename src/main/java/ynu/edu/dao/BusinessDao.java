package ynu.edu.dao;

import ynu.edu.entity.Business;

import java.sql.Connection;
import java.util.List;

public interface BusinessDao {
    /**
     * 商家登录
     * @param businessId 商家编号
     * @param password 商家密码
     * @return 登录成功的商家信息
     */
    Business login(Integer businessId, String password);
    /**
     * 新增商家
     * @param business 商家信息
     * @return 新增商家的编号
     */
    Integer insert(Business business);
}
