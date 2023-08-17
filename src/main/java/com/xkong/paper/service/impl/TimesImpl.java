package com.xkong.paper.service.impl;

import com.xkong.paper.dao.Paper;
import com.xkong.paper.dao.PaperTimes;
import com.xkong.paper.service.paper.Times;
import com.xkong.paper.utils.ChartData;
import com.xkong.paper.utils.ResBody;
import com.xkong.paper.utils.TimesResBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimesImpl implements Times {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String errorMessage = "no such paper";
    private static final String successMessage = "success";

    @Override
    public TimesResBody getSaveTimes(String title, String month) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        PaperTimes paperDownloadTimes = mongoTemplate.findOne(query, PaperTimes.class);
        if(paperDownloadTimes == null) {
            return new TimesResBody(false, errorMessage, null);
        } else{
            return new TimesResBody(true, successMessage, paperDownloadTimes.getSaveTimes().get(month));
        }
    }

    @Override
    public TimesResBody getDownloadTimes(String title, String month) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        PaperTimes paperTimes = mongoTemplate.findOne(query, PaperTimes.class);
        if(paperTimes == null) {
            return new TimesResBody(false, errorMessage, null);
        } else{
            return new TimesResBody(true, successMessage, paperTimes.getDownloadTimes().get(month));
        }
    }

    @Override
    public TimesResBody getCiteTimes(String title, String month) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        PaperTimes paperTimes = mongoTemplate.findOne(query, PaperTimes.class);
        if(paperTimes == null) {
            return new TimesResBody(false, errorMessage, null);
        } else{
            return new TimesResBody(true, successMessage, paperTimes.getCiteTimes().get(month));
        }
    }

    @Override
    public ChartData getChartData(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        PaperTimes paperTimes = mongoTemplate.findOne(query, PaperTimes.class);
        if(paperTimes != null) {
            return new ChartData(paperTimes.getDownloadTimes(), paperTimes.getCiteTimes(), paperTimes.getSaveTimes());
        } else{
            return null;
        }
    }

    @Override
    public ChartData getAllChartData(String author) {
        // Find all papers of this author
        Query query = new Query(Criteria.where("collaborators").in(author));
        List<String> titles = mongoTemplate.find(query, Paper.class)
                .stream()
                .map(Paper::getTitle)
                .toList();
        Map<String, Integer> downloadTimes = new HashMap<>(), citeTimes = new HashMap<>(), saveTimes = new HashMap<>();
        for(String title : titles) {
            Query query1 = new Query();
            query1.addCriteria(Criteria.where("title").is(title));
            PaperTimes paperTimes = mongoTemplate.findOne(query1, PaperTimes.class);
            if(paperTimes != null) {
                for(String month : paperTimes.getDownloadTimes().keySet()) {
                    downloadTimes.put(month, downloadTimes.getOrDefault(month, 0) + paperTimes.getDownloadTimes().getOrDefault(month, 0));
                }
                for(String month : paperTimes.getCiteTimes().keySet()) {
                    citeTimes.put(month, citeTimes.getOrDefault(month, 0) + paperTimes.getCiteTimes().get(month));
                }
                for(String month : paperTimes.getSaveTimes().keySet()) {
                    saveTimes.put(month, saveTimes.getOrDefault(month, 0) + paperTimes.getSaveTimes().get(month));
                }
            }
        }
        return new ChartData(downloadTimes, citeTimes, saveTimes);
    }



    @Override
    public ResBody addSaveTimes(String title, String month) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(title));
            Update update = new Update();
            update.inc("saveTimes." + month, 1);
            mongoTemplate.upsert(query, update, PaperTimes.class);
            return new ResBody(true, successMessage);
        } catch (Exception e) {
            return new ResBody(false, e.getMessage());
        }
    }

    @Override
    public ResBody addDownloadTimes(String title, String month) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(title));
            Update update = new Update();
            update.inc("downloadTimes." + month, 1);
            mongoTemplate.upsert(query, update, PaperTimes.class);
            return new ResBody(true, successMessage);
        } catch (Exception e) {
            return new ResBody(false, e.getMessage());
        }
    }

    @Override
    public ResBody addCiteTimes(String title, String month) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(title));
            Update update = new Update();
            update.inc("citeTimes." + month, 1);
            mongoTemplate.upsert(query, update, PaperTimes.class);
            return new ResBody(true, successMessage);
        } catch (Exception e) {
            return new ResBody(false, e.getMessage());
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 1 1 *", zone = "UTC")
    public void resetTimes() {
        Query query = new Query();
        Update update = new Update();
        PaperTimes paperTimes = new PaperTimes();
        update.set("saveTimes", paperTimes.getSaveTimes());
        update.set("downloadTimes", paperTimes.getDownloadTimes());
        update.set("citeTimes", paperTimes.getCiteTimes());
        mongoTemplate.updateMulti(query, update, PaperTimes.class);
    }
}
