package com.xkong.paper.service.paper;

import com.xkong.paper.dao.Paper;
import org.springframework.web.multipart.MultipartFile;

public interface Upload {
    String uploadPaper(MultipartFile multipartFile, String author);
    String uploadPaperData(Paper paper);
}
