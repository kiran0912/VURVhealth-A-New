package com.VURVhealth.vurvhealth.authorize.netpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 19/7/17.
 */

public class UpgradePackageResPayload {

    @SerializedName("Order Sucessfully Placed")
    @Expose
    private OrderSucessfullyPlaced orderSucessfullyPlaced;

    public OrderSucessfullyPlaced getOrderSucessfullyPlaced() {
        return orderSucessfullyPlaced;
    }

    public void setOrderSucessfullyPlaced(OrderSucessfullyPlaced orderSucessfullyPlaced) {
        this.orderSucessfullyPlaced = orderSucessfullyPlaced;
    }

    public class Order {

        @SerializedName("order_type")
        @Expose
        private String orderType;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("post")
        @Expose
        private Post post;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("customer_message")
        @Expose
        private String customerMessage;
        @SerializedName("customer_note")
        @Expose
        private String customerNote;
        @SerializedName("post_status")
        @Expose
        private String postStatus;
        @SerializedName("prices_include_tax")
        @Expose
        private Boolean pricesIncludeTax;
        @SerializedName("tax_display_cart")
        @Expose
        private String taxDisplayCart;
        @SerializedName("display_totals_ex_tax")
        @Expose
        private Boolean displayTotalsExTax;
        @SerializedName("display_cart_ex_tax")
        @Expose
        private Boolean displayCartExTax;
        @SerializedName("billing_email")
        @Expose
        private String billingEmail;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Post getPost() {
            return post;
        }

        public void setPost(Post post) {
            this.post = post;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getCustomerMessage() {
            return customerMessage;
        }

        public void setCustomerMessage(String customerMessage) {
            this.customerMessage = customerMessage;
        }

        public String getCustomerNote() {
            return customerNote;
        }

        public void setCustomerNote(String customerNote) {
            this.customerNote = customerNote;
        }

        public String getPostStatus() {
            return postStatus;
        }

        public void setPostStatus(String postStatus) {
            this.postStatus = postStatus;
        }

        public Boolean getPricesIncludeTax() {
            return pricesIncludeTax;
        }

        public void setPricesIncludeTax(Boolean pricesIncludeTax) {
            this.pricesIncludeTax = pricesIncludeTax;
        }

        public String getTaxDisplayCart() {
            return taxDisplayCart;
        }

        public void setTaxDisplayCart(String taxDisplayCart) {
            this.taxDisplayCart = taxDisplayCart;
        }

        public Boolean getDisplayTotalsExTax() {
            return displayTotalsExTax;
        }

        public void setDisplayTotalsExTax(Boolean displayTotalsExTax) {
            this.displayTotalsExTax = displayTotalsExTax;
        }

        public Boolean getDisplayCartExTax() {
            return displayCartExTax;
        }

        public void setDisplayCartExTax(Boolean displayCartExTax) {
            this.displayCartExTax = displayCartExTax;
        }

        public String getBillingEmail() {
            return billingEmail;
        }

        public void setBillingEmail(String billingEmail) {
            this.billingEmail = billingEmail;
        }

    }

    public class OrderSucessfullyPlaced {

        @SerializedName("order")
        @Expose
        private Order order;
        @SerializedName("order_type")
        @Expose
        private String orderType;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("post")
        @Expose
        private Post_ post;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("customer_message")
        @Expose
        private String customerMessage;
        @SerializedName("customer_note")
        @Expose
        private String customerNote;
        @SerializedName("post_status")
        @Expose
        private String postStatus;
        @SerializedName("prices_include_tax")
        @Expose
        private Boolean pricesIncludeTax;
        @SerializedName("tax_display_cart")
        @Expose
        private String taxDisplayCart;
        @SerializedName("display_totals_ex_tax")
        @Expose
        private Boolean displayTotalsExTax;
        @SerializedName("display_cart_ex_tax")
        @Expose
        private Boolean displayCartExTax;
        @SerializedName("billing_email")
        @Expose
        private String billingEmail;
        @SerializedName("schedule")
        @Expose
        private Schedule schedule;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Post_ getPost() {
            return post;
        }

        public void setPost(Post_ post) {
            this.post = post;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getCustomerMessage() {
            return customerMessage;
        }

        public void setCustomerMessage(String customerMessage) {
            this.customerMessage = customerMessage;
        }

        public String getCustomerNote() {
            return customerNote;
        }

        public void setCustomerNote(String customerNote) {
            this.customerNote = customerNote;
        }

        public String getPostStatus() {
            return postStatus;
        }

        public void setPostStatus(String postStatus) {
            this.postStatus = postStatus;
        }

        public Boolean getPricesIncludeTax() {
            return pricesIncludeTax;
        }

        public void setPricesIncludeTax(Boolean pricesIncludeTax) {
            this.pricesIncludeTax = pricesIncludeTax;
        }

        public String getTaxDisplayCart() {
            return taxDisplayCart;
        }

        public void setTaxDisplayCart(String taxDisplayCart) {
            this.taxDisplayCart = taxDisplayCart;
        }

        public Boolean getDisplayTotalsExTax() {
            return displayTotalsExTax;
        }

        public void setDisplayTotalsExTax(Boolean displayTotalsExTax) {
            this.displayTotalsExTax = displayTotalsExTax;
        }

        public Boolean getDisplayCartExTax() {
            return displayCartExTax;
        }

        public void setDisplayCartExTax(Boolean displayCartExTax) {
            this.displayCartExTax = displayCartExTax;
        }

        public String getBillingEmail() {
            return billingEmail;
        }

        public void setBillingEmail(String billingEmail) {
            this.billingEmail = billingEmail;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

    }

    public class Post {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("post_author")
        @Expose
        private String postAuthor;
        @SerializedName("post_date")
        @Expose
        private String postDate;
        @SerializedName("post_date_gmt")
        @Expose
        private String postDateGmt;
        @SerializedName("post_content")
        @Expose
        private String postContent;
        @SerializedName("post_title")
        @Expose
        private String postTitle;
        @SerializedName("post_excerpt")
        @Expose
        private String postExcerpt;
        @SerializedName("post_status")
        @Expose
        private String postStatus;
        @SerializedName("comment_status")
        @Expose
        private String commentStatus;
        @SerializedName("ping_status")
        @Expose
        private String pingStatus;
        @SerializedName("post_password")
        @Expose
        private String postPassword;
        @SerializedName("post_name")
        @Expose
        private String postName;
        @SerializedName("to_ping")
        @Expose
        private String toPing;
        @SerializedName("pinged")
        @Expose
        private String pinged;
        @SerializedName("post_modified")
        @Expose
        private String postModified;
        @SerializedName("post_modified_gmt")
        @Expose
        private String postModifiedGmt;
        @SerializedName("post_content_filtered")
        @Expose
        private String postContentFiltered;
        @SerializedName("post_parent")
        @Expose
        private Integer postParent;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("menu_order")
        @Expose
        private Integer menuOrder;
        @SerializedName("post_type")
        @Expose
        private String postType;
        @SerializedName("post_mime_type")
        @Expose
        private String postMimeType;
        @SerializedName("comment_count")
        @Expose
        private String commentCount;
        @SerializedName("filter")
        @Expose
        private String filter;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getPostAuthor() {
            return postAuthor;
        }

        public void setPostAuthor(String postAuthor) {
            this.postAuthor = postAuthor;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getPostDateGmt() {
            return postDateGmt;
        }

        public void setPostDateGmt(String postDateGmt) {
            this.postDateGmt = postDateGmt;
        }

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getPostExcerpt() {
            return postExcerpt;
        }

        public void setPostExcerpt(String postExcerpt) {
            this.postExcerpt = postExcerpt;
        }

        public String getPostStatus() {
            return postStatus;
        }

        public void setPostStatus(String postStatus) {
            this.postStatus = postStatus;
        }

        public String getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(String commentStatus) {
            this.commentStatus = commentStatus;
        }

        public String getPingStatus() {
            return pingStatus;
        }

        public void setPingStatus(String pingStatus) {
            this.pingStatus = pingStatus;
        }

        public String getPostPassword() {
            return postPassword;
        }

        public void setPostPassword(String postPassword) {
            this.postPassword = postPassword;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getToPing() {
            return toPing;
        }

        public void setToPing(String toPing) {
            this.toPing = toPing;
        }

        public String getPinged() {
            return pinged;
        }

        public void setPinged(String pinged) {
            this.pinged = pinged;
        }

        public String getPostModified() {
            return postModified;
        }

        public void setPostModified(String postModified) {
            this.postModified = postModified;
        }

        public String getPostModifiedGmt() {
            return postModifiedGmt;
        }

        public void setPostModifiedGmt(String postModifiedGmt) {
            this.postModifiedGmt = postModifiedGmt;
        }

        public String getPostContentFiltered() {
            return postContentFiltered;
        }

        public void setPostContentFiltered(String postContentFiltered) {
            this.postContentFiltered = postContentFiltered;
        }

        public Integer getPostParent() {
            return postParent;
        }

        public void setPostParent(Integer postParent) {
            this.postParent = postParent;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public Integer getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(Integer menuOrder) {
            this.menuOrder = menuOrder;
        }

        public String getPostType() {
            return postType;
        }

        public void setPostType(String postType) {
            this.postType = postType;
        }

        public String getPostMimeType() {
            return postMimeType;
        }

        public void setPostMimeType(String postMimeType) {
            this.postMimeType = postMimeType;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

    }

    public class Post_ {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("post_author")
        @Expose
        private String postAuthor;
        @SerializedName("post_date")
        @Expose
        private String postDate;
        @SerializedName("post_date_gmt")
        @Expose
        private String postDateGmt;
        @SerializedName("post_content")
        @Expose
        private String postContent;
        @SerializedName("post_title")
        @Expose
        private String postTitle;
        @SerializedName("post_excerpt")
        @Expose
        private String postExcerpt;
        @SerializedName("post_status")
        @Expose
        private String postStatus;
        @SerializedName("comment_status")
        @Expose
        private String commentStatus;
        @SerializedName("ping_status")
        @Expose
        private String pingStatus;
        @SerializedName("post_password")
        @Expose
        private String postPassword;
        @SerializedName("post_name")
        @Expose
        private String postName;
        @SerializedName("to_ping")
        @Expose
        private String toPing;
        @SerializedName("pinged")
        @Expose
        private String pinged;
        @SerializedName("post_modified")
        @Expose
        private String postModified;
        @SerializedName("post_modified_gmt")
        @Expose
        private String postModifiedGmt;
        @SerializedName("post_content_filtered")
        @Expose
        private String postContentFiltered;
        @SerializedName("post_parent")
        @Expose
        private Integer postParent;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("menu_order")
        @Expose
        private Integer menuOrder;
        @SerializedName("post_type")
        @Expose
        private String postType;
        @SerializedName("post_mime_type")
        @Expose
        private String postMimeType;
        @SerializedName("comment_count")
        @Expose
        private String commentCount;
        @SerializedName("filter")
        @Expose
        private String filter;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getPostAuthor() {
            return postAuthor;
        }

        public void setPostAuthor(String postAuthor) {
            this.postAuthor = postAuthor;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getPostDateGmt() {
            return postDateGmt;
        }

        public void setPostDateGmt(String postDateGmt) {
            this.postDateGmt = postDateGmt;
        }

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getPostExcerpt() {
            return postExcerpt;
        }

        public void setPostExcerpt(String postExcerpt) {
            this.postExcerpt = postExcerpt;
        }

        public String getPostStatus() {
            return postStatus;
        }

        public void setPostStatus(String postStatus) {
            this.postStatus = postStatus;
        }

        public String getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(String commentStatus) {
            this.commentStatus = commentStatus;
        }

        public String getPingStatus() {
            return pingStatus;
        }

        public void setPingStatus(String pingStatus) {
            this.pingStatus = pingStatus;
        }

        public String getPostPassword() {
            return postPassword;
        }

        public void setPostPassword(String postPassword) {
            this.postPassword = postPassword;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getToPing() {
            return toPing;
        }

        public void setToPing(String toPing) {
            this.toPing = toPing;
        }

        public String getPinged() {
            return pinged;
        }

        public void setPinged(String pinged) {
            this.pinged = pinged;
        }

        public String getPostModified() {
            return postModified;
        }

        public void setPostModified(String postModified) {
            this.postModified = postModified;
        }

        public String getPostModifiedGmt() {
            return postModifiedGmt;
        }

        public void setPostModifiedGmt(String postModifiedGmt) {
            this.postModifiedGmt = postModifiedGmt;
        }

        public String getPostContentFiltered() {
            return postContentFiltered;
        }

        public void setPostContentFiltered(String postContentFiltered) {
            this.postContentFiltered = postContentFiltered;
        }

        public Integer getPostParent() {
            return postParent;
        }

        public void setPostParent(Integer postParent) {
            this.postParent = postParent;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public Integer getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(Integer menuOrder) {
            this.menuOrder = menuOrder;
        }

        public String getPostType() {
            return postType;
        }

        public void setPostType(String postType) {
            this.postType = postType;
        }

        public String getPostMimeType() {
            return postMimeType;
        }

        public void setPostMimeType(String postMimeType) {
            this.postMimeType = postMimeType;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

    }

    public class Schedule {


    }
}
