package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 高复用的类，返回给前端序列化信息
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)  //保证序列化json的时候，如果是null的对象，key也会消失
public class ServerResponse<T> implements Serializable{
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status=status;
    }

    private ServerResponse(int status, String msg){
        this.status=status;
        this.msg=msg;
    }
    private ServerResponse(int status, T data){         //当传入的第二个参数为String类型时，会赋值给msg，怎么给data？
        this.status=status;
        this.data=data;
    }
    private ServerResponse(int status,String msg, T data){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }
    public int getStatus(){
        return this.status;
    }
    public String getMsg(){
        return this.msg;
    }
    public T getData(){
        return this.data;
    }

    @JsonIgnore    //这样就不会返回boolean字段到json里面，jackson扫描public方法
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }

    public static  <T>ServerResponse<T> createBySuccess(){                         //第一个<T>代表泛型方法，第二个<T>限定参数类型
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static  <T>ServerResponse<T> createBySuccessMsg(String msg){             //这样就避免了String信息没办法给data，两个方法名称不一样
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T>ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T>ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T>ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T>ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T>ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}
