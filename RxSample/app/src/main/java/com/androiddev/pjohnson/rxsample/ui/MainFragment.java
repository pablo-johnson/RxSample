package com.androiddev.pjohnson.rxsample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androiddev.pjohnson.rxsample.R;
import com.androiddev.pjohnson.rxsample.adapters.ExchangeRecyclerAdapter;
import com.androiddev.pjohnson.rxsample.model.Currency;
import com.androiddev.pjohnson.rxsample.networking.ExchangeApi;
import com.androiddev.pjohnson.rxsample.networking.responses.ExchangeResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements ExchangeRecyclerAdapter.OnCurrencyClickListener {

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.exchangesList)
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        exchangesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        exchangesRecyclerView.setLayoutManager(linearLayoutManager);
        exchangeRecyclerAdapter = new ExchangeRecyclerAdapter(getActivity(), new ArrayList<Currency>());
        exchangeRecyclerAdapter.setListener(this);
        exchangesRecyclerView.setAdapter(exchangeRecyclerAdapter);
        ExchangeApi.get().getRetrofitService().getLatestExchanges();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

    }
}
