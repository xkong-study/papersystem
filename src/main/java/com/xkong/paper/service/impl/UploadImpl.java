package com.xkong.paper.service.impl;

import com.xkong.paper.config.AWSS3Config;
import com.xkong.paper.dao.Paper;
import com.xkong.paper.service.paper.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class UploadImpl implements Upload {
    @Autowired
    private AWSS3Config awsS3Config;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String BUCKET_NAME = "xkongpaper";

    @Override
    public String uploadPaper(MultipartFile multipartFile, String author) {
        S3Client s3Client = awsS3Config.getS3Client();
        try{
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(author + "/" + multipartFile.getOriginalFilename())
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(multipartFile.getBytes()));
            return "ok";
        } catch (IOException e) {
            return "Upload to S3 bucket failed";
        }
    }

    @Override
    public String uploadPaperData(Paper paper) {
        try{
            mongoTemplate.insert(paper);
        } catch (Exception e){
            return "Upload to MongoDB failed";
        }
        return "ok";
    }

//    private String generatePaperName(MultipartFile multiPartFile) {
//        return new Date().getTime() + "-" + Objects.requireNonNull(multiPartFile.getOriginalFilename()).replace(" ", "_");
//    }
}
