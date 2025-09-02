package com.sorhive.comprojectserver.lifing.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Class : LifingHoney
 * Comment: 라이핑 허니 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-16       부시연           허니 구분을 위해 라이핑 허니로 명칭 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_lifing_honey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LifingHoney extends SoftDeleteEntity {

    @Id
    @Column(name="lifing_honey_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lifing_id")
    private Lifing lifing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public LifingHoney(Member member, Lifing lifing) {
        this.member = member;
        this.lifing = lifing;
    }
}
