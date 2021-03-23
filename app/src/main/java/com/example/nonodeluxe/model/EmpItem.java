package com.example.nonodeluxe.model;

public class EmpItem extends MyItem{
    private String name;
    private String id;
    private String pw;
    private int tel_num;
    private int emp_code;
    private int unit_code;
    private String emp_grade;
    private String unit_name;

    public EmpItem(){

    }

    public EmpItem(String name, String id, String pw, int tel_num, int emp_code, int unit_code, String emp_grade, String unit_name) {
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.tel_num = tel_num;
        this.emp_code = emp_code;
        this.unit_code = unit_code;
        this.emp_grade = emp_grade;
        this.unit_name = unit_name;
    }

    public EmpItem(String name, String pw, int tel_num, int emp_code, int unit_code){
        this.name = name;
        this.pw = pw;
        this.tel_num = tel_num;
        this.emp_code = emp_code;
        this.unit_code = unit_code;
    }

    public String getId() {
        return id;
    }

    public String getEmp_grade() {
        return emp_grade;
    }

    public String getUnit_name() {
        return unit_name;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setEmp_grade(String emp_grade) {
        this.emp_grade = emp_grade;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
