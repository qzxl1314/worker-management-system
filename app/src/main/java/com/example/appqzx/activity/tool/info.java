package com.example.appqzx.activity.tool;

import java.util.Date;

public class info {
    private String office;
    private String person;
    private String work;
    private String content;
    private String what;
    private String data;
    public void setOffice(String a){
        this.office=a;
    }
    public void setPerson(String a){
        this.person=a;
    }
    public void setWhat(String a){
        this.what=a;
    }
    public void setWork(String a){
        this.work=a;
    }
    public void setContent(String a){
        this.content=a;
    }
    public void setData(String a){
        this.data=a;
    }
    public String getContent(){return  this.content;}
    public String getOffice(){
        return this.office;
    }
    public String getPerson(){
        return this.person;
    }
    public String getWork(){
        return this.work;
    }
    public String getWhat(){return this.what;}
    public String getData() {
        return data;
    }
}
