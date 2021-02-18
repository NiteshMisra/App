package app.care.di.component;

import javax.inject.Singleton;

import app.care.activity.BaseActivity;
import app.care.di.module.MyDbModule;
import app.care.extras.MyApplication;
import app.care.fragments.BaseFragment;
import dagger.Component;

@Singleton
@Component(modules = {MyDbModule.class})
public interface MyComponent {

    void inject(BaseActivity baseActivity);

    void inject(MyApplication  myApplication);

    void inject(BaseFragment baseFragment);

}
