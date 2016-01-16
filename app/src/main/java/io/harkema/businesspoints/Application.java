package io.harkema.businesspoints;

/**
 * Created by tomas on 16-1-16.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        App.injectContext(getApplicationContext());
    }
}
