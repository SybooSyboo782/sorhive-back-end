package com.sorhive.comprojectserver.feed.command.domain.model.feedhoney;

import com.sorhive.comprojectserver.feed.command.domain.model.feed.FeedId;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : FeedHoney
 * Comment: 피드 허니 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-16       부시연           허니 구분을 위해 피드 허니로 명칭 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_feed_honey")
@Getter
public class FeedHoney {

    @Id
    @Column(name="feed_honey_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_honey_create_time")
    private Timestamp createTime;

    @Column(name = "feed_honey_delete_time")
    private Timestamp deleteTime;

    @Column(name = "feed_honey_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    @Embedded
    private FeedId feedId;

    @Embedded
    private MemberCode memberCode;

    protected FeedHoney() {}

    public FeedHoney(MemberCode memberCode, FeedId feedId) {
        this.feedId = feedId;
        this.memberCode = memberCode;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
    }

    public void setDeleteYn(Character deleteYn) {
        this.deleteYn = deleteYn;
        this.deleteTime = new Timestamp(System.currentTimeMillis());
    }
}
