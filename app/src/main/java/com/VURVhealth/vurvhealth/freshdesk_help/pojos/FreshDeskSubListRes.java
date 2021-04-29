package com.VURVhealth.vurvhealth.freshdesk_help.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FreshDeskSubListRes {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("agent_id")
    @Expose
    private long agent_id;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("category_id")
    @Expose
    private long category_id;
    @SerializedName("folder_id")
    @Expose
    private long folder_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("description_text")
    @Expose
    private String description_text;
    @SerializedName("seo_data")
    @Expose
    private Seo_data seo_data;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;
    @SerializedName("cloud_files")
    @Expose
    private List<Object> cloud_files = null;
    @SerializedName("thumbs_up")
    @Expose
    private Integer thumbs_up;
    @SerializedName("thumbs_down")
    @Expose
    private Integer thumbs_down;
    @SerializedName("hits")
    @Expose
    private Integer hits;
    @SerializedName("suggested")
    @Expose
    private Integer suggested;
    @SerializedName("feedback_count")
    @Expose
    private Integer feedback_count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(long agent_id) {
        this.agent_id = agent_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(long folder_id) {
        this.folder_id = folder_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_text() {
        return description_text;
    }

    public void setDescription_text(String description_text) {
        this.description_text = description_text;
    }

    public Seo_data getSeo_data() {
        return seo_data;
    }

    public void setSeo_data(Seo_data seo_data) {
        this.seo_data = seo_data;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public List<Object> getCloud_files() {
        return cloud_files;
    }

    public void setCloud_files(List<Object> cloud_files) {
        this.cloud_files = cloud_files;
    }

    public Integer getThumbs_up() {
        return thumbs_up;
    }

    public void setThumbs_up(Integer thumbs_up) {
        this.thumbs_up = thumbs_up;
    }

    public Integer getThumbs_down() {
        return thumbs_down;
    }

    public void setThumbs_down(Integer thumbs_down) {
        this.thumbs_down = thumbs_down;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getSuggested() {
        return suggested;
    }

    public void setSuggested(Integer suggested) {
        this.suggested = suggested;
    }

    public Integer getFeedback_count() {
        return feedback_count;
    }

    public void setFeedback_count(Integer feedback_count) {
        this.feedback_count = feedback_count;
    }



    public class Seo_data {

        @SerializedName("meta_title")
        @Expose
        private String meta_title;
        @SerializedName("meta_description")
        @Expose
        private String meta_description;
        @SerializedName("meta_keywords")
        @Expose
        private String meta_keywords;

        public String getMeta_title() {
            return meta_title;
        }

        public void setMeta_title(String meta_title) {
            this.meta_title = meta_title;
        }

        public String getMeta_description() {
            return meta_description;
        }

        public void setMeta_description(String meta_description) {
            this.meta_description = meta_description;
        }

        public String getMeta_keywords() {
            return meta_keywords;
        }

        public void setMeta_keywords(String meta_keywords) {
            this.meta_keywords = meta_keywords;
        }

    }
}
