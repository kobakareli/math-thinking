package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tmand13.math_thinking.LocaleHelper;

/**
 * Created by tmand on 4/19/2018.
 */

@Entity(tableName = "test")
public class Test {
    @PrimaryKey
    @ColumnInfo(name = "test_id")
    private int testId;

    @NonNull
    @ColumnInfo(name = "title_en")
    private String titleEn;

    @NonNull
    @ColumnInfo(name = "title_ge")
    private String titleGe;

    @ColumnInfo(name = "solved")
    private boolean solved;

    @ColumnInfo(name = "creation_time")
    @TypeConverters({DateTypeConverter.class})
    private Date creationTime;

    @ColumnInfo(name = "update_time")
    @TypeConverters({DateTypeConverter.class})
    private Date updateTime;

    public Test(int testId, @NonNull String titleEn, @NonNull String titleGe, boolean solved) {
        this.testId = testId;
        this.titleEn = titleEn;
        this.titleGe = titleGe;
        this.solved = solved;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    @NonNull
    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(@NonNull String titleEn) {
        this.titleEn = titleEn;
    }

    @NonNull
    public String getTitleGe() {
        return titleGe;
    }

    public void setTitleGe(@NonNull String titleGe) {
        this.titleGe = titleGe;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getTitle(Context context) {
        String language = LocaleHelper.getLanguage(context);
        if (language.equals(Locale.ENGLISH.getLanguage())) {
            return titleEn;
        } else {
            return titleGe;
        }
    }

    public static String getTitleColumnName(Context context) {
        String language = LocaleHelper.getLanguage(context);
        if (language.equals(Locale.ENGLISH.getLanguage())) {
            return "title_en";
        } else {
            return "title_ge";
        }
    }
}
