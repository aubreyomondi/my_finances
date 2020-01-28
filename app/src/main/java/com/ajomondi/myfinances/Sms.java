package com.ajomondi.myfinances;

import java.io.Serializable;
import java.util.Date;

public class Sms implements Serializable {

    private String smsDate;
    private String number;
    private String body;
    private Date dateFormat;
    private String type;

    public Sms(String smsDate, String number, String body, Date dateFormat, String type) {
        this.smsDate = smsDate;
        this.number = number;
        this.body = body;
        this.dateFormat = dateFormat;
        this.type = type;
    }

    public String getSmsDate() {
        return smsDate;
    }

    public String getNumber() {
        return number;
    }

    public String getBody() {
        return body;
    }

    public Date getDateFormat() {
        return dateFormat;
    }

    public String getType() {
        return type;
    }

    public void setSmsDate(String smsDate) {
        this.smsDate = smsDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDateFormat(Date dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setType(String type) {
        this.type = type;
    }
}
