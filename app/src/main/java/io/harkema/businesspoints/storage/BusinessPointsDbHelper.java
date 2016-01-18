package io.harkema.businesspoints.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.harkema.businesspoints.model.BusinessPoint;

/**
 * Created by tomas on 18-01-16.
 */
public class BusinessPointsDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INT";
    private static final String BOOLEAN_TYPE = " BOOL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BusinessPoint.TABLE_NAME + " (" +
                    BusinessPoint._ID + " INTEGER PRIMARY KEY," +
                    BusinessPoint.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    BusinessPoint.COLUMN_NAME_TEACHER + TEXT_TYPE + COMMA_SEP +
                    BusinessPoint.COLUMN_NAME_ECTS + INTEGER_TYPE + COMMA_SEP +
                    BusinessPoint.COLUMN_NAME_FINISHED + BOOLEAN_TYPE + COMMA_SEP +
                    BusinessPoint.COLUMN_NAME_GRADE + INTEGER_TYPE + COMMA_SEP +
            " )";

    private static final String[] BUSINESS_POINT_PROJECTION = {
            BusinessPoint._ID,
            BusinessPoint.COLUMN_NAME_TITLE,
            BusinessPoint.COLUMN_NAME_TEACHER,
            BusinessPoint.COLUMN_NAME_ECTS,
            BusinessPoint.COLUMN_NAME_FINISHED,
            BusinessPoint.COLUMN_NAME_GRADE
    };

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BusinessPoint.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";

    public BusinessPointsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(BusinessPoint businessPoint) {
        ContentValues contentValues = businessPoint.toContentValues();

        SQLiteDatabase db = getWritableDatabase();
        db.insert(BusinessPoint.TABLE_NAME, null, contentValues);
    }

    public void update(BusinessPoint businessPoint) {
        ContentValues contentValues = businessPoint.toContentValues();

        SQLiteDatabase db = getWritableDatabase();
        db.update(BusinessPoint.TABLE_NAME, contentValues, BusinessPoint._ID + "=?", new String[] {""+businessPoint.id});
    }

    public BusinessPoint getById(int id) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.query(
                BusinessPoint.TABLE_NAME,
                BUSINESS_POINT_PROJECTION,
                BusinessPoint._ID + "=?",
                new String[] {""+id},
                null,
                null,
                null
        );

        c.moveToFirst();

        int _id = c.getInt(c.getColumnIndex(BusinessPoint._ID));
        String title = c.getString(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_TITLE));
        String teacher = c.getString(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_TEACHER));
        int ects = c.getInt(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_ECTS));
        boolean finished = c.getInt(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_FINISHED)) == 1;
        int grade = c.getInt(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_GRADE));

        BusinessPoint b = new BusinessPoint(title, teacher, ects, finished, grade);
        b.id = _id;

        c.close();
        return b;
    }

    public List<BusinessPoint> getAllBusinessPoints() {

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.query(
                BusinessPoint.TABLE_NAME,
                BUSINESS_POINT_PROJECTION,
                null,
                null,
                null,
                null,
                null
        );

        List<BusinessPoint> array = new ArrayList<>();

        if (c.moveToFirst()) {
            do {

                int _id = c.getInt(c.getColumnIndex(BusinessPoint._ID));
                String title = c.getString(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_TITLE));
                String teacher = c.getString(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_TEACHER));
                int ects = c.getInt(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_ECTS));
                boolean finished = c.getInt(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_FINISHED)) == 1;
                int grade = c.getInt(c.getColumnIndexOrThrow(BusinessPoint.COLUMN_NAME_GRADE));

                BusinessPoint b = new BusinessPoint(title, teacher, ects, finished, grade);
                b.id = _id;

                array.add(b);

            } while (c.moveToNext());
        }

        return array;
    }

    public void deleteBusinessPoint(BusinessPoint businessPoint) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(BusinessPoint.TABLE_NAME, BusinessPoint._ID + "=?", new String[] { ""+businessPoint.id });
    }

    private int countECTS(List<BusinessPoint> businessPoints, boolean checkForFinished) {
        int points = 0;
        for (BusinessPoint bp : businessPoints) {
            if (checkForFinished) {
                if (bp.finished) {
                    points += bp.ects;
                }
            } else {
                points += bp.ects;
            }
        }

        return points;
    }

    public int getFinishedECTS() {
        return countECTS(getAllBusinessPoints(), true);
    }

    public int getAllECTS() {
        return countECTS(getAllBusinessPoints(), false);
    }

}
