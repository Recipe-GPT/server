package com.recipe.gpt.app.domain.board;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.recipe.gpt.common.exception.S3FileUploadException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${recipe.gpt.cloudFront.distributionDomain}")
    private String cloudfrontDomain;

    public String upload(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // s3 업로드
        try {
            amazonS3Client.putObject(
                new PutObjectRequest(
                    bucket,
                    fileName,
                    file.getInputStream(),
                    metadata).withCannedAcl(CannedAccessControlList.PublicRead
                )
            );
        } catch (IOException e) {
            throw new S3FileUploadException();
        }

        String path = amazonS3Client.getUrl(bucket, fileName).getPath();
        return s3UrlToCloudFrontDomain(path);
    }

    private String s3UrlToCloudFrontDomain(String path) {
        return cloudfrontDomain + path;
    }

}
