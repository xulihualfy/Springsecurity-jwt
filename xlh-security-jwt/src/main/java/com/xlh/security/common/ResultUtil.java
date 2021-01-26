package com.xlh.security.common;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author xulihua
 * @date 2021/1/21 16:32
 * 返回数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public  class ResultUtil<T> implements Serializable {
     private static final long serialVersionUID = 1L;
     public static final String SUCCESS="SUCCESS";
     public static final String FAILED="FAILED";
     public static final Integer SUCCESS_CODE=200;
     public static final Integer FAILED_CODE=500;
     public static final Integer FAILED_NO_PERMISSION_CODE=403;
     public static final Integer NO_LOGIN_CODE=401;
     public static final String NO_MSG=null;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 状态
     */
    private String status;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回成功有信息
     * @param message:信息
     * @return
     */
    public static ResultUtil success(String message){
        return new ResultUtil(SUCCESS_CODE,SUCCESS,null,message);
    }
    /**
     * 返回成功无信息
     * @return
     */
    public static ResultUtil success(){
        return new ResultUtil(SUCCESS_CODE,SUCCESS,null,NO_MSG);
    }
    /**
     * 返回成功有数据
     * @param data:数据
     * @return
     */
    public static<T> ResultUtil success(T data){
        return new ResultUtil(SUCCESS_CODE,SUCCESS,data,NO_MSG);
    }
    /**
     * 返回成功有信息有数据
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResultUtil success(String message,T data){
        return new ResultUtil(SUCCESS_CODE,SUCCESS,data,message);
    }
    /**
     * 返回失败无信息
     * @return
     */
    public static ResultUtil failed(){
        return new ResultUtil(FAILED_CODE,FAILED,null,NO_MSG);
    }
    /**
     * 返回失败有信息
     * @param message:信息
     * @return
     */
    public static ResultUtil failed(String message){
        return new ResultUtil(FAILED_CODE,FAILED,null,message);
    }

     /**
     * 返回失败有信息
     * @param code:状态吗
     * @param message:信息
     * @return
     */
    public static ResultUtil failed(Integer code,String message){
        return new ResultUtil(code,FAILED,null,message);
    }

    /**
     * response返回数据
     * @param resultUtil:返回的数据体
     * @param response:response响应
     */
    public static void Response(ResultUtil resultUtil,ServletResponse response){
        // 设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = null;
        try {
            // 返回数据
            writer = response.getWriter();
            String result = new Gson().toJson(resultUtil);
            log.info("返回的信息:"+result);
            writer.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
