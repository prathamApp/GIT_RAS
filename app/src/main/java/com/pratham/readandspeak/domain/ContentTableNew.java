package com.pratham.readandspeak.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentTableNew {

    public String nodeId;
    public String level;
    public String resourceId;
    public String parentId;
    public String nodeDesc;
    public String nodeType;

    public String nodeTitle;
    public String resourcePath;
    public String resourceType;
    public String nodeServerImage;
    public String nodeImage;
    public String nodeAge;
    public String isDownloaded;
    public String contentType;
    public String contentLanguage;
    public String nodeKeywords;
    private boolean onSDCard = false;
    private List<ContentTableNew> nodelist = null;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getNodeServerImage() {
        return nodeServerImage;
    }

    public void setNodeServerImage(String nodeServerImage) {
        this.nodeServerImage = nodeServerImage;
    }

    public String getNodeImage() {
        return nodeImage;
    }

    public void setNodeImage(String nodeImage) {
        this.nodeImage = nodeImage;
    }

    public String getNodeAge() {
        return nodeAge;
    }

    public void setNodeAge(String nodeAge) {
        this.nodeAge = nodeAge;
    }

    public String getIsDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(String isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public String getNodeKeywords() {
        return nodeKeywords;
    }

    public void setNodeKeywords(String nodeKeywords) {
        this.nodeKeywords = nodeKeywords;
    }

    public boolean isOnSDCard() {
        return onSDCard;
    }

    public void setOnSDCard(boolean onSDCard) {
        this.onSDCard = onSDCard;
    }

    public List<ContentTableNew> getNodelist() {
        return nodelist;
    }

    public void setNodelist(List<ContentTableNew> nodelist) {
        this.nodelist = nodelist;
    }
}