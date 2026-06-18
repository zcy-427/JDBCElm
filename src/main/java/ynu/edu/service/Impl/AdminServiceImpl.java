package ynu.edu.service.Impl;

import ynu.edu.dao.AdminDao;
import ynu.edu.dao.BusinessDao;
import ynu.edu.dao.FoodDao;
import ynu.edu.dao.Impl.AdminDaoImpl;
import ynu.edu.dao.Impl.BusinessDaoImpl;
import ynu.edu.dao.Impl.FoodDaoImpl;
import ynu.edu.entity.Business;
import ynu.edu.service.AdminService;
import ynu.edu.util.JdbcUtil;

import java.sql.Connection;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao = new AdminDaoImpl();
    private final BusinessDao businessDao = new BusinessDaoImpl();
    private final FoodDao foodDao = new FoodDaoImpl();

    /**
     * 管理员登录
     * @param username 管理员用户名
     * @param password 管理员密码
     * @return 登录是否成功
     */
    @Override
    public boolean login(String username, String password) {
        if (isBlank(username) || isBlank(password)) {
            return false;
        }
        return adminDao.existsByUsernameAndPassword(username, password);
    }

    /**
     * 查询所有商家列表
     * @return 商家列表
     */
    @Override
    public List<Business> listBusinesses() {
        return businessDao.findAll();
    }

    /**
     * 按名称和地址关键词搜索商家
     * @param nameKeyword 商家名称关键词
     * @param addressKeyword 商家地址关键词
     * @return 符合条件的商家列表
     */
    @Override
    public List<Business> searchBusinesses(String nameKeyword, String addressKeyword) {
        return businessDao.search(nameKeyword, addressKeyword);
    }

    /**
     * 新增商家
     * @param business 商家信息
     * @return 新增商家的编号
     */
    @Override
    public Integer addBusiness(Business business) {
        if (business == null) {
            return null;
        }
        if (isBlank(business.getPassword())) {
            business.setPassword("123456");
        }
        return businessDao.insert(business);
    }

    /**
     * 删除商家
     * @param businessId 商家编号
     * @return 删除是否成功
     */
    @Override
    public boolean deleteBusiness(Integer businessId) {
        if (businessId == null || businessId <= 0) {
            return false;
        }

        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);

            foodDao.deleteByBusinessId(connection, businessId);
            int affectedRows = businessDao.deleteById(connection, businessId);

            connection.commit();
            return affectedRows > 0;
        } catch (Exception e) {
            rollback(connection);
            throw new RuntimeException("删除商家事务失败", e);
        } finally {
            close(connection);
        }
    }

    /**
     * 判断字符串是否为空
     * @param value 字符串
     * @return 是否为空
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 回滚数据库事务
     * @param connection 数据库连接
     */
    private void rollback(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch (Exception e) {
            throw new RuntimeException("事务回滚失败", e);
        }
    }

    /**
     * 关闭数据库连接
     * @param connection 数据库连接
     */
    private void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException("数据库连接关闭失败", e);
        }
    }
}
