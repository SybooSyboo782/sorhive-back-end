package com.sorhive.comprojectserver.harvest.command.domain.model.harvestimage;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * <pre>
 * Class : FeedImage
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_harvest_images")
public class HarvestImage {

    @EmbeddedId
    private HarvestImageId id;

    @Column(name = "harvest_image_path")
    private String path;

    @Column(name = "lifing_original_name")
    private String orginalName;

    @Column(name = "lifing_saved_name")
    private String savedName;

    public HarvestImage() {
    }

    public HarvestImage(HarvestImageId id, String path, String orginalName, String savedName) {
        this.id = id;
        this.path = path;
        this.orginalName = orginalName;
        this.savedName = savedName;
    }

    public HarvestImageId getId() {
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
}