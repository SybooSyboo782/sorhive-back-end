package com.sorhive.comprojectserver.feed.command.domain.model.feed;

import com.sorhive.comprojectserver.feed.command.domain.model.feedcomment.FeedComment;
import com.sorhive.comprojectserver.feed.command.domain.model.feedimage.FeedImage;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
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
public class Feed {

    @Id
    @Column(name="feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(name = "feed_content")
    private String feedContent;

    private FeedWriter feedWriter;

    @Column(name = "feed_create_time")
    private Timestamp feedCreateTime;

    @Column(name = "feed_upload_time")
    private Timestamp feedUploadTime;

    @Column(name = "feed_delete_time")
    private Timestamp feedDeleteTime;

    @Column(name = "feed_delete_yn")
    @ColumnDefault(value = "'N'")
    private Character feedDeleteYn;

    @Column(name = "feed_honey_count")
    @ColumnDefault("0")
    private Integer feedHoneyCount;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    protected List<FeedComment> feedComments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    protected List<FeedImage> feedImages = new ArrayList<>();

    protected Feed() { }

    public Feed(FeedWriter feedWriter, String feedContent) {

        this.feedWriter = feedWriter;
        this.feedContent = feedContent;
        this.feedCreateTime = new Timestamp(System.currentTimeMillis());
        this.feedUploadTime = new Timestamp(System.currentTimeMillis());
        this.feedDeleteYn = 'N';

    }

    /** 허니 총 수 계산 */
    public void countingFeedHoney(Integer feedHoneyCount) {

        /* 허니 총 수가 null 일 경우 0으로 초기화 */
        if(this.feedHoneyCount == null) {
            this.feedHoneyCount = 0;
        }
        this.feedHoneyCount += feedHoneyCount;
    }

    /** 피드 삭제 */
    public void delete() {
        
        this.feedDeleteYn = 'Y';
        this.feedDeleteTime = new Timestamp(System.currentTimeMillis());
        
    }
    
    /** 피드 수정 */
    public void modify(String feedContent) {
        
        this.feedContent = feedContent;
        this.feedUploadTime = new Timestamp(System.currentTimeMillis());
        
    }
}
