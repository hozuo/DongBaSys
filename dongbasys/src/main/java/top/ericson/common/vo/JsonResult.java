package top.ericson.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -5198349481844754759L;
    
    /**状态码 1表示SUCCESS,0表示ERROR*/
    private int state = 1;
    /**状态信息*/
    private String message = "ok";
    /**正确数据*/
    private Object data;

    public JsonResult(String message) {
        this.message = message;
    }

    /**一般查询时调用，封装查询结果*/
    public JsonResult(Object data) {
        this.data = data;
    }

    /**出现异常时时调用*/
    public JsonResult(Throwable t) {
        this.state = 0;
        this.message = t.getMessage();
    }

}
