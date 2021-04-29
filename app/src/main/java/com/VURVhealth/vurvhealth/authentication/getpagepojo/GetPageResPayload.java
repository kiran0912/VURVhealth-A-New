package com.VURVhealth.vurvhealth.authentication.getpagepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yqlabs on 3/5/17.
 */

public class GetPageResPayload {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("page")
    @Expose
    private Page page;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class Page {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("title_plain")
        @Expose
        private String titlePlain;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("excerpt")
        @Expose
        private String excerpt;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("modified")
        @Expose
        private String modified;
        @SerializedName("categories")
        @Expose
        private List<Object> categories = null;
        @SerializedName("tags")
        @Expose
        private List<Object> tags = null;
        @SerializedName("author")
        @Expose
        private Author author;
        @SerializedName("comments")
        @Expose
        private List<Object> comments = null;
        @SerializedName("attachments")
        @Expose
        private List<Object> attachments = null;
        @SerializedName("comment_count")
        @Expose
        private Integer commentCount;
        @SerializedName("comment_status")
        @Expose
        private String commentStatus;
        @SerializedName("custom_fields")
        @Expose
        private CustomFields customFields;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitlePlain() {
            return titlePlain;
        }

        public void setTitlePlain(String titlePlain) {
            this.titlePlain = titlePlain;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public List<Object> getCategories() {
            return categories;
        }

        public void setCategories(List<Object> categories) {
            this.categories = categories;
        }

        public List<Object> getTags() {
            return tags;
        }

        public void setTags(List<Object> tags) {
            this.tags = tags;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public List<Object> getComments() {
            return comments;
        }

        public void setComments(List<Object> comments) {
            this.comments = comments;
        }

        public List<Object> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Object> attachments) {
            this.attachments = attachments;
        }

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public String getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(String commentStatus) {
            this.commentStatus = commentStatus;
        }

        public CustomFields getCustomFields() {
            return customFields;
        }

        public void setCustomFields(CustomFields customFields) {
            this.customFields = customFields;
        }

    }

    public class Author {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("nickname")
        @Expose
        private String nickname;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("description")
        @Expose
        private String description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class CustomFields {

        @SerializedName("site_layout")
        @Expose
        private List<String> siteLayout = null;

        public List<String> getSiteLayout() {
            return siteLayout;
        }

        public void setSiteLayout(List<String> siteLayout) {
            this.siteLayout = siteLayout;
        }

    }



}
