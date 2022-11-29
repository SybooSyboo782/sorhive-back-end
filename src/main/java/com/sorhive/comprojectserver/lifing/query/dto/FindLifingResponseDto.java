package com.sorhive.comprojectserver.lifing.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 * Class : FindLifingResponseDto
 * Comment: 라이핑 상세 조회 반환 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * 2022-11-15       부시연           라이핑 댓글 목록 추가
 * 2022-11-16       부시연           라이핑 이미지 목록 추가
 * 2022-11-23       부시연           회원 상세 조회 기능 변경으로 응답 타입 변경
 * 2022-11-23       부시연           라이핑 이미지 1장으로 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
@Setter
public class FindLifingResponseDto {

    private LifingSummary lifingSummary;

    @JsonIgnore
    private List<LifingCommentData> lifingCommentDataList;

    private LifingImagePath lifingImagePath;

}
