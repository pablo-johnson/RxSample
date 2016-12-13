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
    private int mViewType;
    public static int FROM_DIALOG_TYPE = 0;
    public static int FROM_LANDING_TYPE = 1;


    public interface OnCurrencyClickListener {
        void onCurrencyClicked(@NonNull final Currency currency, View view, int position);
    }

    public ExchangeRecyclerAdapter(Context context, ArrayList<Currency> currencies, int viewType) {
        this.currencies = currencies;
        mInflater = LayoutInflater.from(context);
        mViewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FROM_DIALOG_TYPE) {
            return new CurrencyHolder(mInflater.inflate(R.layout.item_dialog_currency, parent, false));
        } else {
            return new CurrencyHolder(mInflater.inflate(R.layout.item_currency, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Currency currency = currencies.get(position);
        ((CurrencyHolder) holder).currency.setText(currency.getCurrency());
        ((CurrencyHolder) holder).currencyName.setText(currency.getName());
        if (getItemViewType(position) == FROM_DIALOG_TYPE) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onCurrencyClicked(currency, view, holder.getAdapterPosition());
                }
            });
        } else {
            ((CurrencyHolder) holder).currencyValue.setText(currency.getValue() != null ? currency.getValue().toString() : null);
        }

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
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
