package com.ctw.interceptor;

import com.ctw.annotation.Login;
import com.ctw.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String content = request.getHeader("content");
        if (!"login".equals(content)) return true;

        //判断该controller方法是否有@Login注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(Login.class) == null) return true;

        //拦截
        Result<Object> result = new Result<>();
        result.setResultMsg("当前请求被拦截");
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            out.write(s);
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
