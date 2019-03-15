
package com.example.projetonunocosta.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Epub extends RealmObject {

    @SerializedName("isAvailable")
    @Expose
    private Boolean isAvailable;
    @SerializedName("acsTokenLink")
    @Expose
    private String acsTokenLink;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getAcsTokenLink() {
        return acsTokenLink;
    }

    public void setAcsTokenLink(String acsTokenLink) {
        this.acsTokenLink = acsTokenLink;
    }

}
