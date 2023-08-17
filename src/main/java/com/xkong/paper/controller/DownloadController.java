package com.xkong.paper.controller;

import com.xkong.paper.service.paper.Download;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/paper")
public class DownloadController {
    private final Download download;

    @Autowired
    public DownloadController(Download download) {
        this.download = download;
    }

    @GetMapping("/download/all")
    public List<String> downloadPaper(@RequestParam String author) {
        return download.getAllPaperUrl(author);
    }

    @GetMapping("/download/title")
    public String downloadPaperByTitle(@RequestParam("author") String author, @RequestParam("title") String title) {
        return download.getPaperUrlByTitle(author + "/" + title);
    }

}
