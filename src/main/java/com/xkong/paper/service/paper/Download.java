package com.xkong.paper.service.paper;

import java.util.List;

public interface Download {
    List<String> getAllPaperUrl(String author);
    String getPaperUrlByTitle(String title);
}
