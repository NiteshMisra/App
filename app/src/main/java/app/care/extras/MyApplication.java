package app.care.extras;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import app.care.di.component.DaggerMyComponent;
import app.care.di.component.MyComponent;
import app.care.di.module.MyDbModule;

public class MyApplication extends MultiDexApplication {

    private static MyComponent myComponent;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        init();

    }

    public static MyComponent getDaggerComponent() {
        return myComponent;
    }

    private void init() {
        myComponent = DaggerMyComponent.builder()
                .myDbModule(new MyDbModule(this.getApplicationContext()))
                .build();
        myComponent.inject(this);

    }

    public static Context getContext() {
        return context;
    }

}
