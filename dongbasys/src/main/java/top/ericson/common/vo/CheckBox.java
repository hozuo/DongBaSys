package top.ericson.common.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ericson
 * @class CheckBox
 * @date 2020/02/08 17:21
 * @description 封装角色信息,用于添加用户时显示角色列表
 * @version 1.0
 */
@Data
public class CheckBox implements Serializable{
    
	private static final long serialVersionUID = -4751717952362978939L;
	
    private Integer id;
	private String name;
}
