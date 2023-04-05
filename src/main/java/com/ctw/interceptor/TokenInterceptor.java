package com.ctw.interceptor;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ctw.entity.Token;
import com.ctw.service.TokenManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author cjq
 * @program: TokenDemo
 * @description: Token拦截器，用于身份认证
 * @date 2021-05-14 09:36:54
 */
@Component
public class TokenInterceptor extends BaseInterceptor {

    private final TokenManager tokenManager;

    public TokenInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //首先判断是否需要进行token拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(FreeTokenAuthentication.class) != null) {
            return true;
        }

        //其他判断header中是否包含正确的token
        String tokenValue = request.getHeader(Constants.TAG_TOKEN_VALUE);
        if (StringUtils.isNotEmpty(tokenValue) && !tokenValue.equals("null")) {
            Token token = new Token(tokenValue);
            if (tokenManager.checkToken(token)) {
                request.setAttribute(Constants.CURRENT_USER_ROLE, token.getType());
                return true;
            }
        }

        //拦截后提示
        showInterceptionInfo(response, -1, "请先登录");
        return false;
    }
}
