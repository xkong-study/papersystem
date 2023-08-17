package com.xkong.paper.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {
    Map<String, Integer> downloads;
    Map<String, Integer> references;
    Map<String, Integer> favorites;
}
