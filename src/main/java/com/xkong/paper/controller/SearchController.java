package com.xkong.paper.controller;

import com.xkong.paper.dao.Paper;
import com.xkong.paper.service.paper.Search;
import com.xkong.paper.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paper")
public class SearchController {
    @Autowired
    private Search searchService;

    @GetMapping("/search")
    public List<SearchTermResBody> searchPaper(@RequestParam("searchTerm") String searchTerm) {
        return searchService.searchPaper(searchTerm);
    }

    @GetMapping("/search/comments")
    public List<SearchCommentResBody> searchComment(@RequestParam("title") String title) {
        return searchService.searchComment(title);
    }

    @PostMapping("/search/add/comment")
    public SubmitCommentResBody submitComment(@RequestBody SubmitCommentReqBody submitCommentReqBody) {
        return searchService.submitComment(submitCommentReqBody);
    }

    @GetMapping("/search/detail")
    public List<Paper> searchPaperDetail(@RequestParam("title") String title, @RequestParam("keyword") String keyword) {
        return searchService.searchPaperDetail(title, keyword);
    }

    @GetMapping("/search/detail/save")
    public List<Paper> searchPaperDetailSave(@RequestParam("tag") String tag) {
        return searchService.searchPaperDetailByTag(tag);
    }

    @GetMapping("/search/detail/all")
    public List<Paper> searchPaperDetailAll() {
        return searchService.searchPaperDetailAll();
    }

}
