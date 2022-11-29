package com.sorhive.comprojectserver.lifing.command.domain.model.lifingimage;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.Lifing;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : LifingImage
 * Comment: 라이핑 이미지 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-15       부시연           분석된 라이핑 이미지 컬럼 제거 && 라이핑 컬럼 연관관계 매핑
 * 2022-11-17       부시연           라이핑 이미지 삭제여부 및 삭제 시간 추가
 * 2022-11-22       부시연           분석된 라이핑 이미지와 분석 점수 1,2,3 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_lifing_image")
@DynamicInsert
@DynamicUpdate
@Getter
public class LifingImage {

    @Id
    @Column(name = "lifing_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifingImageId;

    @Column(name = "lifing_image_path")
    private String lifingImagePath;

    @Column(name = "lifing_original_name")
    private String lifingImageOrginalName;

    @Column(name = "lifing_saved_name")
    private String lifingImageSavedName;

    @Column(name = "lifing_upload_time")
    private Timestamp lifingImageUploadTime;

    @Column(name = "lifing_delete_time")
    private Timestamp lifingImageDeleteTime;

    @Column(name = "lifing_delete_yn")
    @ColumnDefault("'N'")
    private Character lifingImageDeleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lifing_id")
    private Lifing lifing;

    @Column(name = "anaylzed_lifing_no1")
    @ColumnDefault("-1")
    private Long analyzedLifingNo1;

    @Column(name = "anaylzed_lifing_score1")
    @ColumnDefault("-1")
    private Double analyzedLifingScore1;

    @Column(name = "anaylzed_lifing_no2")
    @ColumnDefault("-1")
    private Long analyzedLifingNo2;

    @Column(name = "anaylzed_lifing_score2")
    @ColumnDefault("-1")
    private Double analyzedLifingScore2;

    @Column(name = "anaylzed_lifing_no3")
    @ColumnDefault("-1")
    private Long analyzedLifingNo3;

    @Column(name = "anaylzed_lifing_score3")
    @ColumnDefault("-1")
    private Double analyzedLifingScore3;

    @Column(name = "lifing_learning_yn")
    @ColumnDefault("'N'")
    private Character lifingLearningYn;

    protected LifingImage() { }

    public LifingImage(String lifingImagePath, String originalName, String changeName, Long analyzedLifingNo1, Double analyzedLifingScore1, Long analyzedLifingNo2, Double analyzedLifingScore2, Long analyzedLifingNo3, Double analyzedLifingScore3, Lifing lifingId) {
        this.lifingImagePath = lifingImagePath;
        this.lifingImageOrginalName = originalName;
        this.lifingImageSavedName = changeName;
        this.analyzedLifingNo1 = analyzedLifingNo1;
        this.analyzedLifingScore1 = analyzedLifingScore1;
        this.analyzedLifingNo2 = analyzedLifingNo2;
        this.analyzedLifingScore2 = analyzedLifingScore2;
        this.analyzedLifingNo3 = analyzedLifingNo3;
        this.analyzedLifingScore3 = analyzedLifingScore3;
        this.lifingImageUploadTime = new Timestamp(System.currentTimeMillis());
        this.lifing = lifingId;
    }

    public void deleteImage(char deleteYn) {
        this.lifingImageDeleteYn = deleteYn;
        this.lifingImageDeleteTime = new Timestamp(System.currentTimeMillis());
    }

    public void learningYn(char lifingLearningYn) {
        this.lifingLearningYn = lifingLearningYn;
    }
}
