package com.xkong.paper.service.impl;

import com.xkong.paper.dao.Paper;
import com.xkong.paper.service.paper.Search;
import com.xkong.paper.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class SearchImpl implements Search {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<SearchTermResBody> searchPaper(String searchTerm) {
        try{
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("title").regex(searchTerm, "i"),
                    Criteria.where("tags").in(Pattern.compile(".*" + searchTerm + ".*", Pattern.CASE_INSENSITIVE)),
                    Criteria.where("collaborators").in(Pattern.compile(".*" + searchTerm + ".*", Pattern.CASE_INSENSITIVE))
            );
            query.addCriteria(criteria);
            List<Paper> papers = mongoTemplate.find(query, Paper.class);
            return papers.stream().map(this::convertToSearchTermResBody).toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public List<SearchCommentResBody> searchComment(String title) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(title));
            List<Paper> papers = mongoTemplate.find(query, Paper.class);
            return papers.stream().map(this::convertToSearchCommentResBody).toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public SubmitCommentResBody submitComment(SubmitCommentReqBody submitCommentReqBody) {
        Comment comment = new Comment(submitCommentReqBody.getUsername(), submitCommentReqBody.getAvatar(), submitCommentReqBody.getRating(), submitCommentReqBody.getComment());
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(submitCommentReqBody.getTitle()));
            Update update = new Update();
            update.push("comments", comment);
            mongoTemplate.updateFirst(query, update, Paper.class);
            return new SubmitCommentResBody("ok", "Comment added successfully", comment);
        } catch (Exception e) {
            return new SubmitCommentResBody("error", e.getMessage(), comment);
        }
    }

    @Override
    public List<Paper> searchPaperDetail(String title, String keyword) {
        try{
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("title").is(title),
                    //我想判断keyword是否是tags数组的一个元素，但是不知道怎么写
                    Criteria.where("tags").in(keyword)
            );
            query.addCriteria(criteria);

            return mongoTemplate.find(query, Paper.class);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public List<Paper> searchPaperDetailByTag(String tag) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("tags").in(tag));
            return mongoTemplate.find(query, Paper.class);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public List<Paper> searchPaperDetailAll() {
        try{
            return mongoTemplate.findAll(Paper.class);
        } catch (Exception e) {
            return List.of();
        }
    }


    private SearchTermResBody convertToSearchTermResBody(Paper paper) {
        return new SearchTermResBody(paper.getTitle(), paper.getCollaborators(), paper.getTags(), paper.getDate());
    }

    private SearchCommentResBody convertToSearchCommentResBody(Paper paper) {
        return new SearchCommentResBody(paper.getTitle(), paper.getAbstracts(), paper.getComments());
    }


}
