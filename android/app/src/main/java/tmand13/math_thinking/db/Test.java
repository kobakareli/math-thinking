package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Test(int testId, @NonNull String titleEn, @NonNull String titleGe) {
        this.testId = testId;
        this.titleEn = titleEn;
        this.titleGe = titleGe;
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
