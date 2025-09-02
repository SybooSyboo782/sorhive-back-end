package com.sorhive.comprojectserver.room.query;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * <pre>
 * Class : FurnitureImageData
 * Comment: 가구 이미지 조회 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Entity
@Table(name = "tbl_furniture_images")
@Getter
public class FurnitureImageData extends SoftDeleteEntity {

    @Id
    @Column(name = "furniture_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long furnitureImageId;

    @Column(name = "furniture_image_path")
    private String furnitureImagePath;

    @Column(name = "furniture_image_no")
    private Integer furnitureImageNo;

    @Column(name = "fk_room_id")
    private Long roomId;

}
