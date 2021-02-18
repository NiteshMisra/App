package app.care.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import javax.inject.Inject;
import app.care.di.ObjectManager;

public class BaseActivity extends AppCompatActivity {

    @Inject
    ObjectManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
