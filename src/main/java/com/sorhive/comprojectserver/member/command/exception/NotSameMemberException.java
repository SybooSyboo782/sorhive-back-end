package com.sorhive.comprojectserver.member.command.exception;

/**
 * <pre>
 * Class : NotSameMemberException
 * Comment: 동일 회원에 대한 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       부시연           최초 생성
 * 2022-11-24       부시연           HttpStatus 상태값 반환 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public class NotSameMemberException extends RuntimeException{
    public NotSameMemberException() {
        super();
    }

    public NotSameMemberException(String message) {
        super(message);
    }

    public NotSameMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSameMemberException(Throwable cause) {
        super(cause);
    }
}
