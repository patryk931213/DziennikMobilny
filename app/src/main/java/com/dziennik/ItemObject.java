package com.dziennik;

public class ItemObject {

    private String teacherName;
    private String teacherSubject;
    private String teacherEmail;

    public ItemObject(String teacherName, String teacherSubject, String teacherEmail) {
        this.teacherName = teacherName;
        this.teacherSubject = teacherSubject;
        this.teacherEmail = teacherEmail;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherSubject() {
        return teacherSubject;
    }
    public void setTeacherSubject(String teacherSubject) {
        this.teacherSubject = teacherSubject;
    }
    public String getTeacherEmail() {
        return teacherEmail;
    }
    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }
}
