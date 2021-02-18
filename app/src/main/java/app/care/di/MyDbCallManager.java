package app.care.di;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;
import app.care.db.AppDatabase;
import app.care.db.MyEntity;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyDbCallManager {

    private AppDatabase appDatabase;

    public MyDbCallManager(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public LiveData<ArrayList<MyEntity>> getAllUsers() {
        MutableLiveData<ArrayList<MyEntity>> data = new MutableLiveData<>();
        appDatabase.getMyDAO().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyEntity>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<MyEntity> dailyChartEntities) {
                        data.postValue((ArrayList<MyEntity>) dailyChartEntities);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        data.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> insertUser(MyEntity user) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        ArrayList<MyEntity> list = new ArrayList<>();
        list.add(user);
        appDatabase.getMyDAO().insertAll(list)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        data.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        data.postValue(false);
                    }
                });
        return data;
    }

    public MutableLiveData<Integer> isUserExist(String userId) {
        MutableLiveData<Integer> data = new MutableLiveData<>();

        appDatabase.getMyDAO().isUserExist(userId)
                .subscribeOn(Schedulers.io())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        data.postValue(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        data.postValue(-1);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> deleteUser(MyEntity user) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        appDatabase.getMyDAO().delete(user)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        data.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        data.postValue(false);
                    }
                });
        return data;
    }

}
