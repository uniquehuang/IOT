package com.ctw.interceptor;

import com.ctw.entity.Result;
import com.ctw.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Result<Object> result = new Result<>();
        //获取请求头中的令牌
        String token = request.getHeader("token");
        try {
            return JWTUtil.verifyToken(token);//验证token
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            result.failure("token算法不一致");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            result.failure("token过期");
        } catch (Exception e) {
            e.printStackTrace();
            result.failure("token无效");
        }
        String json = new ObjectMapper().writeValueAsString(result);
        response.setContentType("application/json;character=UTF-8");
        response.getWriter().println(json);
//        return false;
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}