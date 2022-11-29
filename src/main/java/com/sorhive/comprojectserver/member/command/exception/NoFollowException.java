package com.sorhive.comprojectserver.member.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * Class : NoFollowException
 * Comment: 해당 팔로우가 없을 때 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-10-31       부시연           최초 생성
 * 2022-11-10       부시연           메시지 추가
 * 2022-11-24       부시연           HttpStatus 상태값 반환 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "해당 팔로우는 존재 하지 않습니다")
public class NoFollowException extends RuntimeException {
    public NoFollowException() {
        super();
    }

    public NoFollowException(String message) {
        super(message);
    }

    public NoFollowException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFollowException(Throwable cause) {
        super(cause);
    }

}
