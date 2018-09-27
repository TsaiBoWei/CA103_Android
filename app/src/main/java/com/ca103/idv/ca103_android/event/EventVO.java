package com.ca103.idv.ca103_android.event;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class EventVO implements Serializable{

    private String eve_id;
    private String mem_id;
    private byte[] eve_photo;
    private byte[] eve_logo;
    private String eve_ptype;
    private String eve_title;
    private String eve_content;
    private Timestamp eve_startdate;
    private Timestamp eve_enddate;
    private Date ereg_startdate;
    private Date ereg_enddate;
    private Integer estart_limit;
    private String eve_status;
    private String eve_location;
    private Double eve_long;
    private Double eve_lat;
    private String city_id;
    private String sptype_id;
    private Integer eve_view;
    private Integer eve_charge;
    private String econtact_info;
    private Timestamp eestablish_date;


    public EventVO() {
        super();
        // TODO Auto-generated constructor stub
    }



    public EventVO(String eve_id, String mem_id, byte[] eve_photo, byte[] eve_logo, String eve_ptype, String eve_title,
                   String eve_content, Timestamp eve_startdate, Timestamp eve_enddate, Date ereg_startdate, Date ereg_enddate,
                   Integer estart_limit, String eve_status, String eve_location, Double eve_long, Double eve_lat,
                   String city_id, String sptype_id, Integer eve_view, Integer eve_charge, String econtact_info,
                   Timestamp eestablish_date) {
        super();
        this.eve_id = eve_id;
        this.mem_id = mem_id;
        this.eve_photo = eve_photo;
        this.eve_logo = eve_logo;
        this.eve_ptype = eve_ptype;
        this.eve_title = eve_title;
        this.eve_content = eve_content;
        this.eve_startdate = eve_startdate;
        this.eve_enddate = eve_enddate;
        this.ereg_startdate = ereg_startdate;
        this.ereg_enddate = ereg_enddate;
        this.estart_limit = estart_limit;
        this.eve_status = eve_status;
        this.eve_location = eve_location;
        this.eve_long = eve_long;
        this.eve_lat = eve_lat;
        this.city_id = city_id;
        this.sptype_id = sptype_id;
        this.eve_view = eve_view;
        this.eve_charge = eve_charge;
        this.econtact_info = econtact_info;
        this.eestablish_date = eestablish_date;
    }



    public String getEve_id() {
        return eve_id;
    }


    public void setEve_id(String eve_id) {
        this.eve_id = eve_id;
    }


    public String getMem_id() {
        return mem_id;
    }


    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }


    public byte[] getEve_photo() {
        return eve_photo;
    }


    public void setEve_photo(byte[] eve_photo) {
        this.eve_photo = eve_photo;
    }


    public byte[] getEve_logo() {
        return eve_logo;
    }


    public void setEve_logo(byte[] eve_logo) {
        this.eve_logo = eve_logo;
    }


    public String getEve_ptype() {
        return eve_ptype;
    }



    public void setEve_ptype(String eve_ptype) {
        this.eve_ptype = eve_ptype;
    }



    public String getEve_title() {
        return eve_title;
    }



    public void setEve_title(String eve_title) {
        this.eve_title = eve_title;
    }



    public String getEve_content() {
        return eve_content;
    }


    public void setEve_content(String eve_content) {
        this.eve_content = eve_content;
    }


    public Timestamp getEve_startdate() {
        return eve_startdate;
    }


    public void setEve_startdate( Timestamp eve_startdate) {
        this.eve_startdate = eve_startdate;
    }


    public  Timestamp getEve_enddate() {
        return eve_enddate;
    }


    public void setEve_enddate( Timestamp eve_enddate) {
        this.eve_enddate = eve_enddate;
    }


    public Date getEreg_startdate() {
        return ereg_startdate;
    }


    public void setEreg_startdate(Date ereg_startdate) {
        this.ereg_startdate = ereg_startdate;
    }


    public Date getEreg_enddate() {
        return ereg_enddate;
    }


    public void setEreg_enddate(Date ereg_enddate) {
        this.ereg_enddate = ereg_enddate;
    }


    public Integer getEstart_limit() {
        return estart_limit;
    }


    public void setEstart_limit(Integer estart_limit) {
        this.estart_limit = estart_limit;
    }


    public String getEve_status() {
        return eve_status;
    }


    public void setEve_status(String eve_status) {
        this.eve_status = eve_status;
    }


    public String getEve_location() {
        return eve_location;
    }


    public void setEve_location(String eve_location) {
        this.eve_location = eve_location;
    }


    public Double getEve_long() {
        return eve_long;
    }


    public void setEve_long(Double eve_long) {
        this.eve_long = eve_long;
    }


    public Double getEve_lat() {
        return eve_lat;
    }


    public void setEve_lat(Double eve_lat) {
        this.eve_lat = eve_lat;
    }


    public String getCity_id() {
        return city_id;
    }


    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }


    public String getSptype_id() {
        return sptype_id;
    }


    public void setSptype_id(String sptype_id) {
        this.sptype_id = sptype_id;
    }


    public Integer getEve_view() {
        return eve_view;
    }


    public void setEve_view(Integer eve_view) {
        this.eve_view = eve_view;
    }


    public Integer getEve_charge() {
        return eve_charge;
    }


    public void setEve_charge(Integer eve_charge) {
        this.eve_charge = eve_charge;
    }


    public String getEcontact_info() {
        return econtact_info;
    }


    public void setEcontact_info(String econtact_info) {
        this.econtact_info = econtact_info;
    }


    public  Timestamp getEestablish_date() {
        return eestablish_date;
    }


    public void setEestablish_date( Timestamp eestablish_date) {
        this.eestablish_date = eestablish_date;
    }






}