package com.flyppe.flypplemvp.network;

/**
 * 针对status + msg + data的统一数据格式
 * 当然，如果能和后台约定好数据格式是这个样子，就可以直接用了
 * 如果后台数据结构不是这样，可以根据实际情况自己封装，也可以不封装
 *
 * Created by flypple on 2018/8/20.
 */
public class HttpResult<T> {
    /*
        {
            status : "0";
            msg : "OK"
            data : ...
        }
     */
    private String status, msg;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}
