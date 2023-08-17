package com.xkong.paper.service.paper;

import com.xkong.paper.dao.Paper;
import com.xkong.paper.utils.*;

import java.util.List;

public interface Search {
    List<SearchTermResBody> searchPaper(String searchTerm);
    List<SearchCommentResBody> searchComment(String title);
    SubmitCommentResBody submitComment(SubmitCommentReqBody submitCommentReqBody);
    List<Paper> searchPaperDetail(String title, String keyword);
    List<Paper> searchPaperDetailByTag(String tag);
    List<Paper> searchPaperDetailAll();
}
