package com.sorhive.comprojectserver.room.command.domain.furnitureimage;

import com.sorhive.comprojectserver.room.command.domain.room.Room;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : FurnitureImage
 * Comment: 가구 이미지 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Entity
@Table(name = "tbl_furniture_images")
@Getter
public class FurnitureImage {

    @Id
    @Column(name = "furniture_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "furniture_image_path")
    private String path;

    @Column(name = "furniture_original_name")
    private String orginalName;

    @Column(name = "furniture_saved_name")
    private String savedName;

    @Column(name = "furniture_image_no")
    private Integer imageNo;

    @Column(name = "furniture_upload_time")
    private Timestamp uploadTime;

    @Column(name = "furniture_delete_time")
    private Timestamp deleteTime;

    @Column(name = "furniture_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_room_id")
    private Room room;

    protected FurnitureImage() { }

    public FurnitureImage(String path, String orginalName, String savedName, Integer imageNo, Room room) {

        this.path = path;
        this.orginalName = orginalName;
        this.savedName = savedName;
        this.imageNo = imageNo;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.room = room;
        this.deleteYn = 'N';

    }

    public void delete() {
        this.deleteTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'Y';
    }
}
