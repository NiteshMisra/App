package app.care.di;

import android.content.Context;

public class ObjectManager {

    private MyDbCallManager myDbCallManager;

    public ObjectManager(Context context){
        myDbCallManager = new MyDbCallManager(context);
    }

    public MyDbCallManager getMyDbCallManager() { return myDbCallManager; }
}
