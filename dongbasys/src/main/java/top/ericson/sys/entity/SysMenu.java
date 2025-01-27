package top.ericson.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysMenu implements Serializable{
    
	private static final long serialVersionUID = 3869215558107638954L;
	
    private Integer id;
	/**菜单名称*/
	private String name;
	/**菜单url: log/doFindPageObjects*/
	private String url;
	/**菜单类型(两种:按钮,普通菜单)*/
	private Integer type=1;
	/**排序(序号)*/
	private Integer sort;
	/**备注*/
	private String note;
	/**上级菜单id*/
	private Integer parentId;
	/**菜单对应的权限标识(sys:log:delete)*/
	private String permission;
	/**创建用户*/
	private String createdUser;
	/**修改用户*/
	private String modifiedUser;
	/**创建时间*/
	private Date createdTime;
    /**修改时间*/
	private Date modifiedTime;
}
