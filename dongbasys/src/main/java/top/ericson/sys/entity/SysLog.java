package top.ericson.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

// lombok自动生成get和set方法
@Data
//链式编程,生成的setter返回this而不是void
@Accessors(chain = true)
public class SysLog implements Serializable {
    
    private static final long serialVersionUID = 3800134363493930598L;
    
    private Integer id;
    // 用户名
    private String username;
    // 用户操作
    private String operation;
    // 请求方法
    private String method;
    // 请求参数
    private String params;
    // 执行时长(毫秒)
    private Long time;
    // IP地址
    private String ip;
    // 创建时间
    private Date createdTime;
    
}
