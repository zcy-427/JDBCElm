package ynu.edu;


import org.junit.jupiter.api.Test;
import ynu.edu.util.JdbcUtil;

import java.sql.Connection;

public class TestJdbc {

    @Test
    public void TestConnection() {
        try (Connection connection = JdbcUtil.getConnection()) {
            System.out.println("数据库连接成功！");
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
