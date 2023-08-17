package com.xkong.paper.controller;

import com.xkong.paper.dao.Paper;
import com.xkong.paper.service.paper.Upload;
import com.xkong.paper.utils.ResBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/paper")
public class UploadController {

    @Autowired
    private Upload uploadService;

    @PostMapping("/upload/paper_data")
    public ResBody uploadPaper(@RequestBody Paper sciencePaper) {
        String message = uploadService.uploadPaperData(sciencePaper);
        if (!"ok".equals(message)) {
            return new ResBody(false, message);
        }
        return new ResBody(true, message);
    }

    @PostMapping("/upload/paper")
    public ResBody uploadPaperData(@RequestPart("author") String author, @RequestPart("paper") MultipartFile multipartFile) {
        String message = uploadService.uploadPaper(multipartFile, author);
        if (!"ok".equals(message)) {
            return new ResBody(false, message);
        }
        return new ResBody(true, message);
    }

}
