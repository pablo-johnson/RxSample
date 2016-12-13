package com.androiddev.pjohnson.rxsample.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androiddev.pjohnson.rxsample.R;
import com.androiddev.pjohnson.rxsample.adapters.ExchangeRecyclerAdapter;
import com.androiddev.pjohnson.rxsample.model.Currency;
import com.androiddev.pjohnson.rxsample.networking.ExchangeApi;
import com.androiddev.pjohnson.rxsample.networking.responses.ExchangeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements ExchangeRecyclerAdapter.OnCurrencyClickListener {

    private static final String BASE_CURRENCY = "baseCurrency";
    private OnFragmentInteractionListener mListener;

    RecyclerView exchangesRecyclerView;
    private ExchangeRecyclerAdapter exchangeRecyclerAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        setUpRecyclerView(view);
        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.DB_NAME, Context.MODE_PRIVATE);
        TextView baseCurrencyTextView = (TextView) view.findViewById(R.id.baseCurrency);
        baseCurrencyTextView.setText(preferences.getString(BASE_CURRENCY, "EUR"));
        return view;
    }

    private void setUpRecyclerView(View view) {
        exchangesRecyclerView = (RecyclerView) view.findViewById(R.id.exchangesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        exchangesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        exchangesRecyclerView.setLayoutManager(linearLayoutManager);
        exchangeRecyclerAdapter = new ExchangeRecyclerAdapter(getActivity(), new ArrayList<Currency>());
        exchangeRecyclerAdapter.setListener(this);
        exchangesRecyclerView.setAdapter(exchangeRecyclerAdapter);
        ExchangeApi.get().getRetrofitService().getLatestExchanges()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<ExchangeResponse, Observable<List<Currency>>>() {
                    @Override
                    public Observable<List<Currency>> call(ExchangeResponse exchangeResponse) {
                        List<Currency> currencies = new ArrayList<>();
                        for (Map.Entry<String, Double> pair : exchangeResponse.getRates().entrySet()) {
                            Currency currency = new Currency();
                            currency.setCurrency(pair.getKey());
                            currency.setValue(pair.getValue());
                            currencies.add(currency);
                        }
                        return Observable.just(currencies);
                    }
                })
                .subscribe(new Action1<List<Currency>>() {
                    @Override
                    public void call(List<Currency> currencies) {
                        exchangeRecyclerAdapter.setData(currencies);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCurrencyClicked(@NonNull Currency currency, View view, int position) {

    }

    public interface OnFragmentInteractionListener {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.exchange_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.currency_action) {
            CurrencyDialog currencyDialog = new CurrencyDialog();
            currencyDialog.show(getFragmentManager(), "yeah");
        }
        return super.onOptionsItemSelected(item);
    }
}
