package com.igeek.tools.db.entity;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ShadowSkyAccount implements Serializable {
    @Id
    long id;
    String account;
    String password;
    Boolean autoCheckIn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAutoCheckIn() {
        return autoCheckIn;
    }

    public void setAutoCheckIn(Boolean autoCheckIn) {
        this.autoCheckIn = autoCheckIn;
    }

    public ShadowSkyAccount(String account, String password, Boolean autoCheckIn) {
        this.account = account;
        this.password = password;
        this.autoCheckIn = autoCheckIn;
    }

    public ShadowSkyAccount(long id, String account, boolean autoCheckIn, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.autoCheckIn = autoCheckIn;
    }
}
