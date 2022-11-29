package com.sorhive.comprojectserver.lifing.command.domain.model.lifing;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifingcomment.LifingComment;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingimage.LifingImage;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingvisit.LifingVisit;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class : Lifing
 * Comment: 라이핑 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-12       부시연           총 허니 개수 추가
 * 2022-11-15       부시연           총 허니 개수 null 값 대응
 * 2022-11-16       부시연           분석된 라이핑 이미지 컬럼 추가 && 라이핑 이미지 연관관계 매핑
 * 2022-11-17       부시연           라이핑 삭제 기능 추가
 * 2022-11-22       부시연           분석된 라이핑 이미지 컬럼 제거
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_lifings")
@Getter
public class Lifing {

    @Id
    @Column(name="lifing_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifingId;

    @Column(name = "lifing_content")
    private String lifingConetent;

    @Column(name = "lifing_no")
    private Long lifingNo;

    @Column(name = "lifing_category_no")
    private Long lifingCategoryNo;

    private LifingWriter lifingWriter;

    @Column(name = "lifing_create_time")
    private Timestamp createTime;

    @Column(name = "lifing_upload_time")
    private Timestamp uploadTime;

    @Column(name = "lifing_delete_time")
    private Timestamp deleteTime;

    @Column(name = "lifing_delete_yn")
    @ColumnDefault(value = "'N'")
    private Character deleteYn;

    @OneToMany(mappedBy = "lifing", cascade = CascadeType.ALL)
    private List<LifingVisit> lifingVisitList = new ArrayList<LifingVisit>();

    @Column(name = "honey_count")
    @ColumnDefault("0")
    private Integer honeyCount;

    @OneToMany(mappedBy = "lifing", cascade = CascadeType.ALL)
    private List<LifingComment> lifingComments = new ArrayList<>();

    @OneToMany(mappedBy = "lifing", cascade = CascadeType.ALL)
    private List<LifingImage> lifingImages = new ArrayList<>();

    protected Lifing() { }

    public Lifing(LifingWriter lifingWriter, Long lifingNo, Long lifingCategoryNo, String lifingConetent) {
        this.lifingWriter = lifingWriter;
        this.lifingNo = lifingNo;
        this.lifingConetent = lifingConetent;
        this.lifingCategoryNo = lifingCategoryNo;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
    }

    public Lifing(LifingWriter lifingWriter, String lifingConetent) {
        this.lifingWriter = lifingWriter;
        this.lifingConetent = lifingConetent;
        this.lifingNo = -1L;
        this.lifingCategoryNo = -1L;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
    }

    public Lifing(LifingWriter lifingWriter) {
        this.lifingWriter = lifingWriter;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';

    }

    /** 허니 총 수 계산 */
    public void countingHoney(Integer honeyCount) {

        /* 허니 총 수가 null 일 경우 0으로 초기화 */
        if(this.honeyCount == null) {
            this.honeyCount = 0;
        }
        this.honeyCount += honeyCount;
    }

    /** 라이핑 삭제 */
    public void delete(char deleteYn) {
        this.deleteTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = deleteYn;
    }

    public void createNewAiLifing(LifingWriter lifingWriter, Long lifingNo, Long lifingCategoryNo, String lifingConetent) {
        this.lifingWriter = lifingWriter;
        this.lifingNo = lifingNo;
        this.lifingCategoryNo = lifingCategoryNo;
        this.lifingConetent = lifingConetent;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
    }
}
