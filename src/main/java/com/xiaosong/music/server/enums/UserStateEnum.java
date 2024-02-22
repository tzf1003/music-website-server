package com.xiaosong.music.server.enums;

public enum UserStateEnum {
    NO_VERIFICATION_EMAIL(0,"未验证邮件"),
    NORMAL_ACCOUNT(1,"账号正常"),
    ACCOUNT_BANNED(2,"账号被封禁");
    /**
     * 状态代码
     */
    private final int stateCode;

    /**
     * 状态描述
     */
    private final String stateMsg;
    UserStateEnum(int stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}
