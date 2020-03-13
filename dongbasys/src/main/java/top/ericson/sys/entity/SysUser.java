package top.ericson.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUser implements Serializable {
    
    private static final long serialVersionUID = -5310595143059517770L;
    
    private Integer id;
    private String username;
    private String password;
    // 盐值
    private String salt;
    private String email;
    private String mobile;
    // 默认禁用
    private Integer valid = 1;
    private Integer deptId;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;
}