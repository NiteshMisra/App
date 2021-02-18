package app.care.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import app.care.R;
import app.care.adapter.UserAdapter;
import app.care.db.MyEntity;
import app.care.extras.MainScreenInterface;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListingFragment extends BaseFragment {

    @BindView(R.id.addUser_tv)
    TextView addUser;
    @BindView(R.id.usersRcv)
    RecyclerView usersRcv;
    @BindView(R.id.emptyLayout_ll)
    LinearLayout emptyLayout;
    @BindView(R.id.addUserBtn)
    MaterialButton addUserBtn;

    private UserAdapter userAdapter;

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_listing, container, false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addUser.setOnClickListener(view1 -> {
            ((MainScreenInterface)activity).addFragment(AddUserFragment.newInstance(),true);
        });

        addUserBtn.setOnClickListener(view1 -> {
            ((MainScreenInterface)activity).addFragment(AddUserFragment.newInstance(),true);
        });

        usersRcv.setLayoutManager(new LinearLayoutManager(activity));

        dbManager.getMyDbCallManager().getAllUsers().observe(activity,users -> {
            if (users == null){
                Toast.makeText(activity, "Some error occurred, Try again later", Toast.LENGTH_SHORT).show();
                return;
            }

            if (users.isEmpty()){
                emptyLayout.setVisibility(View.VISIBLE);
                usersRcv.setVisibility(View.GONE);
                return;
            }

            emptyLayout.setVisibility(View.GONE);
            usersRcv.setVisibility(View.VISIBLE);

            userAdapter = new UserAdapter(users,activity) {
                @Override
                protected void deleteUser(MyEntity user) {
                    deleteCurrentUser(user);
                }
            };
            usersRcv.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();

        });

    }

    private void deleteCurrentUser(MyEntity user) {
        dbManager.getMyDbCallManager().deleteUser(user).observe(activity,isDeleted ->{
            if (isDeleted == null){
                Toast.makeText(activity, "Some error occurred, Try again later", Toast.LENGTH_SHORT).show();
                return;
            }

            String message;
            if (isDeleted) message = "User deleted successfully";
            else message = "Some error occurred, Try again later";

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

        });
    }
}