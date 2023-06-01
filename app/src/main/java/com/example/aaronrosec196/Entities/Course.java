package com.example.aaronrosec196.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Aaron Rose
 * C196 Mobile application
 * Course Class
 */
@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String status;
    private String instructor;
    private String instructorPhone;
    private String instructorEmail;
    private String note;
    private int termId;

    public Course(int courseId, String courseName, String  courseStart, String courseEnd, String status, String instructor, String instructorPhone, String instructorEmail, String note, int termId){

        this.courseId= courseId;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.instructor = instructor;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName(){
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String  getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    public String toString(){ return (courseName);}
}
