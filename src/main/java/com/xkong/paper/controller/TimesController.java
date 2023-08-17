package com.xkong.paper.controller;

import com.xkong.paper.service.paper.Times;
import com.xkong.paper.utils.ChartData;
import com.xkong.paper.utils.ResBody;
import com.xkong.paper.utils.TimesResBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/times")
public class TimesController {
    @Autowired
    private Times timesService;

    @GetMapping("/get/save")
    public TimesResBody getSaveTimes(@RequestParam("title") String title, @RequestParam("month") String month) {
        return timesService.getSaveTimes(title, month);
    }

    @GetMapping("/get/download")
    public TimesResBody getDownloadTimes(@RequestParam("title") String title, @RequestParam("month") String month) {
        return timesService.getDownloadTimes(title, month);
    }

    @GetMapping("/get/cite")
    public TimesResBody getCiteTimes(@RequestParam("title") String title, @RequestParam("month") String month) {
        return timesService.getCiteTimes(title, month);
    }

    @GetMapping("/get/chart")
    public ChartData getChartData(@RequestParam("title") String title) {
        return timesService.getChartData(title);
    }

    @GetMapping("/get/all")
    public ChartData getAllChartData(@RequestParam("author") String author) {
        return timesService.getAllChartData(author);
    }

    @PostMapping("/add/save")
    public ResBody addSaveTimes(@RequestParam("title") String title, @RequestParam("month") String month) {
        return timesService.addSaveTimes(title, month);
    }

    @PostMapping("/add/download")
    public ResBody addDownloadTimes(@RequestParam("title") String title, @RequestParam("month") String month) {
        return timesService.addDownloadTimes(title, month);
    }

    @PostMapping("/add/cite")
    public ResBody addCiteTimes(@RequestParam("title") String title, @RequestParam("month") String month) {
        return timesService.addCiteTimes(title, month);
    }

}
