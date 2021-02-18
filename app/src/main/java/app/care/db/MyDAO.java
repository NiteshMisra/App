package app.care.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface MyDAO {

    @Query("SELECT * FROM MyEntity")
    Observable<List<MyEntity>> getAllUsers();

    @Query("SELECT count(*) FROM MyEntity where userId = :userId")
    Maybe<Integer> isUserExist(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<MyEntity> userData);

    @Delete
    Completable delete(MyEntity user);


}
