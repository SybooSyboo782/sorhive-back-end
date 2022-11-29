package com.sorhive.comprojectserver.lifing.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingvisit.LifingVisit;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class : LifingSummary
 * Comment: 라이핑 데이터 전송객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-16       부시연           최초 생성
 * 2022-11-22       부시연           분석된 라이핑 번호 제거
 * 2022-11-23       부시연           회원 상세 조회 기능 변경으로 JsonIgnore 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class LifingSummary {

    private Long lifingId;

    private String lifingConetent;

    private Long lifingNo;

    private Long lifingCategoryNo;

    private Long lifingWriterCode;

    private String lifingWriterId;

    private String lifingWriterName;

    private Timestamp uploadTime;

    @JsonIgnore
    private Character deleteYn;

    @JsonIgnore
    private List<LifingVisit> lifingVisitList = new ArrayList<LifingVisit>();

    @JsonIgnore
    private List<LifingCommentData> lifingCommentData = new ArrayList<>();

    @JsonIgnore
    private List<LifingImagePath> lifingImages = new ArrayList<>();

    protected LifingSummary() {}



}
