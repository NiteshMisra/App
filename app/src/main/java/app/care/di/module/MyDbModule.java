package app.care.di.module;

import android.content.Context;

import javax.inject.Singleton;

import app.care.di.ObjectManager;
import dagger.Module;
import dagger.Provides;

@Module
public class MyDbModule {

    private Context context;

    public MyDbModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ObjectManager getObjectManager(){
        return new ObjectManager(context);
    }
}
