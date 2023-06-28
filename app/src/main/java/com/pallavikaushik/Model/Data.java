package com.pallavikaushik.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    // TODO: 01-10-2022 We need to modify the model according to our needs
    // TODO: 01-10-2022 We need to add silver data in SLV, RWM, IWM, ADVANIHOTAR listview
    // TODO: 01-10-2022 We need to add gold data for GLD listview
    // TODO: 01-10-2022 We need to check does high has broken first or low has been broken first
    // TODO: 01-10-2022 We need to get high and low for each stock and show them in corners of the layout from monthly page in investing.com
    // TODO: 01-10-2022 We have to add a dummy name in each object and show them instead of the right name.
    // TODO: 01-10-2022 We have to create two textview in between and color them accordingly what the stock is directing
    // TODO: 01-10-2022 buy -> both are green
    // TODO: 01-10-2022 sell -> both are red
    // TODO: 01-10-2022 Both sides -> different color in both
    // TODO: 01-10-2022 not decided -> no color in both
    // TODO: 01-10-2022 Remove delete button (Completed)
    // TODO: 01-10-2022 Also update the adapter of show data class
    // TODO: 01-10-2022 For silver and gold data we need to update it from 9:00 AM
    // TODO: 01-10-2022 Remove high, low, and open text from the view
    // TODO: 01-10-2022 When slv breaks high or low at that time we have to get the current silver rate and show them in below corner of our layout


    private String name;
    private float open;
    private float high;
    private float low;
    private float currentPrice;
    private String date;
    private String dummyName;
    private int silverOrGoldOpen;
    private int silverOrGoldHigh;
    private int silverOrGoldLow;
    private int silverOrGoldCurrentPrice;
    private String whichHasBrokenFirst;
    private String silverPriceWhenHighOrLowBroken;

    public Data(String name,
                float open,
                float high,
                float low,
                float currentPrice,
                String date,
                String dummyName,
                int silverOrGoldOpen,
                int silverOrGoldHigh,
                int silverOrGoldLow,
                int silverOrGoldCurrentPrice,
                String whichHasBrokenFirst,
                String silverPriceWhenHighOrLowBroken) {
        this.name = name;
        this.open = open;
        this.high = high;
        this.low = low;
        this.currentPrice = currentPrice;
        this.date = date;
        this.dummyName = dummyName;
        this.silverOrGoldOpen = silverOrGoldOpen;
        this.silverOrGoldHigh = silverOrGoldHigh;
        this.silverOrGoldLow = silverOrGoldLow;
        this.silverOrGoldCurrentPrice = silverOrGoldCurrentPrice;
        this.whichHasBrokenFirst = whichHasBrokenFirst;
        this.silverPriceWhenHighOrLowBroken = silverPriceWhenHighOrLowBroken;
    }

    protected Data(Parcel in) {
        name = in.readString();
        open = in.readFloat();
        high = in.readFloat();
        low = in.readFloat();
        currentPrice = in.readFloat();
        date = in.readString();
        dummyName = in.readString();
        silverOrGoldOpen = in.readInt();
        silverOrGoldHigh = in.readInt();
        silverOrGoldLow = in.readInt();
        silverOrGoldCurrentPrice = in.readInt();
        whichHasBrokenFirst = in.readString();
        silverPriceWhenHighOrLowBroken = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDummyName() {
        return dummyName;
    }

    public void setDummyName(String dummyName) {
        this.dummyName = dummyName;
    }

    public int getSilverOrGoldOpen() {
        return silverOrGoldOpen;
    }

    public void setSilverOrGoldOpen(int silverOrGoldOpen) {
        this.silverOrGoldOpen = silverOrGoldOpen;
    }

    public int getSilverOrGoldHigh() {
        return silverOrGoldHigh;
    }

    public void setSilverOrGoldHigh(int silverOrGoldHigh) {
        this.silverOrGoldHigh = silverOrGoldHigh;
    }

    public int getSilverOrGoldLow() {
        return silverOrGoldLow;
    }

    public void setSilverOrGoldLow(int silverOrGoldLow) {
        this.silverOrGoldLow = silverOrGoldLow;
    }

    public int getSilverOrGoldCurrentPrice() {
        return silverOrGoldCurrentPrice;
    }

    public void setSilverOrGoldCurrentPrice(int silverOrGoldCurrentPrice) {
        this.silverOrGoldCurrentPrice = silverOrGoldCurrentPrice;
    }

    public String getWhichHasBrokenFirst() {
        return whichHasBrokenFirst;
    }

    public void setWhichHasBrokenFirst(String whichHasBrokenFirst) {
        this.whichHasBrokenFirst = whichHasBrokenFirst;
    }

    public String getSilverPriceWhenHighOrLowBroken() {
        return silverPriceWhenHighOrLowBroken;
    }

    public void setSilverPriceWhenHighOrLowBroken(String silverPriceWhenHighOrLowBroken) {
        this.silverPriceWhenHighOrLowBroken = silverPriceWhenHighOrLowBroken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeFloat(open);
        parcel.writeFloat(high);
        parcel.writeFloat(low);
        parcel.writeFloat(currentPrice);
        parcel.writeString(date);
        parcel.writeString(dummyName);
        parcel.writeInt(silverOrGoldOpen);
        parcel.writeInt(silverOrGoldHigh);
        parcel.writeInt(silverOrGoldLow);
        parcel.writeInt(silverOrGoldCurrentPrice);
        parcel.writeString(whichHasBrokenFirst);
        parcel.writeString(silverPriceWhenHighOrLowBroken);
    }
}
