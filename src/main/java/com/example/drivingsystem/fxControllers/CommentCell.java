package com.example.drivingsystem.fxControllers;

import com.example.drivingsystem.Comment;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;

public class CommentCell extends TreeCell<Comment> {

    @Override
    protected void updateItem(Comment comment, boolean empty) {
        super.updateItem(comment, empty);
        if (!empty && comment != null) {
            HBox hBox = new HBox();
            hBox.setSpacing(5);
            Label commentTextLabel = new Label("Message: " + comment.getCommentText());
            Label usernameLabel = new Label("Username: " + comment.getUsername());
            Label commentDateLabel = new Label("Time: " + comment.getTimestamp());
            hBox.getChildren().addAll(commentDateLabel, usernameLabel, commentTextLabel);
            setGraphic(hBox);
        } else {
            setGraphic(null);
        }
    }
}