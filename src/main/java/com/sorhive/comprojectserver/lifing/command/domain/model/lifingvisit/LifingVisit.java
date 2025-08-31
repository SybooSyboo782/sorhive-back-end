package com.sorhive.comprojectserver.lifing.command.domain.model.lifingvisit;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.Lifing;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.LifingWriter;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : LifingVisit
 * Comment: 라이핑 방문 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Entity
@Table(name = "tbl_lifing_visits")
@Getter
public class LifingVisit {

    @Id
    @Column(name="lifing_visit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LifingVisitId;

    @Column(name = "lifing_visit_time")
    private Timestamp LifingVisitTime;

    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "lifing_visitor_code"))
    )
    @Embedded
    private MemberCode memberCode;

    @Embedded
    @AttributeOverride(name = "memberCode", column = @Column(name = "lifing_writer_code"))
    @AttributeOverride(name = "name", column = @Column(name = "lifing_writer_name"))
    @AttributeOverride(name = "id", column = @Column(name = "lifing_writer_id"))
    private LifingWriter lifingWriter;

    @Column(name = "lifing_visit_yn")
    @ColumnDefault("'N'")
    private Character lifingVisitYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_lifing_id")
    private Lifing lifing;

    protected LifingVisit() {}

    public LifingVisit(MemberCode memberCode, LifingWriter lifingWriter, Lifing lifing) {
        this.memberCode = memberCode;
        this.lifingWriter = lifingWriter;
        this.lifing = lifing;
        this.LifingVisitTime = new Timestamp(System.currentTimeMillis());
        this.lifingVisitYn = 'Y';
    }

}
