package com.VURVhealth.vurvhealth.upgrade;

/**
 * Created by yqlabs on 20/3/17.
 */

public class AddMemberDataObject {

    private String mem_type;
    private String mem_f_name;
    private String mem_l_name;
    private String mem_dob;
    private String mem_email;
    private String mem_gen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public AddMemberDataObject(int id, String mem_type, String mem_f_name, String mem_l_name, String mem_dob, String mem_email,String mem_gen) {

        this.id = id;
        this.mem_type = mem_type;
        this.mem_f_name = mem_f_name;
        this.mem_l_name = mem_l_name;
        this.mem_dob = mem_dob;
        this.mem_email = mem_email;
        this.mem_gen = mem_gen;
    }

    public String getMem_type() {
        return mem_type;
    }

    public void setMem_type(String mem_type) {
        this.mem_type = mem_type;
    }

    public String getMem_f_name() {
        return mem_f_name;
    }

    public void setMem_f_name(String mem_f_name) {
        this.mem_f_name = mem_f_name;
    }

    public String getMem_l_name() {
        return mem_l_name;
    }

    public void setMem_l_name(String mem_l_name) {
        this.mem_l_name = mem_l_name;
    }

    public String getMem_dob() {
        return mem_dob;
    }

    public void setMem_dob(String mem_dob) {
        this.mem_dob = mem_dob;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }

    public String getMem_gen() {
        return mem_gen;
    }

    public void setMem_gen(String mem_gen) {
        this.mem_gen = mem_gen;
    }
}
