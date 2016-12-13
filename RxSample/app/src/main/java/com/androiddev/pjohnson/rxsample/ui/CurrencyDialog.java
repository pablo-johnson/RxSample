package com.androiddev.pjohnson.rxsample.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.androiddev.pjohnson.rxsample.R;
import com.androiddev.pjohnson.rxsample.adapters.ExchangeRecyclerAdapter;
import com.androiddev.pjohnson.rxsample.model.Currency;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by pjohnson on 12/13/16.
 */

public class CurrencyDialog extends DialogFragment implements ExchangeRecyclerAdapter.OnCurrencyClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_currency, null);
        setUpRecyclerView(view);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chooseCurrencyLabel)
                .setView(view);
        return builder.create();
    }

    private void setUpRecyclerView(View view) {
        RecyclerView exchangesRecyclerView = (RecyclerView) view.findViewById(R.id.exchangesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        exchangesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        exchangesRecyclerView.setLayoutManager(linearLayoutManager);
        ExchangeRecyclerAdapter exchangeRecyclerAdapter = new ExchangeRecyclerAdapter(getActivity(), new ArrayList<Currency>());
        exchangeRecyclerAdapter.setListener(this);
        exchangesRecyclerView.setAdapter(exchangeRecyclerAdapter);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        exchangeRecyclerAdapter.setData(new ArrayList<>(realm.where(Currency.class).findAll()));
    }


    @Override
    public void onCurrencyClicked(@NonNull Currency currency, View view, int position) {

    }
}
