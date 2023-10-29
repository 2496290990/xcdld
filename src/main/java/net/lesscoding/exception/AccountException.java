package net.lesscoding.exception;

/**
 * @author eleven
 * @date 2023/10/29 21:20
 * @apiNote
 */
public class AccountException extends RuntimeException{

    public AccountException(String message) {
        super(message);
    }

    public static AccountException delException() {
        return new AccountException("账号已被删除或不存在");
    }
    public static AccountException singOut() {
        return new AccountException("账号已注销");
    }
    public static AccountException blackList() {
        return new AccountException("账号已被封禁");
    }

    public static AccountException notAllow() {
        return new AccountException("账号密码不正确");
    }
}
