package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by tmand on 4/19/2018.
 */
@Entity(tableName = "task")
public class Task {
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    private int taskId;

    @NonNull
    @ColumnInfo(name = "title_en")
    private String titleEn;

    @NonNull
    @ColumnInfo(name = "title_ge")
    private String titleGe;

    @NonNull
    @ColumnInfo(name = "description_en")
    private String descriptionEn;

    @NonNull
    @ColumnInfo(name = "description_ge")
    private String descriptionGe;

    @NonNull
    @ColumnInfo(name = "hint_en")
    private String hintEn;

    @NonNull
    @ColumnInfo(name = "hint_ge")
    private String hintGe;

    @NonNull
    @ColumnInfo(name = "answer_en")
    private String answerEn;

    @NonNull
    @ColumnInfo(name = "answer_ge")
    private String answerGe;

    @ColumnInfo(name = "numeric_answer")
    private int numericAnswer;

    @ColumnInfo(name = "total_answers")
    private int totalAnswers;

    @ColumnInfo(name = "correct_answers")
    private int correctAnswers;

    @ColumnInfo(name = "has_options")
    private boolean hasOptions;

    @NonNull
    @ColumnInfo(name = "option_1_en")
    private String option1En;

    @NonNull
    @ColumnInfo(name = "option_1_ge")
    private String option1Ge;

    @NonNull
    @ColumnInfo(name = "option_2_en")
    private String option2En;

    @NonNull
    @ColumnInfo(name = "option_2_ge")
    private String option2Ge;

    @NonNull
    @ColumnInfo(name = "option_3_en")
    private String option3En;

    @NonNull
    @ColumnInfo(name = "option_3_ge")
    private String option3Ge;

    @NonNull
    @ColumnInfo(name = "option_4_en")
    private String option4En;

    @NonNull
    @ColumnInfo(name = "option_4_ge")
    private String option4Ge;

    public Task(int taskId, @NonNull String titleEn, @NonNull String titleGe,
                @NonNull String descriptionEn, @NonNull String descriptionGe,
                @NonNull String hintEn, @NonNull String hintGe, @NonNull String answerEn,
                @NonNull String answerGe, int numericAnswer, int totalAnswers, int correctAnswers,
                boolean hasOptions, @NonNull String option1En, @NonNull String option1Ge,
                @NonNull String option2En, @NonNull String option2Ge, @NonNull String option3En,
                @NonNull String option3Ge, @NonNull String option4En, @NonNull String option4Ge) {
        this.taskId = taskId;
        this.titleEn = titleEn;
        this.titleGe = titleGe;
        this.descriptionEn = descriptionEn;
        this.descriptionGe = descriptionGe;
        this.hintEn = hintEn;
        this.hintGe = hintGe;
        this.answerEn = answerEn;
        this.answerGe = answerGe;
        this.numericAnswer = numericAnswer;
        this.totalAnswers = totalAnswers;
        this.correctAnswers = correctAnswers;
        this.hasOptions = hasOptions;
        this.option1En = option1En;
        this.option1Ge = option1Ge;
        this.option2En = option2En;
        this.option2Ge = option2Ge;
        this.option3En = option3En;
        this.option3Ge = option3Ge;
        this.option4En = option4En;
        this.option4Ge = option4Ge;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    @NonNull
    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(@NonNull String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    @NonNull
    public String getDescriptionGe() {
        return descriptionGe;
    }

    public void setDescriptionGe(@NonNull String descriptionGe) {
        this.descriptionGe = descriptionGe;
    }

    @NonNull
    public String getHintEn() {
        return hintEn;
    }

    public void setHintEn(@NonNull String hintEn) {
        this.hintEn = hintEn;
    }

    @NonNull
    public String getHintGe() {
        return hintGe;
    }

    public void setHintGe(@NonNull String hintGe) {
        this.hintGe = hintGe;
    }

    @NonNull
    public String getAnswerEn() {
        return answerEn;
    }

    public void setAnswerEn(@NonNull String answerEn) {
        this.answerEn = answerEn;
    }

    @NonNull
    public String getAnswerGe() {
        return answerGe;
    }

    public void setAnswerGe(@NonNull String answerGe) {
        this.answerGe = answerGe;
    }

    public int getNumericAnswer() {
        return numericAnswer;
    }

    public void setNumericAnswer(int numericAnswer) {
        this.numericAnswer = numericAnswer;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public boolean isHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(boolean hasOptions) {
        this.hasOptions = hasOptions;
    }

    @NonNull
    public String getOption1En() {
        return option1En;
    }

    public void setOption1En(@NonNull String option1En) {
        this.option1En = option1En;
    }

    @NonNull
    public String getOption1Ge() {
        return option1Ge;
    }

    public void setOption1Ge(@NonNull String option1Ge) {
        this.option1Ge = option1Ge;
    }

    @NonNull
    public String getOption2En() {
        return option2En;
    }

    public void setOption2En(@NonNull String option2En) {
        this.option2En = option2En;
    }

    @NonNull
    public String getOption2Ge() {
        return option2Ge;
    }

    public void setOption2Ge(@NonNull String option2Ge) {
        this.option2Ge = option2Ge;
    }

    @NonNull
    public String getOption3En() {
        return option3En;
    }

    public void setOption3En(@NonNull String option3En) {
        this.option3En = option3En;
    }

    @NonNull
    public String getOption3Ge() {
        return option3Ge;
    }

    public void setOption3Ge(@NonNull String option3Ge) {
        this.option3Ge = option3Ge;
    }

    @NonNull
    public String getOption4En() {
        return option4En;
    }

    public void setOption4En(@NonNull String option4En) {
        this.option4En = option4En;
    }

    @NonNull
    public String getOption4Ge() {
        return option4Ge;
    }

    public void setOption4Ge(@NonNull String option4Ge) {
        this.option4Ge = option4Ge;
    }
}
