package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model;

import java.io.Serializable;

public class Post implements Serializable{
    String avt;
    String postId;
    String postedBy;
    String postDescription;//
    String postImage;//
    long postedAt;//
    int postLike;
    int commentCount;
    int share;



    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Post(String avt, String postId, String postedBy, String postDescription, String postImage, long postedAt, int postLike, int commentCount, int share) {
        this.avt = avt;
        this.postId = postId;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.postImage = postImage;
        this.postedAt = postedAt;
        this.postLike = postLike;
        this.commentCount = commentCount;
        this.share = share;
    }


    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }



    @Override
    public String toString() {
        return "Post{" +
                "avt='" + avt + '\'' +
                ", postId='" + postId + '\'' +
                ", postedBy='" + postedBy + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", postImage='" + postImage + '\'' +
                ", postedAt=" + postedAt +
                ", postLike=" + postLike +
                ", commentCount=" + commentCount +
                ", share=" + share +
                '}';
    }

    public Post(String avt, String postedBy, String postDescription, String postImage, long postedAt, int postLike, int commentCount, int share) {
        this.avt = avt;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.postImage = postImage;
        this.postedAt = postedAt;
            this.postLike = postLike;
        this.commentCount = commentCount;
        this.share = share;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }


    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }


    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }


    public Post() {
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }


    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}
