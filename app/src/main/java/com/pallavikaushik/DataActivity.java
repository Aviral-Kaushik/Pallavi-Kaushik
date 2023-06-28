package com.pallavikaushik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pallavikaushik.Model.Data;
import com.pallavikaushik.Utils.ShowDataAdapter;
import com.pallavikaushik.Utils.Spacing;

import java.util.ArrayList;
import java.util.Objects;

public class DataActivity extends AppCompatActivity {


    private ArrayList<Data> unSortedStockData;
    private String stockName;
    private Context context;

    private RecyclerView dataRecyclerView;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://pallavi-kaushik-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = firebaseDatabase.getReference();

        context = DataActivity.this;

        dataRecyclerView = findViewById(R.id.show_data_recycler_view);
        ProgressBar progressBar = findViewById(R.id.show_data_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Spacing spacing = new Spacing(63, 1);
        dataRecyclerView.addItemDecoration(spacing);


        LinearLayoutManager llm = new LinearLayoutManager(this);

        dataRecyclerView.setHasFixedSize(true);
        dataRecyclerView.setLayoutManager(llm);

        Intent intent = getIntent();

        if (intent.hasExtra(getString(R.string.stock_data))) {
            stockName = intent.getStringExtra(getString(R.string.dummy_stock_name));
            unSortedStockData = intent.getParcelableArrayListExtra(getString(R.string.stock_data));
            getData();
        }


        ImageView report = findViewById(R.id.report);

        progressBar.setVisibility(View.GONE);

        ArrayList<Data> SlvGldData = new ArrayList<>();

        for (Data data: unSortedStockData){
            if (data.getName().equals("SLV") || data.getName().equals("GLD")) {
                SlvGldData.add(data);
            }
        }

        report.setOnClickListener(view -> {
            Intent intentToData = new Intent(this, ReportActivity.class);
            intentToData.putParcelableArrayListExtra(getString(R.string.stock_data), SlvGldData);
            startActivity(intentToData);
        });
    }

    public void getData() {

        ArrayList<Data> stockData = new ArrayList<>();

        for (Data stock : unSortedStockData) {
            Query query = myRef.child(getString(R.string.data))
                    .child(stock.getName());

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {

                        Data data = singleSnapshot.getValue(Data.class);

                        if (Objects.requireNonNull(data).getName().equals(stockName)) {

                            stockData.add(data);

                        }

                        ShowDataAdapter showDataAdapter = new ShowDataAdapter(context, stockData);
                        dataRecyclerView.setAdapter(showDataAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        ShowDataAdapter showDataAdapter = new ShowDataAdapter(this, stockData);
        dataRecyclerView.setAdapter(showDataAdapter);
    }
}