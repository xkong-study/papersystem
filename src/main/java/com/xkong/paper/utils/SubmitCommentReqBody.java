package com.xkong.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitCommentReqBody {
    private String username;
    private String avatar;
    private Integer rating;
    private String comment;
    private String title;
}
