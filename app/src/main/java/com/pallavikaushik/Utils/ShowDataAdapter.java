package com.pallavikaushik.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pallavikaushik.Model.Data;
import com.pallavikaushik.R;

import java.util.ArrayList;

public class ShowDataAdapter extends  RecyclerView.Adapter<ShowDataAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Data> stockList;

    public ShowDataAdapter(Context context, ArrayList<Data> stockList) {
        this.context = context;
        this.stockList = stockList;
    }


    @NonNull
    @Override
    public ShowDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_stock_list_item,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDataAdapter.ViewHolder holder, int position) {
        holder.stockName.setText(stockList.get(position).getName());
        holder.date.setText(stockList.get(position).getDate());
        holder.price.setText(String.valueOf(stockList.get(position).getCurrentPrice()));
        holder.open.setText(String.valueOf(stockList.get(position).getOpen()));
        holder.high.setText(String.valueOf(stockList.get(position).getHigh()));
        holder.low.setText(String.valueOf(stockList.get(position).getLow()));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView stockName;
        private final TextView price;
        private final TextView open;
        private final TextView high;
        private final TextView low;
        private final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stockName = itemView.findViewById(R.id.show_stock_name);
            price = itemView.findViewById(R.id.show_current_price);
            open = itemView.findViewById(R.id.show_open_price);
            high = itemView.findViewById(R.id.show_high_price);
            low = itemView.findViewById(R.id.show_low_price);
            date = itemView.findViewById(R.id.show_date);
        }
    }
}
