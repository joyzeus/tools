package com.igeek.tools.db.entity;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ShadowSkyCheckInLogEntity {
    @Id
    private long id;
    private String account;
    private Date checkInTime;
    private String checkInResult;

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

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckInResult() {
        return checkInResult;
    }

    public void setCheckInResult(String checkInResult) {
        this.checkInResult = checkInResult;
    }

    public ShadowSkyCheckInLogEntity(String account, Date checkInTime, String checkInResult) {
        this.account = account;
        this.checkInTime = checkInTime;
        this.checkInResult = checkInResult;
    }

    public ShadowSkyCheckInLogEntity(long id, String account, Date checkInTime, String checkInResult) {
        this.id = id;
        this.account = account;
        this.checkInTime = checkInTime;
        this.checkInResult = checkInResult;
    }

    @Override
    public String toString() {
        return "ShadowCheckInLogEntity{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", checkInTime=" + checkInTime +
                ", checkInResult='" + checkInResult + '\'' +
                '}';
    }
}
