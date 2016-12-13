package com.androiddev.pjohnson.rxsample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androiddev.pjohnson.rxsample.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer s) {
                        return true;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        switch (integer) {
                            case 5:
                                return "cinco";
                            case 1:
                                return "uno";
                            case 2:
                                return "dos";
                            case 3:
                                return "tres";
                            case 4:
                                return "cuatro";
                            default:
                                return "No Hay!!";
                        }
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("Pablo", "" + s);
                    }
                });


        Observable.just("Hello, world!").subscribe(s -> System.out.println(s));

    }
}
