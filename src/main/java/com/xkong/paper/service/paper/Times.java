package com.xkong.paper.service.paper;

import com.xkong.paper.utils.ChartData;
import com.xkong.paper.utils.ResBody;
import com.xkong.paper.utils.TimesResBody;

public interface Times {
    TimesResBody getSaveTimes(String title, String month);
    TimesResBody getDownloadTimes(String title, String month);
    TimesResBody getCiteTimes(String title, String month);
    ChartData getChartData(String title);
    ChartData getAllChartData(String author);

    ResBody addSaveTimes(String title, String month);
    ResBody addDownloadTimes(String title, String month);
    ResBody addCiteTimes(String title, String month);
    void resetTimes();
}
