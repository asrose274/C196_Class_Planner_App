package com.example.aaronrosec196.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Aaron Rose
 * C196 Mobile application
 * Term Class
 */
@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;

    private String name;
    private String termStart;
    private String termEnd;

    public Term(int termId, String name, String termStart, String termEnd){

        this.termId = termId;
        this.name = name;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermId() {return termId;}

    public void setTermId(int termId) { this.termId = termId;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    @Override
    public String toString() {return (name);}

}
