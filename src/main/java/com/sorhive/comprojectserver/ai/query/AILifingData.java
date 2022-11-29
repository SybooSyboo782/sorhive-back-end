package com.sorhive.comprojectserver.ai.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 * <pre>
 * Class : AILifingData
 * Comment: AI 라이핑 데이터 전송객체
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
public class AILifingData {

    @JsonIgnore
    private Long lifingId;
    private Long lifingCategoryNo;

}
