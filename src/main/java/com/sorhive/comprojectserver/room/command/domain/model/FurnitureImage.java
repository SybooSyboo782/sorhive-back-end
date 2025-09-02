package com.sorhive.comprojectserver.room.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.room.command.domain.model.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FurnitureImage extends SoftDeleteEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public FurnitureImage(String path, String orginalName, String savedName, Integer imageNo, Room room) {
        this.path = path;
        this.orginalName = orginalName;
        this.savedName = savedName;
        this.imageNo = imageNo;
        this.room = room;
    }
}
