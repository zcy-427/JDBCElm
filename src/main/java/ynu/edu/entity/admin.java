package ynu.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class admin {
    //管理员id
    private Integer adminId;

    //管理员用户名
    private String userName;

    //管理员密码
    private String password;
}
