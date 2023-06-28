package com.pallavikaushik.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pallavikaushik.DataActivity;
import com.pallavikaushik.MainActivity;
import com.pallavikaushik.Model.Data;
import com.pallavikaushik.R;

import java.util.ArrayList;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder> {


    private final Context context;
    private final ArrayList<Data> stockList;

    public StockListAdapter(Context context, ArrayList<Data> stockList) {
        this.context = context;
        this.stockList = stockList;
    }


    @NonNull
    @Override
    public StockListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_main_stock_list_item,
                parent,
                false);

        view.setFocusable(true);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockListAdapter.ViewHolder holder, int position) {

        checkForPriceUpdates(holder, position);

        holder.stockName.setText(stockList.get(position).getDummyName());
        holder.open.setText(String.valueOf(stockList.get(position).getOpen()));
        holder.high.setText(String.valueOf(stockList.get(position).getHigh()));
        holder.low.setText(String.valueOf(stockList.get(position).getLow()));
        holder.price.setText(String.valueOf(stockList.get(position).getCurrentPrice()));
        holder.silverOrGoldOpen.setText(String.valueOf(stockList.getSilverOrGoldOpen()));
        holder.silverOrGoldHigh.setText(String.valueOf(stockList.getSilverOrGoldHigh()));
        holder.silverOrGoldLow.setText(String.valueOf(stockList.getSilverOrGoldLow()));
        holder.silverOrGoldCurrentPrice.setText(String.valueOf(stockList.getSilverOrGoldCurrentPrice()));


    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView stockName;
        private final TextView price;
        private final TextView high;
        private final TextView low;
        private final TextView open;
        private final TextView silverOrGoldOpen;
        private final TextView silverOrGoldHigh;
        private final TextView silverOrGoldLow;
        private final TextView silverOrGoldCurrentPrice;

        private static final String TAG = "AviralKaushik";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            stockName = itemView.findViewById(R.id.stock_name);
            price = itemView.findViewById(R.id.current_price);
            high = itemView.findViewById(R.id.high_price);
            low = itemView.findViewById(R.id.low_price);
            open = itemView.findViewById(R.id.open_price);
            silverOrGoldCurrentPrice = itemView.findViewById(R.id.silver_gold_current_price);
            silverOrGoldOpen = itemView.findViewById(R.id.silver_gold_open_price);
            silverOrGoldHigh = itemView.findViewById(R.id.silver_gold_high_price);
            silverOrGoldLow = itemView.findViewById(R.id.silver_gold_low_price);
        }

        @Override
        public void onClick(View v) {


            MainActivity.GetData getData = new MainActivity.GetData((MainActivity) context);
            getData.cancel(true);

            Log.d(TAG, "onClick: Item Clicked is:" + getAdapterPosition());
            Log.d(TAG, "onClick: Item Clicked is:" + stockList.get(getAdapterPosition()).getName());

            Intent intent = new Intent(context, DataActivity.class);
            intent.putExtra(context.getString(R.string.dummy_stock_name),
                    stockList.get(getAdapterPosition()).getName());
            intent.putParcelableArrayListExtra(context.getString(R.string.stock_data), stockList);
            context.startActivity(intent);
        }
    }

    private void checkForPriceUpdates(ViewHolder holder, int pos) {

        if (Double.parseDouble(String.valueOf(holder.price.getText()))
                > stockList.get(pos).getCurrentPrice()) {
            holder.price.setTextColor(context.getResources().getColor(R.color.low_red));
        } else if (Double.parseDouble(String.valueOf(holder.price.getText()))
                < stockList.get(pos).getCurrentPrice()) {
            holder.price.setTextColor(context.getResources().getColor(R.color.high_blue));
        }

        if (Double.parseDouble(String.valueOf(holder.high.getText()))
                < stockList.get(pos).getHigh()) {
            holder.price.setTextColor(context.getResources().getColor(R.color.high_blue));
        }

        if (Double.parseDouble(String.valueOf(holder.low.getText()))
                > stockList.get(pos).getLow()) {
            holder.price.setTextColor(context.getResources().getColor(R.color.low_red));
        }
    }
}
