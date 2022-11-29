package com.sorhive.comprojectserver.lifing.command.application.dto;

import lombok.Getter;

/**
 * <pre>
 * Class : RequestLifingImageAiDto
 * Comment: AI 서버에 보내는 라이핑 이미지 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-11       부시연           최초 생성
 * 2022-11-16       부시연           라이핑 URL 리스트로 변경
 * 2022-11-19       부시연           라이핑 URL 문자열로 다시 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class LifingImageAiRequestDto {
    private String url;

    protected LifingImageAiRequestDto () {}

    public LifingImageAiRequestDto(String lifingImagePath) {
        this.url = lifingImagePath;
    }
}
