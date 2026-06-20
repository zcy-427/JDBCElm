package ynu.edu.dao.Impl;

import ynu.edu.dao.FoodDao;
import ynu.edu.entity.Food;
import ynu.edu.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImpl implements FoodDao {
    /**
     * 根据商家编号查询食品列表
     * @param businessId 商家编号
     * @return 食品列表
     */
    @Override
    public List<Food> findByBusinessId(Integer businessId) {
        String sql = "select food_id, business_id, food_name, food_explain, food_price "
                + "from food where business_id = ? order by food_id";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, businessId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Food> foods = new ArrayList<>();
                while (resultSet.next()) {
                    foods.add(mapFood(resultSet));
                }
                return foods;
            }
        } catch (Exception e) {
            throw new RuntimeException("查询食品列表失败", e);
        }
    }

    /**
     * 根据食品编号和商家编号查询食品信息
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 食品信息
     */
    @Override
    public Food findByIdAndBusinessId(Integer foodId, Integer businessId) {
        String sql = "select food_id, business_id, food_name, food_explain, food_price "
                + "from food where food_id = ? and business_id = ?";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, foodId);
            preparedStatement.setInt(2, businessId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFood(resultSet);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("查询食品信息失败", e);
        }

        return null;
    }

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
     * 修改食品信息
     * @param food 食品信息
     * @return 受影响行数
     */
    @Override
    public int update(Food food) {
        String sql = "update food set food_name = ?, food_explain = ?, food_price = ? "
                + "where food_id = ? and business_id = ?";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, food.getFoodName());
            preparedStatement.setString(2, food.getFoodExplain());
            preparedStatement.setBigDecimal(3, food.getFoodPrice());
            preparedStatement.setInt(4, food.getFoodId());
            preparedStatement.setInt(5, food.getBusinessId());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("修改食品信息失败", e);
        }
    }

    /**
     * 根据食品编号和商家编号删除食品
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 受影响行数
     */
    @Override
    public int deleteByIdAndBusinessId(Integer foodId, Integer businessId) {
        String sql = "delete from food where food_id = ? and business_id = ?";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, foodId);
            preparedStatement.setInt(2, businessId);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("删除食品失败", e);
        }
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

    /**
     * 将结果集映射为食品对象
     * @param resultSet 查询结果集
     * @return 食品对象
     */
    private Food mapFood(ResultSet resultSet) throws Exception {
        Food food = new Food();
        food.setFoodId(resultSet.getInt("food_id"));
        food.setBusinessId(resultSet.getInt("business_id"));
        food.setFoodName(resultSet.getString("food_name"));
        food.setFoodExplain(resultSet.getString("food_explain"));
        food.setFoodPrice(resultSet.getBigDecimal("food_price"));
        return food;
    }
}
