package com.pallavikaushik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pallavikaushik.Model.Data;
import com.pallavikaushik.Utils.CheckTime;
import com.pallavikaushik.Utils.Notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = "IWMDate";

    private DatabaseReference myRef;

    private String yesterdayDate;
    private String dateForRWM;
    private String dateCheckForRwmIwm;

    private TextView indicator, slvPoint, gldPoint, rwmPoint, iwmPoint;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://pallavi-kaushik-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = firebaseDatabase.getReference();

        indicator = findViewById(R.id.indicator);
        slvPoint = findViewById(R.id.slv_point);
        gldPoint = findViewById(R.id.gld_point);
        rwmPoint = findViewById(R.id.rwm_point);
        iwmPoint = findViewById(R.id.iwm_point);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        ArrayList<Data> slvGldRwmIwmData = intent.getParcelableArrayListExtra(getString(R.string.stock_data));

        yesterdayDate = getYesterdayDate();
        dateForRWM = getDateForRWM();
        dateCheckForRwmIwm = getCheckDateForRwmIwm();

        ArrayList<String> stockName = new ArrayList<>();
        stockName.add("SLV");
        stockName.add("GLD");
        stockName.add("RWM");
        stockName.add("IWM");

        getDataForIndication(stockName, slvGldRwmIwmData);

    }

    private String getYesterdayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        if (CheckTime.isTimeGreaterThanSevenPM()) {
            Log.d(TAG, "getYesterdayDate: Time is or after 7:00 PM ");
            calendar.add(Calendar.DATE, -1);
        } else {
            Log.d(TAG, "getYesterdayDate: Time is before 7:00 PM ");
            calendar.add(Calendar.DATE, -2);
        }

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        Log.d(TAG, "getYesterdayDate: Yesterday date is:" + dateFormat.format(calendar.getTime()));

        return dateFormat.format(calendar.getTime());
    }

    private String getDateForRWM() {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        if (CheckTime.isTimeGreaterThanSevenPM()) {
            Log.d(TAG, "getYesterdayDate: Time is or after 7:00 PM ");
            calendar.add(Calendar.DATE, -3);
        } else {
            Log.d(TAG, "getYesterdayDate: Time is before 7:00 PM ");
            calendar.add(Calendar.DATE, -4);
        }

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        Log.d(TAG, "onCreate: Date for IWM is: " + format.format(calendar.getTime()));

        return format.format(calendar.getTime());
    }

    private String getCheckDateForRwmIwm() {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        if (CheckTime.isTimeGreaterThanSevenPM()) {
            Log.d(TAG, "getYesterdayDate: Time is or after 7:00 PM ");
            calendar.add(Calendar.DATE, -4);
        } else {
            Log.d(TAG, "getYesterdayDate: Time is before 7:00 PM ");
            calendar.add(Calendar.DATE, -5);
        }

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        Log.d(TAG, "onCreate: Check Date for IWM is: " + format.format(calendar.getTime()));

        return format.format(calendar.getTime());
    }

    public void getDataForIndication(ArrayList<String> stockName, ArrayList<Data> slvGldRwmIwmOldData) {

        ArrayList<Data> stockListForIndication = new ArrayList<>();

        for (String name : stockName) {
            Query query = myRef.child(getString(R.string.data))
                    .child(name);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                        Data data = singleSnapshot.getValue(Data.class);

                        if (Objects.requireNonNull(data).getDate().equals(yesterdayDate)
                                && (data.getName().equals("SLV") || data.getName().equals("GLD"))) {
                            stockListForIndication.add(data);
                            Log.d(TAG, "onDataChange: Adding data in list:  " + data.getName() + " " + data.getDate());
                        }

                        if (data.getDate().equals(dateForRWM)
                                && (data.getName().equals("RWM") || data.getName().equals("IWM"))) {
                            stockListForIndication.add(data);
                            Log.d(TAG, "onDataChange: Adding data in list:  " + data.getName() + " " + data.getDate());
                        }

                        if (data.getDate().equals(dateCheckForRwmIwm) &&
                                (data.getName().equals("RWM") || data.getName().equals("IWM"))) {
                            slvGldRwmIwmOldData.add(data);
                            Log.d(TAG, "onDataChange: Adding data in slvGldRwmIwm list:  " + data.getName() + " " + data.getDate());
                        }

                    }
                    checkForResultAndUpdateUI(stockListForIndication, slvGldRwmIwmOldData);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void checkForResultAndUpdateUI(ArrayList<Data> stockData, ArrayList<Data> slvGldRwmIwmOldData) {


        for (Data data: slvGldRwmIwmOldData) {
            Log.d(TAG, "onCreate: old Data from  main: " + data.getName() + " " + data.getDate());
        }

        int buyPoints = 0, sellPoints = 0, bothSidePoints = 0, notDecidedPoints = 0;

        for (Data data : stockData) {
            for (Data todayData : slvGldRwmIwmOldData) {
                if (data.getName().equals("SLV") && todayData.getName().equals("SLV")) {

                    // SLV
                    if (todayData.getHigh() > data.getHigh()) {
                        slvPoint.setText(getString(R.string.SlV_breaks_high));
                        buyPoints += 100;

                        new Notification(this,
                                getString(R.string.silver_buy),
                                getString(R.string.SlV_breaks_high));
                    }
                    if (todayData.getLow() < data.getLow()) {
                        slvPoint.setText(getString(R.string.SlV_breaks_low));
                        sellPoints += 100;

                        new Notification(this,
                                getString(R.string.silver_sell),
                                getString(R.string.SlV_breaks_low));
                    }
                    if (todayData.getHigh() > data.getHigh() && todayData.getLow() < data.getLow()) {
                        slvPoint.setText(getString(R.string.SLV_breaks_high_low));
                        bothSidePoints += 100;

                        new Notification(this,
                                getString(R.string.silver_any_side),
                                getString(R.string.SLV_breaks_high_low));
                    }
                    if (todayData.getHigh() < data.getHigh() && todayData.getLow() > data.getLow()) {
                        slvPoint.setText(getString(R.string.SlV_not_breaks_high_low));
                        notDecidedPoints += 100;
                    }
                }

                // GLD
                if (data.getName().equals("GLD") && todayData.getName().equals("GLD")) {
                    if (todayData.getHigh() > data.getHigh()) {
                        gldPoint.setText(getString(R.string.GLD_breaks_high));
                        buyPoints += 100;

                        new Notification(this,
                                getString(R.string.silver_buy),
                                getString(R.string.GLD_breaks_high));
                    }
                    if (todayData.getLow() < data.getLow()) {
                        gldPoint.setText(getString(R.string.GLD_breaks_low));
                        sellPoints += 100;

                        new Notification(this,
                                getString(R.string.silver_sell),
                                getString(R.string.GLD_breaks_low));
                    }
                    if (todayData.getHigh() > data.getHigh() && todayData.getLow() < data.getLow()) {
                        gldPoint.setText(getString(R.string.GLD_breaks_high_low));
                        bothSidePoints += 100;

                        new Notification(this,
                                getString(R.string.silver_buy),
                                getString(R.string.GLD_breaks_high_low));
                    }
                    if (todayData.getHigh() < data.getHigh() && todayData.getLow() > data.getLow()) {
                        gldPoint.setText(getString(R.string.GLD_not_breaks_high_low));
                        notDecidedPoints += 100;
                    }
                }

                // RWM
                if (data.getName().equals("RWM") && todayData.getName().equals("RWM")) {
                    // toady -> old
                    Log.d(TAG, "checkForResultAndUpdateUI: RWM Today High: " +  todayData.getHigh());
                    Log.d(TAG, "checkForResultAndUpdateUI: RWM Today low: " + todayData.getLow());
                    Log.d(TAG, "checkForResultAndUpdateUI: RWM OLd High: " +  data.getHigh());
                    Log.d(TAG, "checkForResultAndUpdateUI: RWM OLd Low: " + data.getLow());
                    if (todayData.getHigh() < data.getHigh()) {
                        rwmPoint.setText(getString(R.string.RWM_breaks_high));
                        buyPoints += 100;
                    }
                    if (todayData.getLow() > data.getLow()) {
                        rwmPoint.setText(getString(R.string.RWM_breaks_low));
                        sellPoints += 100;
                    }
                    if (todayData.getHigh() > data.getHigh() && todayData.getLow() < data.getLow()) {
                        rwmPoint.setText(getString(R.string.RWM_breaks_high_low));
                        bothSidePoints += 100;
                    }
                    if (todayData.getHigh() < data.getHigh() && todayData.getLow() > data.getLow()) {
                        rwmPoint.setText(getString(R.string.RWM_not_breaks_high_low));
                        notDecidedPoints += 100;
                    }
                }

                // IWM
                if (data.getName().equals("IWM") && todayData.getName().equals("IWM")) {
                    if (todayData.getHigh() < data.getHigh()) {
                        iwmPoint.setText(getString(R.string.IWM_breaks_high));
                        sellPoints += 100;
                    }
                    if (todayData.getLow() > data.getLow()) {
                        iwmPoint.setText(getString(R.string.IWM_breaks_low));
                        buyPoints += 100;
                    }
                    if (todayData.getHigh() > data.getHigh() && todayData.getLow() < data.getLow()) {
                        iwmPoint.setText(getString(R.string.IWM_breaks_high_low));
                        bothSidePoints += 100;
                    }
                    if (todayData.getHigh() < data.getHigh() && todayData.getLow() > data.getLow()) {
                        iwmPoint.setText(getString(R.string.IWM_not_breaks_high_low));
                        notDecidedPoints += 100;
                    }
                }
            }

            if (buyPoints > sellPoints
                    && buyPoints > bothSidePoints
                    &&  buyPoints > notDecidedPoints) {
                indicator.setText(getString(R.string.buy));
                indicator.setTextColor(getResources().getColor(R.color.silver_buy));
            }

            else if (sellPoints > buyPoints
                    && sellPoints > bothSidePoints
                    && sellPoints > notDecidedPoints) {
                indicator.setText(getString(R.string.sell));
                indicator.setTextColor(getResources().getColor(R.color.silver_sell));
            }

            else if (bothSidePoints > buyPoints
                    && bothSidePoints > sellPoints
                    && bothSidePoints > notDecidedPoints) {
                indicator.setText(getString(R.string.both_side));
                indicator.setTextColor(getResources().getColor(R.color.may_be_both_side));
            }

            else if (notDecidedPoints > buyPoints
                    && notDecidedPoints > bothSidePoints
                    && notDecidedPoints > sellPoints) {
                indicator.setText(getString(R.string.not_decided_yet));
                indicator.setTextColor(getResources().getColor(R.color.not_decided_yet));
            }

            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "checkForResultAndSendNotification: BuyPoints: " + buyPoints);
            Log.d(TAG, "checkForResultAndSendNotification: SellPoints: " + sellPoints);
            Log.d(TAG, "checkForResultAndSendNotification: BothSidePoints: " + bothSidePoints);
            Log.d(TAG, "checkForResultAndSendNotification: NotDecidedPoints: " + notDecidedPoints);

            progressBar.setVisibility(View.GONE);
        }

    }
}