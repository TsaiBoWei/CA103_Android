package com.ca103.idv.ca103_android.plan;


import java.sql.Date;
import java.sql.Timestamp;

public class PlanVO implements java.io.Serializable {

    private String plan_id;
    private String mem_id;
    private String plan_name;
    private String plan_vo;
    private byte[] plan_cover;
    private Timestamp plan_start_date;
    private Timestamp plan_end_date;
    private String sptype_id;
    private Integer plan_view;
    private String plan_privacy;
    private Date plan_create_time;
    private String plan_status;

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_vo() {
        return plan_vo;
    }

    public void setPlan_vo(String plan_vo) {
        this.plan_vo = plan_vo;
    }

    public byte[] getPlan_cover() {
        return plan_cover;
    }

    public void setPlan_cover(byte[] plan_cover) {
        this.plan_cover = plan_cover;
    }

    public Timestamp getPlan_start_date() {
        return plan_start_date;
    }

    public void setPlan_start_date(Timestamp plan_start_date) {
        this.plan_start_date = plan_start_date;
    }

    public Timestamp getPlan_end_date() {
        return plan_end_date;
    }

    public void setPlan_end_date(Timestamp plan_end_date) {
        this.plan_end_date = plan_end_date;
    }

    public String getSptype_id() {
        return sptype_id;
    }

    public void setSptype_id(String sptype_id) {
        this.sptype_id = sptype_id;
    }

    public Integer getPlan_view() {
        return plan_view;
    }

    public void setPlan_view(Integer plan_view) {
        this.plan_view = plan_view;
    }

    public String getPlan_privacy() {
        return plan_privacy;
    }

    public void setPlan_privacy(String plan_privacy) {
        this.plan_privacy = plan_privacy;
    }

    public Date getPlan_create_time() {
        return plan_create_time;
    }

    public void setPlan_create_time(Date plan_create_time) {
        this.plan_create_time = plan_create_time;
    }

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

}
