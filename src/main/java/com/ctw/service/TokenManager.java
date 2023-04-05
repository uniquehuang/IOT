package com.ctw.service;


import com.ctw.entity.Token;
import org.springframework.stereotype.Service;

/**
 * @author cjq
 * @program: TokenDemo
 * @description: 用于提供Token的相关功能
 * @date 2021-05-06 16:02:18
 */
@Service
public class TokenManager {

    private final RedisService redisService;

    public TokenManager(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 创建一个token关联指定用户（设备）
     * @param id 指定用户（设备）的id
     * @param type token类型
     * @return 生成的token
     */
    public Token createToken(Integer id, Token.Type type) {
        Token token = new Token(id, type);
        redisService.set(token.getUuid(), token.getValue(),
                type == Token.Type.TT_DEVICE ? 3600 * 24 : 3600);
        return token;
    }

    /**
     * @see #checkToken(Token)
     * @param id 指定用户（设备）的id
     * @param type token类型
     * @return
     */
    public boolean checkToken(long id, Token.Type type) {
        return checkToken(new Token(id, type));
    }

    /**
     * @see #checkToken(Token)
     * @param tokenValue
     * @return
     */
    public boolean checkToken(String tokenValue) {
        try {
            return checkToken(new Token(tokenValue));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 检查token是否有效
     * @param token 用户端发送来的token
     * @return 是否有效
     */
    public boolean checkToken(Token token) {
        return token.getValue().equals(redisService.get(token.getUuid()));
    }

    public void deleteToken(Token token) {
        deleteToken(token.getUuid());
    }

    public void deleteToken(String uuid) {
        redisService.delete(uuid);
    }
}
