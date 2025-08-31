package com.sorhive.comprojectserver.lifing.command.domain.model.lifinghoney;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.LifingId;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import java.sql.Timestamp;

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
public class LifingHoney {

    @Id
    @Column(name="lifing_honey_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lifing_honey_create_time")
    private Timestamp createTime;

    @Column(name = "lifing_honey_delete_time")
    private Timestamp deleteTime;

    @Column(name = "lifing_honey_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    @Embedded
    private LifingId lifingId;

    @Embedded
    private MemberCode memberCode;

    protected LifingHoney() {}

    public LifingHoney(MemberCode memberCode, LifingId lifingId) {
        this.lifingId = lifingId;
        this.memberCode = memberCode;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
    }

    public void setDeleteYn(Character deleteYn) {
        this.deleteYn = deleteYn;
        this.deleteTime = new Timestamp(System.currentTimeMillis());
    }
}
