package com.xkong.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResBody {
    List<SearchResultsContent> searchResults;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SearchResultsContent {
        String key;
        String title;
        String name;
        String source;
        Date date;
    }
}
