package com.pallavikaushik;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pallavikaushik.Model.Data;
import com.pallavikaushik.Utils.CheckTime;
import com.pallavikaushik.Utils.FirebaseMethods;
import com.pallavikaushik.Utils.StockListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private GetData getCurrentData;

    private ArrayList<String> stockUrl;
    private ArrayList<String> stockName;
    private ArrayList<String> dummyNames;
    private FirebaseMethods firebaseMethods;

    private RecyclerView stockList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        stockList = findViewById(R.id.stock_list);
        stockList.setHasFixedSize(true);
        stockList.setLayoutManager(new LinearLayoutManager(this));

        context = MainActivity.this;

        firebaseMethods = new FirebaseMethods(context);
        firebaseMethods.signInTheOnlyUser();

        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        stockList.addItemDecoration(decoration);

        stockUrl = new ArrayList<>();
        stockName = new ArrayList<>();
        dummyNames = new ArrayList<>();

        stockName.add("SLV");
        stockName.add("GLD");
        stockName.add("RWM");
        stockName.add("IWM");
        stockName.add("ADWANIHOTR");
        stockName.add("USO");
        stockName.add("UNG");

        dummyNames.add("SI");
        dummyNames.add("GO");
        dummyNames.add("RW");
        dummyNames.add("IW");
        dummyNames.add("AD");
        dummyNames.add("CR");
        dummyNames.add("NG");

        stockUrl.add("https://in.investing.com/etfs/ishares-silver-trust-historical-data");
        stockUrl.add("https://in.investing.com/etfs/spdr-gold-trust-historical-data");
        stockUrl.add("https://in.investing.com/etfs/proshares-short-russell2000-historical-data");
        stockUrl.add("https://in.investing.com/etfs/ishares-russell-2000-index-etf-historical-data");
        stockUrl.add("https://in.investing.com/equities/advani-hotels-resorts-india-historical-data");
        stockUrl.add("https://in.investing.com/etfs/united-states-oil-fund-historical-data");
        stockUrl.add("https://in.investing.com/etfs/us-natural-gas-fund-historical-data");

        getCurrentData = new GetData(this);

        getCurrentData.execute();

    }

    public static class GetData extends AsyncTask<Void, Void, ArrayList<Data>> {

        WeakReference<MainActivity> activityWeakReference;

        ArrayList<Data> stockDetails = new ArrayList<>();

        public GetData(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected ArrayList<Data> doInBackground(Void... voids) {

            MainActivity activity = activityWeakReference.get();

            for (int i = 0; i < activity.stockUrl.size() && i < activity.stockName.size(); i++) {

                Data data = activity.getStockData(
                        activity.stockUrl.get(i),
                        activity.stockName.get(i),
                        activity.dummyNames.get(i));

                stockDetails.add(data);

            }

            return stockDetails;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> data) {

            MainActivity activity = activityWeakReference.get();

            activity.progressBar.setVisibility(View.GONE);

            StockListAdapter stockListAdapter = new StockListAdapter(activity, stockDetails);
            activity.stockList.setAdapter(stockListAdapter);

            activity.firebaseMethods = new FirebaseMethods(activity);
            activity.firebaseMethods.insertData(stockDetails);

            if (activity.getCurrentData.getStatus() == Status.FINISHED) {
                activity.executeAgain();
            }
        }
    }

    private Data getStockData(String url, String stockName, String dummyName) {

        try {

                String date;

                Document slv = Jsoup.connect(url).get();

                Elements elementPrice = slv.getElementsByClass("last-price-value");
                Elements elementOpen = slv.getElementsByClass("col-last_open");
                Elements elementHigh = slv.getElementsByClass("col-last_max");
                Elements elementLow = slv.getElementsByClass("col-last_min");

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();

                if (CheckTime.isTimeGreaterThanNineAM()) {
                    // Time is or after 7:00 PM
                    date = dateFormat.format(new Date());
                } else {
                    // Time is before 7:00 PM
                    calendar.add(Calendar.DATE, -1);
                    date = dateFormat.format(calendar.getTime());
                }

                ArrayList<Integer> silverGldData;

                if (stockName.equals("GLD")) {
                    silverGldData = getSilverGoldData( "https://in.investing.com/commodities/gold-mini-historical-data");
                } else {
                    silverGldData = getSilverGoldData( "https://in.investing.com/commodities/silver-historical-data?cid=49791");
                }

                return new Data(stockName,
                        Float.parseFloat(elementOpen.text().substring(5, 10)),
                        Float.parseFloat(elementHigh.text().substring(5, 10)),
                        Float.parseFloat(elementLow.text().substring(4, 9)),
                        Float.parseFloat(elementPrice.text()),
                        date,
                        dummyName,
                        Objects.requireNonNull(silverGldData).get(0),
                        silverGldData.get(1),
                        silverGldData.get(2),
                        silverGldData.get(3),
                        null,
                        "0");

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(context, "Cannot Load Data", Toast.LENGTH_SHORT).show();

            return null;
        }
    }

    private ArrayList<Integer> getSilverGoldData( String stockUrl) {
        try {

            Document slv = Jsoup.connect(stockUrl).get();

            Elements elementPrice = slv.getElementsByClass("last-price-value");
            Elements elementOpen = slv.getElementsByClass("col-last_open");
            Elements elementHigh = slv.getElementsByClass("col-last_max");
            Elements elementLow = slv.getElementsByClass("col-last_min");

            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(Integer.parseInt(elementOpen.text().substring(5, 11).replace(",", "")));
            arrayList.add(Integer.parseInt(elementHigh.text().substring(5, 11).replace(",", "")));
            arrayList.add(Integer.parseInt(elementLow.text().substring(4, 10).replace(",", "")));
            arrayList.add(Integer.parseInt(elementPrice.text().replace(",", "")));

            return arrayList;

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(context, "Cannot Load Data", Toast.LENGTH_SHORT).show();

            return null;
        }
    }



    private void executeAgain() {

        GetData getDataAgain = new GetData(this);
        getDataAgain.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        executeAgain();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        executeAgain();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getCurrentData.cancel(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getCurrentData.cancel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getCurrentData.cancel(true);
    }
}