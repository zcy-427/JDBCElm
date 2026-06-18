package ynu.edu.dao.Impl;

import ynu.edu.dao.AdminDao;
import ynu.edu.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDaoImpl implements AdminDao {
    /**
     * 根据用户名和密码查询管理员是否存在
     * @param username 管理员用户名
     * @param password 管理员密码
     * @return 管理员是否存在
     */
    @Override
    public boolean existsByUsernameAndPassword(String username, String password) {
        String sql = "select count(*) from admin where username = ? and password = ?";

        try (Connection connection = JdbcUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("管理员登录查询失败", e);
        }

        return false;
    }
}
