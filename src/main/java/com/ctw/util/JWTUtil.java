package com.ctw.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {

    private static final String SECRET = "token!##qw";//要绝对保密

    //获取token
    public static String getToken(Map<String, Object> map) {

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 600);

        String token = Jwts.builder()
                .setClaims(map)//有效荷载
                .setExpiration(instance.getTime())//有效时间
                .signWith(SignatureAlgorithm.HS512, SECRET)//加密方式及密钥
                .compact();

        System.out.println("token = " + token);
        return token;
    }

    //单纯验证token
    public static boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            return false;
        }
    }

    //验证token并返回载荷类
    public static Map<String, Object> getTokenInfo(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}