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
     * 根据商家编号查询商家信息
     * @param businessId 商家编号
     * @return 商家信息
     */
    Business findById(Integer businessId);

    /**
     * 查询所有商家
     * @return 商家列表
     */
    List<Business> findAll();

    /**
     * 按名称和地址关键词搜索商家
     * @param nameKeyword 商家名称关键词
     * @param addressKeyword 商家地址关键词
     * @return 符合条件的商家列表
     */
    List<Business> search(String nameKeyword, String addressKeyword);

    /**
     * 新增商家
     * @param business 商家信息
     * @return 新增商家的编号
     */
    Integer insert(Business business);

    /**
     * 修改商家信息
     * @param business 商家信息
     * @return 受影响行数
     */
    int update(Business business);

    /**
     * 根据编号删除商家
     * @param connection 数据库连接
     * @param businessId 商家编号
     * @return 受影响行数
     */
    int deleteById(Connection connection, Integer businessId);
}
