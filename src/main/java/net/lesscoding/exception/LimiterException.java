package net.lesscoding.exception;

/**
 * @author eleven
 * @date 2023/10/23 16:13
 * @apiNote
 */
public class LimiterException extends RuntimeException{
    public LimiterException(String message) {
        super(message);
    }
}
