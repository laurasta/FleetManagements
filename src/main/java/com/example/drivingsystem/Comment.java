package com.example.drivingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Comment {
    private int commentId;
    private String commentText;
    private ArrayList<Comment> subComments;
    private int parentCommentId;
    private String username;
    private String timestamp;

    public Comment(int commentId, String commentText, int parentCommentId, String username){
        this.commentId = commentId;
        this.commentText = commentText;
        subComments = new ArrayList<>();
        this.parentCommentId = parentCommentId;
        this.username = username;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.timestamp = dtf.format(now);
    }
    public int getCommentId(){
        return commentId;
    }
    public String getCommentText(){
        return commentText;
    }
    public int getParentCommentId(){
        return parentCommentId;
    }
    public String getUsername(){
        return username;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public void addSubComment(Comment comment){
        subComments.add(comment);
    }
    public ArrayList<Comment> getSubComments(){
        return subComments;
    }
}