package com.xkong.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCommentResBody {
    private String title;
    private String abstracts;
    private List<Comment> comments;
}
