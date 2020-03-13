package top.ericson.common.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import top.ericson.sys.entity.SysDept;

/**
 * @author Ericson
 * @class SysUserDeptVo
 * @date 2020/02/07 22:56
 * @description 除了将deptId换成SysDept对象以外和数据库全是一一对应的
 * @version 1.0
 */
@Data
public class SysUserDeptVo implements Serializable {

    private static final long serialVersionUID = 826685329050752125L;

    private Integer id;
    private String username;
    private String password;// md5
    private String salt;
    private String email;
    private String mobile;
    private Integer valid = 1;
    private SysDept sysDept; // private Integer deptId;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;

}