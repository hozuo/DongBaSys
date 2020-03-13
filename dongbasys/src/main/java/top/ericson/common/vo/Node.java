package top.ericson.common.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ericson
 * @class Node
 * @date 2020/02/06 15:31
 * @description 基于请求获取数据库对应的菜单表中的所有菜单id,name,parentId，
 * 一行记录封装为一个Node对象，多个node对象存储到List集合
 * @version 1.0
 */
@Data
public class Node implements Serializable {

    private static final long serialVersionUID = -5340026273457753137L;

    private Integer id;
    private String name;
    private Integer parentId;
}
