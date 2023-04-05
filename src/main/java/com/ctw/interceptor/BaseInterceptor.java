package com.ctw.interceptor;


import com.ctw.service.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonObject;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cjq
 * @program: TokenDemo
 * @description: 为拦截器提供一些基础方法
 * @date 2021-05-17 16:07:43
 */
public class BaseInterceptor implements HandlerInterceptor {

    public void showInterceptionInfo(HttpServletResponse response, String msg) {
        showInterceptionInfo(response, 0, msg);
    }

    public void showInterceptionInfo(HttpServletResponse response, int status, String msg) {
        showInterceptionInfo(response, status, msg, null);
    }

    public void showInterceptionInfo(HttpServletResponse response, int status, String msg, Object data) {
        Result result = new Result();
        result.setStatus(status);
        result.setMsg(msg);
        result.setData(data);
        PrintWriter out = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type",
                    "application/json;charset=UTF-8");
            out = response.getWriter();
//            out.write(JSONobject.fromObject(result).toString());
            out.write(mapper.writeValueAsString(result));
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
