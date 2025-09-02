package com.sorhive.comprojectserver.member.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * <pre>
 * Class : Avatar
 * Comment: 아바타 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-04       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name ="tbl_avatars")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar extends SoftDeleteEntity {

    @Id
    @Column(name="avatar_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "avatar_face_type")
    private Integer faceType;

    @Column(name = "avatar_eyebrows_type")
    private Integer eyeBrowsType;

    @Column(name = "avatar_eye_type")
    private Integer eyeType;

    @Column(name = "avatar_hair_type")
    private Integer hairType;

    public Avatar(Long id, Integer faceType, Integer eyeBrowsType, Integer eyeType, Integer hairType) {
        this.id = id;
        this.faceType = faceType;
        this.eyeBrowsType = eyeBrowsType;
        this.eyeType = eyeType;
        this.hairType = hairType;
    }

    public Long getId() {
        return id;
    }

    public Integer getFaceType() {
        return faceType;
    }

    public Integer getEyeBrowsType() {
        return eyeBrowsType;
    }

    public Integer getEyeType() {
        return eyeType;
    }

    public Integer getHairType() {
        return hairType;
    }
}

