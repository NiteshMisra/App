package app.care.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.Objects;
import javax.inject.Inject;
import app.care.R;
import app.care.di.ObjectManager;
import app.care.extras.MyApplication;

public class BaseFragment extends Fragment {

    @Inject
    ObjectManager dbManager;

    private ProgressDialog pDialog;
    public FragmentActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getDaggerComponent().inject(this);
        activity = requireActivity();
    }

    public void showProgressBar(String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(activity);
        }
        if (!pDialog.isShowing() && !activity.isFinishing()) {
            pDialog.setTitle(null);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            if (message == null){
                Objects.requireNonNull(pDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pDialog.show();
                pDialog.setContentView(R.layout.progress_bar_layout);
            }else if (message.isEmpty()){
                Objects.requireNonNull(pDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pDialog.show();
                pDialog.setContentView(R.layout.progress_bar_layout);
            }else {
                pDialog.setMessage(message);
                pDialog.show();
            }
        }
    }

    public void hideProgress() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
        }
    }

}
