package com.ca103.idv.ca103_android.course;


public class CourunitVO implements java.io.Serializable{

    private String cour_unit_id;
    private String cour_id;
    private String cu_name;
    private Byte[] cour_film_blob;
    private String cour_film_vc;
    private Double cour_length;
    private String cour_vtype;

    public String getCour_unit_id() {
        return cour_unit_id;
    }
    public void setCour_unit_id(String cour_unit_id) {
        this.cour_unit_id = cour_unit_id;
    }
    public String getCour_id() {
        return cour_id;
    }
    public void setCour_id(String cour_id) {
        this.cour_id = cour_id;
    }
    public String getCu_name() {
        return cu_name;
    }
    public void setCu_name(String cu_name) {
        this.cu_name = cu_name;
    }
    public Byte[] getCour_film_blob() {
        return cour_film_blob;
    }
    public void setCour_film_blob(Byte[] cour_film_blob) {
        this.cour_film_blob = cour_film_blob;
    }
    public String getCour_film_vc() {
        return cour_film_vc;
    }
    public void setCour_film_vc(String cour_film_vc) {
        this.cour_film_vc = cour_film_vc;
    }
    public Double getCour_length() {
        return cour_length;
    }
    public void setCour_length(Double cour_length) {
        this.cour_length = cour_length;
    }
    public String getCour_vtype() {
        return cour_vtype;
    }
    public void setCour_vtype(String cour_vtype) {
        this.cour_vtype = cour_vtype;
    }
}