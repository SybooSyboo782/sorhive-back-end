package com.sorhive.comprojectserver.feed.command.domain.model.feedimage;

import com.sorhive.comprojectserver.feed.command.domain.model.feed.Feed;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : FeedImage
 * Comment: 피드 이미지 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-20       부시연           이미지 삭제
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_feed_images")
@Getter
public class FeedImage {

    @Id
    @Column(name="feed_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_image_path")
    private String path;

    @Column(name = "feed_image_original_name")
    private String orginalName;

    @Column(name = "feed_image_saved_name")
    private String savedName;

    @Column(name = "feed_image_upload_time")
    private Timestamp uploadTime;


    @Column(name = "feed_image_delete_time")
    private Timestamp deleteTime;

    @Column(name = "feed_image_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    protected FeedImage() { }

    public FeedImage(String feedImagePath, String originalName, String changeName, Feed feedId) {

        this.path = feedImagePath;
        this.orginalName = originalName;
        this.savedName = changeName;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.feed = feedId;
        this.deleteYn = 'N';
    }

    /** 이미지 삭제*/
    public void deleteImage() {

        this.deleteYn = 'Y';
        this.deleteTime = new Timestamp(System.currentTimeMillis());

    }

}
