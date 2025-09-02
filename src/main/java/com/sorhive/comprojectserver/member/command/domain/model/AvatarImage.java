package com.sorhive.comprojectserver.member.command.domain.model;

import com.sorhive.comprojectserver.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * <pre>
 * Class : AvatarImage
 * Comment: 아바타 이미지 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_avatar_images")
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvatarImage extends BaseEntity {

    @Id
    @Column(name = "avatar_image_id")
    private Long id;

    @Column(name = "avatar_image_path")
    private String path;

    @Column(name = "avatar_original_name")
    private String orginalName;

    @Column(name = "avatar_saved_name")
    private String savedName;

    public AvatarImage(Long id, String path, String orginalName, String savedName) {
        this.id = id;
        this.path = path;
        this.orginalName = orginalName;
        this.savedName = savedName;
    }
}
