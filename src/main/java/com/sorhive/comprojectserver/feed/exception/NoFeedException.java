package com.sorhive.comprojectserver.feed.exception;
/**
 * <pre>
 * Class : NoFeedException
 * Comment: 해당 피드가 없을 때 발생하는 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public class NoFeedException extends RuntimeException {
    public NoFeedException() {
        super();
    }

    public NoFeedException(String message) {
        super(message);
    }

    public NoFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFeedException(Throwable cause) {
        super(cause);
    }

}