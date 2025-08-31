package com.sorhive.comprojectserver.member.command.domain.model.avatarimage;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

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
public class AvatarImage {

    @Id
    @Column(name = "avatar_image_id")
    private Long id;

    @Column(name = "avatar_image_path")
    private String path;

    @Column(name = "avatar_original_name")
    private String orginalName;

    @Column(name = "avatar_saved_name")
    private String savedName;

    @Column(name = "avatar_upload_time")
    private Timestamp uploadTime;

    public AvatarImage() {
    }

    public AvatarImage(Long memberCode, String path, String orginalName, String savedName) {
        setId(memberCode);
        setPath(path);
        setOrginalName(orginalName);
        setSavedName(savedName);
        this.uploadTime = new Timestamp(System.currentTimeMillis());
    }
    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getOrginalName() {
        return orginalName;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setId(Long id) { this.id = id; }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOrginalName(String orginalName) {
        this.orginalName = orginalName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }
}
