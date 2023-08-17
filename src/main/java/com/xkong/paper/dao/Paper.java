package com.xkong.paper.dao;

import com.xkong.paper.utils.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="Paper")
public class Paper {
    String title;
    String abstracts;
    String cite;
    Date date;
    List<String> tags;
    List<String> collaborators;
    List<Comment> comments;
}