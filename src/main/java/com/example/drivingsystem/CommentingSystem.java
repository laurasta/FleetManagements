package com.example.drivingsystem;

import java.util.ArrayList;

public class CommentingSystem {
    private ArrayList<Comment> commentList;
    public CommentingSystem(){
        commentList = new ArrayList<>();
    }
    public void addComment(Comment comment){
        if(comment.getParentCommentId() == -1){
            commentList.add(comment);
        } else {
            for(Comment c : commentList){
                if(c.getCommentId() == comment.getParentCommentId()){
                    c.addSubComment(comment);
                    break;
                }
            }
        }
    }
    public ArrayList<Comment> getCommentList(){
        return commentList;
    }
}