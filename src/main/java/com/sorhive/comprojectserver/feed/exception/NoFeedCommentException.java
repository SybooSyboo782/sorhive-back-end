package com.sorhive.comprojectserver.feed.exception;
/**
 * <pre>
 * Class : NoFeedCommentException
 * Comment: 해당 피드 댓글이 없을 때 발생하는 예외처리
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
public class NoFeedCommentException extends RuntimeException {
    public NoFeedCommentException() {
        super();
    }

    public NoFeedCommentException(String message) {
        super(message);
    }

    public NoFeedCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFeedCommentException(Throwable cause) {
        super(cause);
    }

}