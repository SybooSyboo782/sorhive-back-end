package com.sorhive.comprojectserver.member.query.recommend;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * <pre>
 * Class : RecommendData
 * Comment: 추천 정렬 전송 객체
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
public class RecommendData {

    private Long recommendNo;
    private String recommendId;
    private Timestamp uploadTime;

    protected RecommendData() {}
}
