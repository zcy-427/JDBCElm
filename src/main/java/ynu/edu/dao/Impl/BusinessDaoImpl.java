package ynu.edu.dao.Impl;

import ynu.edu.dao.BusinessDao;
import ynu.edu.entity.Business;
import ynu.edu.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusinessDaoImpl implements BusinessDao {
    /**
     * 商家登录
     * @param businessId 商家编号
     * @param password 商家密码
     * @return 登录成功的商家信息
     */
    @Override
    public Business login(Integer businessId, String password) {
        String sql = "select business_id, password, business_name, business_address, business_explain, star_price, delivery_price "
                + "from business where business_id = ? and password = ?";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, businessId);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapBusiness(resultSet);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("商家登录查询失败", e);
        }

        return null;
    }
}
