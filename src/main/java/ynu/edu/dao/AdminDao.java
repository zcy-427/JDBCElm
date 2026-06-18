package ynu.edu.dao;

public interface AdminDao {
    /**
     * 根据用户名和密码查询管理员是否存在
     * @param username 管理员用户名
     * @param password 管理员密码
     * @return 管理员是否存在
     */
    boolean existsByUsernameAndPassword(String username, String password);
}
