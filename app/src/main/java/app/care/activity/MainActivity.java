package app.care.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import app.care.R;
import app.care.extras.MainScreenInterface;
import app.care.fragments.ListingFragment;

public class MainActivity extends BaseActivity implements MainScreenInterface {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        addFragment(ListingFragment.newInstance(),false);
    }

    @Override
    public void addFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment == null) return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container,fragment);
        if (addToBackStack) transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) fragmentManager.popBackStack();
        else super.onBackPressed();
    }
}