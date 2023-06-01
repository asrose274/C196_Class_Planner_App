package com.example.aaronrosec196.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.aaronrosec196.DAO.AssessmentDAO;
import com.example.aaronrosec196.DAO.CourseDAO;
import com.example.aaronrosec196.DAO.TermDAO;
import com.example.aaronrosec196.Entities.Assessment;
import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.Entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 3, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "termSchedulerDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
