package com.sorhive.comprojectserver.ai.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 * <pre>
 * Class : AILearningResponseDto
 * Comment: AI 학습 반환 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class AILearningLifingResponseDto {

    @JsonIgnore
    private Long lifingImageId;

    private String lifingImagePath;

    private Long analyzedLifingNo1;
    private Double analyzedLifingScore1;

    private Long analyzedLifingNo2;
    private Double analyzedLifingScore2;

    private Long analyzedLifingNo3;
    private Double analyzedLifingScore3;

    private AILifingData aiLifingData;

}
