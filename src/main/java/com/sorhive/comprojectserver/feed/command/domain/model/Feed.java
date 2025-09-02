package com.sorhive.comprojectserver.feed.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class : Feed
 * Comment: 피드 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-16       부시연           피드 총 수 계산
 * 2022-11-19       부시연           피드 삭제
 * 2022-11-20       부시연           피드 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_feeds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends SoftDeleteEntity {

    @Id
    @Column(name="feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(name = "feed_content")
    private String feedContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member feedWriter;

    @Column(name = "feed_honey_count")
    @ColumnDefault("0")
    private Integer feedHoneyCount;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    protected List<FeedComment> feedComments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    protected List<FeedImage> feedImages = new ArrayList<>();

    public Feed(String feedContent, Member feedWriter) {
        this.feedContent = feedContent;
        this.feedWriter = feedWriter;
    }

    /** 허니 총 수 계산 */
    public void countingFeedHoney(Integer feedHoneyCount) {

        /* 허니 총 수가 null 일 경우 0으로 초기화 */
        if (this.feedHoneyCount == null) {
            this.feedHoneyCount = 0;
        }
        this.feedHoneyCount += feedHoneyCount;
    }

    public void modify(String feedContent) {
        this.feedContent = feedContent;
    }
}
