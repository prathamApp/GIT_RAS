package com.pratham.readandspeak.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pratham.readandspeak.domain.Score;

import java.util.List;


@Dao
public interface ScoreDao {

    @Insert
    long insert(Score score);

    @Insert
    long[] insertAll(Score... scores);

    @Update
    int update(Score score);

    @Delete
    void delete(Score score);

    @Delete
    void deleteAll(Score... scores);

    @Query("select * from Score")
    List<Score> getAllScores();

    @Query("select * from Score where sentFlag=0")
    List<Score> getAllPushScores();


    @Query("DELETE FROM Score")
    void deleteAllScores();

    @Query("select * from Score where sentFlag = 0 AND SessionID=:s_id")
    List<Score> getAllNewScores(String s_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addScoreList(List<Score> contentList);

    @Query("update Score set sentFlag=1 where sentFlag=0")
    public void setSentFlag();

    @Query("Select MAX(ScoredMarks) from Score where StudentID=:stdID AND Label='RC-sessionTotalScore '")
    public int getRCHighScore(String stdID);

    @Query("select * from Score where StudentID=:stdID AND Label='RC-sessionTotalScore '")
    List<Score> getScoreByStdID(String stdID);

    @Query("select * from Score where Label='RC-sessionTotalScore '")
    List<Score> getScoreOfRCsessionTotalScore();

}
