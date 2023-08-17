package com.xkong.paper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AWSS3Config {
    @Value("${aws.s3.xiangrui.accesskey}")
    private String accessKey;
    @Value("${aws.s3.xiangrui.secretkey}")
    private String secretKey;

    public S3Client getS3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder().region(Region.EU_WEST_1).credentialsProvider(() -> credentials).build();
    }

    public S3Presigner getS3Presigner() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder().region(Region.EU_WEST_1).credentialsProvider(() -> credentials).build();
    }

}
