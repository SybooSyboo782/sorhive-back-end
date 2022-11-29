package com.sorhive.comprojectserver.chatting.exception;
/**
 * <pre>
 * Class : NoMongoChattingException
 * Comment: 몽고 채팅이 존재 하지 않을 때 발생하는 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-19       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public class NoMongoChattingException extends RuntimeException {

    public NoMongoChattingException() {
        super();
    }

    public NoMongoChattingException(String message) {
        super(message);
    }

    public NoMongoChattingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMongoChattingException(Throwable cause) {
        super(cause);
    }

}