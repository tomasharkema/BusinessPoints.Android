package io.harkema.businesspoints;

import android.content.Context;

import io.harkema.businesspoints.storage.BusinessPointsDbHelper;
//import io.harkema.businesspoints.storage.StorageService;

/**
 * Created by tomas on 16-1-16.
 */
public class App {

    public static App instance;

    public static App injectContext(Context context) {
        if (instance == null) {
            instance = new App(context);
        }

        return instance;
    }

    public Context context;
    //public StorageService storageService;
    public BusinessPointsDbHelper businessPointsDbHelper;

    public App(Context context) {
        this.context = context;

        //storageService = new StorageService(context);
        businessPointsDbHelper = new BusinessPointsDbHelper(context);
    }
}
