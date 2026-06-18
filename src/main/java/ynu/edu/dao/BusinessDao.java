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
}
