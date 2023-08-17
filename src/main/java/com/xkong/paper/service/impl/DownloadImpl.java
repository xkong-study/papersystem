package com.xkong.paper.service.impl;

import com.xkong.paper.config.AWSS3Config;
import com.xkong.paper.service.paper.Download;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.util.List;

@Service
public class DownloadImpl implements Download {
    private final AWSS3Config awss3Config;
    private final String BUCKET_NAME = "xkongpaper";

    @Autowired
    public DownloadImpl(AWSS3Config awss3Config){
        this.awss3Config = awss3Config;
    }

    @Override
    public List<String> getAllPaperUrl(String author) {
        S3Client s3Client = awss3Config.getS3Client();
        S3Presigner s3Presigner = awss3Config.getS3Presigner();
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(BUCKET_NAME)
                .prefix(author)
                .build();
        return s3Client.listObjectsV2(listObjectsV2Request).contents().stream().map(s3Object -> {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(s3Object.key())
                    .build();
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofHours(1))
                    .getObjectRequest(getObjectRequest)
                    .build();
            return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
        }).toList();
    }

    @Override
    public String getPaperUrlByTitle(String title) {
        S3Presigner s3Presigner = awss3Config.getS3Presigner();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(title)
                .build();
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(getObjectRequest)
                .build();
        return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
    }
}
