
package com.murrayde.animekingmobile.network.community.api_models;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.animekingmobile.model.community.AnimeAttributes.Attributes;

@Keep
public class AnimeData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    private long numOfQuestions;

    public long getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(long numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
