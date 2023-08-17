package com.xkong.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitCommentResBody {
    private String status;
    private String message;
    private Comment comment;
}
