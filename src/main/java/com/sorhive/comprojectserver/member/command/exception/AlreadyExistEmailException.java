package com.sorhive.comprojectserver.member.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * Class : AlreadyExistEmailException
 * Comment: 이미 이메일이 존재할 때 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * 2022-11-24       부시연           HttpStatus 상태값 반환 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 존재하고 있는 이메일입니다.")
public class AlreadyExistEmailException extends RuntimeException{
    public AlreadyExistEmailException() {
        super();
    }

    public AlreadyExistEmailException(String message) {
        super(message);
    }

    public AlreadyExistEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistEmailException(Throwable cause) {
        super(cause);
    }
}
