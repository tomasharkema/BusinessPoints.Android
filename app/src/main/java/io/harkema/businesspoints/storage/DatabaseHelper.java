//package io.harkema.businesspoints.storage;
//
///**
// * Created by tomas on 16-1-16.
// */
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//import java.sql.SQLException;
//
//import io.harkema.businesspoints.App;
//import io.harkema.businesspoints.model.BusinessPoint;
//
///**
// * Source: https://github.com/j256/ormlite-examples/blob/master/android/HelloAndroidNoBase/src/com/example/hellonobase/SimpleData.java
// *
// * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
// * the DAOs used by the other classes.
// */
//public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
//
//    private static final String DATABASE_NAME = "database.db";
//    // any time you make changes to your database objects, you may have to increase the database version
//    private static final int DATABASE_VERSION = 1;
//
//    private static DatabaseHelper instance;
//
//    public static synchronized DatabaseHelper getInstance(Context context) {
//        if (null == DatabaseHelper.instance && null != context) {
//            DatabaseHelper.instance = new DatabaseHelper(context);
//        }
//        return DatabaseHelper.instance;
//    }
//
//    private DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    /**
//     * This is called when the database is first created. Usually you should call createTable statements here to create
//     * the tables that will store your data.
//     */
//    @Override
//    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
//        try {
//            Log.i(DatabaseHelper.class.getName(), "onCreate");
//            TableUtils.createTableIfNotExists(connectionSource, BusinessPoint.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
//     * the various data to match the new version number.
//     */
//    @Override
//    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
//            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
//            TableUtils.dropTable(connectionSource, BusinessPoint.class, true);
//            onCreate(db, connectionSource);
//        } catch (SQLException e) {
//            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
//            throw new RuntimeException(e);
//        }
//    }
//}
