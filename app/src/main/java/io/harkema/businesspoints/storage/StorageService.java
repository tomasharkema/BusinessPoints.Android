//package io.harkema.businesspoints.storage;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.support.annotation.Nullable;
//
//import com.j256.ormlite.android.AndroidDatabaseResults;
//import com.j256.ormlite.dao.CloseableIterator;
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.stmt.PreparedQuery;
//import com.j256.ormlite.stmt.QueryBuilder;
//import com.j256.ormlite.stmt.Where;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import io.harkema.businesspoints.model.BusinessPoint;
//
///**
// * Created by tomas on 16-1-16.
// */
//public class StorageService {
//    private Context context;
//    private DatabaseHelper databaseHelper;
//
//
//    private Dao<BusinessPoint, Integer> businessPointDao;
//
//    public StorageService(Context context) {
//        this.context = context;
//
//        databaseHelper = DatabaseHelper.getInstance(context);
//        try {
//            businessPointDao = databaseHelper.getDao(BusinessPoint.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Dao<BusinessPoint, Integer> getBusinessPointDao() {
//        return businessPointDao;
//    }
//
//    public Cursor getAllBusinessPoints() {
//
//        QueryBuilder<BusinessPoint, Integer> queryBuilder = businessPointDao.queryBuilder();
//        try {
//            PreparedQuery<BusinessPoint> query = queryBuilder.where()
//                    .isNotNull("title")
//                    .prepare();
//
//            CloseableIterator<BusinessPoint> iterator = businessPointDao.iterator(query);
//            AndroidDatabaseResults results = (AndroidDatabaseResults)iterator.getRawResults();
//            return results.getRawCursor();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public void createOrUpdateBusinessPoint(BusinessPoint businessPoint) {
//        try {
//            businessPointDao.createOrUpdate(businessPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Nullable
//    public BusinessPoint getBusinessPointById(int id) {
//        try {
//            return businessPointDao.queryForId(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public void deleteBusinessPoint(BusinessPoint businessPoint) {
//        try {
//            businessPointDao.delete(businessPoint);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private int countECTS(List<BusinessPoint> businessPoints) {
//        int points = 0;
//        for (BusinessPoint bp : businessPoints) {
//            points += bp.ects;
//        }
//
//        return points;
//    }
//
//    public int getFinishedECTS() {
//        QueryBuilder<BusinessPoint, Integer> queryBuilder = businessPointDao.queryBuilder();
//        try {
//            PreparedQuery<BusinessPoint> query = queryBuilder
//                    .where()
//                    .eq("finished", true).prepare();
//            List<BusinessPoint> elements = businessPointDao.query(query);
//
//            return countECTS(elements);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    public int getAllECTS() {
//        try {
//            List<BusinessPoint> businessPoints = businessPointDao.queryForAll();
//            return countECTS(businessPoints);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//}
