package com.pratham.readandspeak.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pratham.readandspeak.dao.AssessmentDao;
import com.pratham.readandspeak.dao.AttendanceDao;
import com.pratham.readandspeak.dao.ContentTableDao;
import com.pratham.readandspeak.dao.CrlDao;
import com.pratham.readandspeak.dao.GroupDao;
import com.pratham.readandspeak.dao.LogDao;
import com.pratham.readandspeak.dao.ScoreDao;
import com.pratham.readandspeak.dao.SessionDao;
import com.pratham.readandspeak.dao.StatusDao;
import com.pratham.readandspeak.dao.StudentDao;
import com.pratham.readandspeak.dao.VillageDao;
import com.pratham.readandspeak.domain.Assessment;
import com.pratham.readandspeak.domain.Attendance;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.Crl;
import com.pratham.readandspeak.domain.Groups;
import com.pratham.readandspeak.domain.Modal_Log;
import com.pratham.readandspeak.domain.Score;
import com.pratham.readandspeak.domain.Session;
import com.pratham.readandspeak.domain.Status;
import com.pratham.readandspeak.domain.Student;
import com.pratham.readandspeak.domain.Village;


@Database(entities = {Crl.class,  Student.class, Score.class, Session.class, Attendance.class, Status.class,  Village.class, Groups.class, Assessment.class, Modal_Log.class, ContentTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase appDatabase;

    public static final String DB_NAME = "ras_database";

    public abstract CrlDao getCrlDao();

    public abstract StudentDao getStudentDao();

    public abstract ScoreDao getScoreDao();

    public abstract AssessmentDao getAssessmentDao();

    public abstract SessionDao getSessionDao();

    public abstract AttendanceDao getAttendanceDao();

    public abstract VillageDao getVillageDao();

    public abstract GroupDao getGroupsDao();

    public abstract LogDao getLogsDao();

    public abstract ContentTableDao getContentTableDao();

    //new
    public abstract StatusDao getStatusDao();


   /* public static AppDatabase getDatabaseInstance(Context context) {
        if(appDatabase!=null) {
            appDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, AppDatabase.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }*/

    public static AppDatabase getDatabaseInstance(Context context) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ras_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return appDatabase;
    }
}
