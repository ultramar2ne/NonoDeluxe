package com.example.nonodeluxe.model;

public class EmpItem {
    private String name;
    private String id;
    private String pw;
    private int tel_num;
    private int emp_code;
    private int unit_code;
    private String emp_grade;

    public EmpItem(){

    }

    public EmpItem(String name, String pw, int tel_num, int emp_code, int unit_code){
        this.name = name;
        this.pw = pw;
        this.tel_num = tel_num;
        this.emp_code = emp_code;
        this.unit_code = unit_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getTel_num() {
        return tel_num;
    }

    public void setTel_num(int tel_num) {
        this.tel_num = tel_num;
    }

    public int getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(int emp_code) {
        this.emp_code = emp_code;
    }

    public int getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(int unit_code) {
        this.unit_code = unit_code;
    }
}
