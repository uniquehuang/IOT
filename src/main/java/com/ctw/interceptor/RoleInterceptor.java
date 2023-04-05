package com.ctw.interceptor;

import com.ctw.entity.Token;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author cjq
 * @program: TokenDemo
 * @description: 权限管理演示
 * @date 2021-05-17 15:20:13
 */
@Component
public class RoleInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Token.Type userRole = (Token.Type) request.getAttribute(Constants.CURRENT_USER_ROLE);
        if (userRole == null) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RoleAuthentication annotation = method.getAnnotation(RoleAuthentication.class);
        if (annotation == null) {
            return true;
        }

        if (userRole == annotation.role()) {
            return true;
        }

        showInterceptionInfo(response, -2, "您当前没有使用该功能的权限");
        return false;
    }
}
