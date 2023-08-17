package com.xkong.paper.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("PaperOperationTimes")
public class PaperTimes {
    String title;
    Map<String, Integer> downloadTimes;
    Map<String, Integer> citeTimes;
    Map<String, Integer> saveTimes;
}

