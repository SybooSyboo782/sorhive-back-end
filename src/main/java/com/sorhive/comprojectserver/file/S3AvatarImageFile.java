package com.sorhive.comprojectserver.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * <pre>
 * Class : S3AvatarImageFile
 * Comment: 아바타 이미지 S3에 저장
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-10-24       부시연           최초 생성
 * 2022-11-11       부시연           바이트 배열로 대응
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Component
@RequiredArgsConstructor
public class S3AvatarImageFile {
    private static final Logger log = LoggerFactory.getLogger(S3AvatarImageFile.class);
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(byte[] avatarImage, String changeName, String dirName) throws IOException {

        ByteArrayInputStream input_stream= new ByteArrayInputStream(avatarImage);

        BufferedImage final_buffered_image = ImageIO.read(input_stream);

        ImageIO.write(final_buffered_image , "png", new File("./" + changeName) );

        File newFile = new File("./" + changeName);

        return upload(newFile, changeName, dirName);
    }

    private String upload(File uploadFile, String changeName, String dirName) {

        String fileName = dirName + "/" + changeName;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public void delete(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, "images/" + fileName);
        amazonS3Client.deleteObject(request);
    }
}
