package com.sorhive.comprojectserver.member.command.domain.model.avatar;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

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
public class Avatar {

    @Id
    @Column(name="avatar_id")
    private Long id;

    @Column(name = "avatar_face_type")
    private Integer faceType;

    @Column(name = "avatar_eyebrows_type")
    private Integer eyeBrowsType;

    @Column(name = "avatar_eye_type")
    private Integer eyeType;

    @Column(name = "avatar_hair_type")
    private Integer hairType;

    @Column(name = "avatar_create_time")
    private Timestamp createTime;

    @Column(name = "avatar_upload_time")
    private Timestamp uploadTime;

    @Column(name = "avatar_delete_time")
    private Timestamp deleteTime;

    @Column(name = "avatar_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    protected Avatar() {}

    public Avatar(Long id, Integer faceType, Integer eyeBrowsType, Integer eyeType, Integer hairType) {
        this.id = id;
        this.faceType = faceType;
        this.eyeBrowsType = eyeBrowsType;
        this.eyeType = eyeType;
        this.hairType = hairType;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

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

