package com.ca103.idv.ca103_android.course;

public class CourlistVO implements java.io.Serializable{

    private String cour_id;
    private String sptype_id;
    private String coa_id;
    private String cname;
    private String cour_text;
    private Integer cour_cost;
    private byte[] cour_pho;
    private String cour_lau;
    private String cour_ann;
    private Integer cour_view;

    public String getCour_id() {
        return cour_id;
    }
    public void setCour_id(String cour_id) {
        this.cour_id = cour_id;
    }
    public String getSptype_id() {
        return sptype_id;
    }
    public void setSptype_id(String sptype_id) {
        this.sptype_id = sptype_id;
    }
    public String getCoa_id() {
        return coa_id;
    }
    public void setCoa_id(String coa_id) {
        this.coa_id = coa_id;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCour_text() {
        return cour_text;
    }
    public void setCour_text(String cour_text) {
        this.cour_text = cour_text;
    }
    public Integer getCour_cost() {
        return cour_cost;
    }
    public void setCour_cost(Integer cour_cost) {
        this.cour_cost = cour_cost;
    }
    public byte[] getCour_pho() {
        return cour_pho;
    }
    public void setCour_pho(byte[] cour_pho) {
        this.cour_pho = cour_pho;
    }
    public String getCour_lau() {
        return cour_lau;
    }
    public void setCour_lau(String cour_lau) {
        this.cour_lau = cour_lau;
    }
    public String getCour_ann() {
        return cour_ann;
    }
    public void setCour_ann(String cour_ann) {
        this.cour_ann = cour_ann;
    }
    public Integer getCour_view() {
        return cour_view;
    }
    public void setCour_view(Integer cour_view) {
        this.cour_view = cour_view;
    }

}

