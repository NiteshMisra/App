package app.care.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MyEntity implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String username;

    private String userId;

    private String department;

    private String address;

    private String profilePath;

    public MyEntity(String username, String userId, String department, String address, String profilePath) {
        this.username = username;
        this.userId = userId;
        this.department = department;
        this.address = address;
        this.profilePath = profilePath;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDepartment() {
        return department;
    }

    public String getAddress() {
        return address;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
