package app.care.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.util.List;
import app.care.R;
import app.care.db.MyEntity;
import app.care.extras.AppUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static android.app.Activity.RESULT_OK;

@SuppressLint("all")
public class AddUserFragment extends BaseFragment {

    @BindView(R.id.user_image_iv)
    ImageView userImage;
    @BindView(R.id.username_edt)
    EditText userNameEdt;
    @BindView(R.id.userId_edt)
    EditText userIdEdt;
    @BindView(R.id.address_edt)
    EditText userAddressEdt;
    @BindView(R.id.department_edt)
    EditText departmentEdt;
    @BindView(R.id.addUserBtn)
    MaterialButton addUserBtn;


    private String imagePath = null;

    public static AddUserFragment newInstance() {
        return new AddUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_user, container, false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addUserBtn.setOnClickListener(view1 -> {
            addUser();
        });
    }

    private void addUser() {

        if (TextUtils.isEmpty(imagePath)){
            Toast.makeText(activity, "Select image", Toast.LENGTH_SHORT).show();
            return;
        }

        String userName = userNameEdt.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(activity, "Enter user name", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = userIdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(userId)){
            Toast.makeText(activity, "Enter user id", Toast.LENGTH_SHORT).show();
            return;
        }

        String userDepartment = departmentEdt.getText().toString().trim();
        if (TextUtils.isEmpty(userDepartment)) {
            Toast.makeText(activity, "Enter user department", Toast.LENGTH_SHORT).show();
            return;
        }

        String userAddress = userAddressEdt.getText().toString().trim();
        if (TextUtils.isEmpty(userAddress)) {
            Toast.makeText(activity, "Enter user address", Toast.LENGTH_SHORT).show();
            return;
        }

        MyEntity user = new MyEntity(userName,userId,userDepartment,userAddress,imagePath);
        dbManager.getMyDbCallManager().insertUser(user).observe(activity,isAdded -> {
            if (isAdded == null){
                Toast.makeText(activity, "Some error occurred, Try again later", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isAdded){
                Toast.makeText(activity, "Some error occurred, Try again later", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isAdded) {
                Toast.makeText(activity, "User added successfully", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            }

        });
    }

    @OnClick(R.id.userImageCard)
    void selectProfilePicture(View view) {
        TedPermission.with(activity)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("We need storage permission to access images")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private final PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }else {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
            }
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Log.e("data","uri " + selectedImage);
            try {
                if (selectedImage == null) {
                    Toast.makeText(activity, "Some error occurred, Try again later", Toast.LENGTH_SHORT).show();
                    return;
                }

                String path = AppUtils.getRealPathFromURI(activity, selectedImage);
                Log.e("data","path " + path);
                if (TextUtils.isEmpty(path)) {
                    hideProgress();
                    Toast.makeText(activity, "Some error occurred, Try again later", Toast.LENGTH_SHORT).show();
                    return;
                }

                imagePath = path;
                Log.e("data",imagePath);
                if (!TextUtils.isEmpty(imagePath)) Glide.with(activity).load(imagePath).centerCrop().into(userImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}