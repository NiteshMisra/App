package app.care.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import app.care.R;
import app.care.db.MyEntity;

public abstract class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {

    private ArrayList<MyEntity> usersList;
    private Context context;

    public UserAdapter(ArrayList<MyEntity> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    protected abstract void deleteUser(MyEntity user);

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_user,parent,false);
        return new UserVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        MyEntity currentUser = usersList.get(position);
        holder.username.setText(currentUser.getUsername());
        holder.address.setText(("Address : "+currentUser.getAddress()));
        holder.department.setText(("Department : "+currentUser.getDepartment()));

        Glide.with(context)
                .load(currentUser.getProfilePath())
                .circleCrop()
                .into(holder.userProfile);

        holder.delete.setOnClickListener(v -> deleteUser(currentUser));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UserVH extends RecyclerView.ViewHolder {

        TextView username, address, department;
        ImageView userProfile, delete;

        public UserVH(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username_tv);
            address = itemView.findViewById(R.id.address_tv);
            department = itemView.findViewById(R.id.department_tv);
            userProfile = itemView.findViewById(R.id.profile_image);
            delete = itemView.findViewById(R.id.delete_iv);

        }
    }

}
