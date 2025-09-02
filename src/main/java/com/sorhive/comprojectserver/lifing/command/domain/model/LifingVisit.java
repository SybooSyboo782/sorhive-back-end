package com.sorhive.comprojectserver.lifing.command.domain.model;

import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LifingVisit {

    @Id
    @Column(name="lifing_visit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LifingVisitId;

    @Column(name = "lifing_visit_time")
    private LocalDateTime LifingVisitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lifing_writer_id")
    private Member lifingWriter;

    @Column(name = "lifing_visit_yn")
    @ColumnDefault("'N'")
    private Character lifingVisitYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_lifing_id")
    private Lifing lifing;

    public LifingVisit(Member lifingWriter,Lifing lifing) {
        this.lifingWriter = lifingWriter;
        this.lifing = lifing;
        this.LifingVisitTime = LocalDateTime.now();
        this.lifingVisitYn = 'Y';
    }

}
