package io.harkema.businesspoints.model;

import android.content.ContentValues;

/**
 * Created by tomas on 16-1-16.
 */
//@DatabaseTable(tableName = "businesspoint")
public class BusinessPoint {

    public static final String TABLE_NAME = "businesspoint";
    public static final String _ID = "_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_TEACHER = "teacher";
    public static final String COLUMN_NAME_ECTS = "ects";
    public static final String COLUMN_NAME_FINISHED = "finished";
    public static final String COLUMN_NAME_GRADE = "grade";

//    @DatabaseField(generatedId = true, columnName = _ID)
    public int id;

//    @DatabaseField(columnName = COLUMN_NAME_TITLE)
    public String title;

//    @DatabaseField(columnName = COLUMN_NAME_TEACHER)
    public String teacher;

//    @DatabaseField(columnName = COLUMN_NAME_ECTS)
    public int ects;

//    @DatabaseField(columnName = COLUMN_NAME_FINISHED)
    public boolean finished;

//    @DatabaseField(columnName = COLUMN_NAME_GRADE)
    public int grade;

    public BusinessPoint() {
    }

    public BusinessPoint(String title, String teacher, int ects, boolean finished, int grade) {
        this.title = title;
        this.teacher = teacher;
        this.ects = ects;
        this.finished = finished;
        this.grade = grade;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(_ID, id);
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_TEACHER, teacher);
        values.put(COLUMN_NAME_ECTS, ects);
        values.put(COLUMN_NAME_FINISHED, finished);
        values.put(COLUMN_NAME_GRADE, grade);

        return values;
    }
}
