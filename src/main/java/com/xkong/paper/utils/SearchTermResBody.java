package com.xkong.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchTermResBody {
    private String title;
    private List<String> name;
    private List<String> tags;
    private Date date;
}
