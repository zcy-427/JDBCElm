package ynu.edu.dao.Impl;

import ynu.edu.dao.FoodDao;
import ynu.edu.entity.Food;
import ynu.edu.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FoodDaoImpl implements FoodDao {
    /**
     * 新增食品
     * @param food 食品信息
     * @return 新增食品的编号
     */
    @Override
    public Integer insert(Food food) {
        String sql = "insert into food(business_id, food_name, food_explain, food_price) values(?, ?, ?, ?)";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, food.getBusinessId());
            preparedStatement.setString(2, food.getFoodName());
            preparedStatement.setString(3, food.getFoodExplain());
            preparedStatement.setBigDecimal(4, food.getFoodPrice());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("新增食品失败", e);
        }

        return null;
    }

    /**
     * 根据商家编号删除食品
     * @param connection 数据库连接
     * @param businessId 商家编号
     * @return 受影响行数
     */
    @Override
    public int deleteByBusinessId(Connection connection, Integer businessId) {
        String sql = "delete from food where business_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, businessId);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("删除商家食品失败", e);
        }
    }
}
