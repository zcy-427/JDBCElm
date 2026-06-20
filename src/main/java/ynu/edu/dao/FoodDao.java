package ynu.edu.dao;

import ynu.edu.entity.Food;

import java.sql.Connection;

public interface FoodDao {
    /**
     * 新增食品
     * @param food 食品信息
     * @return 新增食品的编号
     */
    Integer insert(Food food);

    /**
     * 根据商家编号删除食品
     * @param connection 数据库连接
     * @param businessId 商家编号
     * @return 受影响行数
     */
    int deleteByBusinessId(Connection connection, Integer businessId);
}
