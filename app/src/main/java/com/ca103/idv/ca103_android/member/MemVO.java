package com.ca103.idv.ca103_android.member;

import java.util.Date;

public class MemVO implements java.io.Serializable{
    private String mem_id;
    private String mem_name;
    private String mem_account;
    private String mem_password;
    private Date mem_birth;
    private byte[] mem_photo;
    private String mem_email;
    private String mem_status;
    private String mem_intro;

    public MemVO() {
        super();
    }

    public MemVO(String mem_id, String mem_name, String mem_account, String mem_password, Date mem_birth,
                 byte[] mem_photo, String mem_email, String mem_status, String mem_intro) {
        super();
        this.mem_id = mem_id;
        this.mem_name = mem_name;
        this.mem_account = mem_account;
        this.mem_password = mem_password;
        this.mem_birth = mem_birth;
        this.mem_photo = mem_photo;
        this.mem_email = mem_email;
        this.mem_status = mem_status;
        this.mem_intro = mem_intro;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_account() {
        return mem_account;
    }

    public void setMem_account(String mem_account) {
        this.mem_account = mem_account;
    }

    public String getMem_password() {
        return mem_password;
    }

    public void setMem_password(String mem_password) {
        this.mem_password = mem_password;
    }

    public Date getMem_birth() {
        return mem_birth;
    }

    public void setMem_birth(Date mem_birth) {
        this.mem_birth = mem_birth;
    }

    public byte[] getMem_photo() {
        return mem_photo;
    }

    public void setMem_photo(byte[] mem_photo) {
        this.mem_photo = mem_photo;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }

    public String getMem_status() {
        return mem_status;
    }

    public void setMem_status(String mem_status) {
        this.mem_status = mem_status;
    }

    public String getMem_intro() {
        return mem_intro;
    }

    public void setMem_intro(String mem_intro) {
        this.mem_intro = mem_intro;
    }



}
