package com.androiddev.pjohnson.rxsample.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androiddev.pjohnson.rxsample.R;
import com.androiddev.pjohnson.rxsample.model.Currency;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    private static final String DATA_LOADED_KEY = "dataLoadedKey";
    public static final String DB_NAME = "exchange_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        if (!preferences.getBoolean(DATA_LOADED_KEY, false)) {
            try {
                loadInitialData(getAssets().open("currenciesNames.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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


        Observable.just("Hello, world!").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, MainFragment.newInstance(), "Rx").commit();
        }

    }

    public void loadInitialData(InputStream is) throws IOException {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String json = new String(buffer, "UTF-8");
        Type listType = new TypeToken<Map<String, String>>() {
        }.getType();
        final Map<String, String> currencyMap = new GsonBuilder().create().fromJson(json, listType);
        final List<Currency> currencyList = new ArrayList<>();
        for (Map.Entry<String, String> entry : currencyMap.entrySet()) {
            Currency currency = new Currency();
            currency.setName(entry.getValue());
            currency.setCurrency(entry.getKey());
            currencyList.add(currency);
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(currencyList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                SharedPreferences.Editor editor = getSharedPreferences(DB_NAME, Context.MODE_PRIVATE).edit();
                editor.putBoolean(DATA_LOADED_KEY, true);
                editor.apply();
            }
        });
    }
}
