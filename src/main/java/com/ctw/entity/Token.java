package com.ctw.entity;


import java.util.UUID;

/**
 * @author cjq
 * @program: TokenDemo
 * @description: Token和用户是一一对应的，并且Token是唯一的。
 *  Token中需要包含类型标识（id）和唯一的标识（uuid）。
 *  Token请求后台API的对象包含用户，开发者还有设备，设备在使用HTTP上传数据时，
 *  也需要携带Token，Token也需要类型区分（type）。
 *  Token的组成如下：Token = id_type_uuid
 * @date 2021-04-26 20:54:12
 */
public class Token {

    public enum Type {
        TT_USER("普通用户"),
        TT_ADMIN("管理员"),
        TT_DEVELOP("开发者"),
        TT_DEVICE("设备");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 当type为
     */
    private long id;

    //token类型
    private Type type;

    //唯一标识
    private String uuid;

    public Token(String value) {
        String[] parameters = value.split("-");
        if (parameters.length != 3) {
            throw new IllegalArgumentException("invalid token value");
        }
        id = Long.parseLong(parameters[0]);
        type = Type.valueOf(parameters[1]);
        uuid = parameters[2];
    }

    public Token(long id, Type type) {
        this(id, type, UUID.randomUUID().toString().replace("-", ""));
    }

    public Token(long id, Type type, String uuid) {
        this.id = id;
        this.type = type;
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return String.valueOf(id) + '-' + type + '-' + uuid;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
