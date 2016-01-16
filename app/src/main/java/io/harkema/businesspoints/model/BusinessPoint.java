package io.harkema.businesspoints.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tomas on 16-1-16.
 */
@DatabaseTable(tableName = "businesspoint")
public class BusinessPoint {

    @DatabaseField(generatedId = true, columnName = "_id")
    public int id;

    @DatabaseField(columnName = "title")
    public String title;

    @DatabaseField(columnName = "teacher")
    public String teacher;

    @DatabaseField(columnName = "ects")
    public int ects;

    @DatabaseField(columnName = "finished")
    public boolean finished;

    @DatabaseField(columnName = "grade")
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
}
