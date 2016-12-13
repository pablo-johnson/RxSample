package com.androiddev.pjohnson.rxsample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androiddev.pjohnson.rxsample.R;
import com.androiddev.pjohnson.rxsample.model.Currency;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Pablo Johnson (pablo.88j@gmail.com)
 */
public class ExchangeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Currency> currencies;
    private final LayoutInflater mInflater;
    @NonNull
    private OnCurrencyClickListener mListener;

    public interface OnCurrencyClickListener {
        void onCurrencyClicked(@NonNull final Currency currency, View view, int position);
    }

    public ExchangeRecyclerAdapter(Context context, ArrayList<Currency> currencies) {
        this.currencies = currencies;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CurrencyHolder(mInflater.inflate(R.layout.item_currency, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Currency currency = currencies.get(position);
        ((CurrencyHolder) holder).currencyName.setText(currency.getName());
        ((CurrencyHolder) holder).currency.setText(currency.getCurrency());
        ((CurrencyHolder) holder).currencyValue.setText(currency.getValue() != null ? currency.getValue().toString() : null);
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public void setListener(@NonNull OnCurrencyClickListener listener) {
        this.mListener = listener;
    }

    public void setData(List<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    final class CurrencyHolder extends RecyclerView.ViewHolder {

        TextView currency;
        TextView currencyValue;
        TextView currencyName;

        public CurrencyHolder(View itemView) {
            super(itemView);
            currencyName = (TextView) itemView.findViewById(R.id.currencyName);
            currency = (TextView) itemView.findViewById(R.id.currency);
            currencyValue = (TextView) itemView.findViewById(R.id.currencyValue);
        }
    }
}
