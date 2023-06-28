package com.pallavikaushik.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pallavikaushik.Model.Data;
import com.pallavikaushik.R;

import java.util.ArrayList;

public class FirebaseMethods {

    private static final String TAG = "IWMDate";

    private final DatabaseReference myRef;
    private final FirebaseAuth mAuth;

    private final Context context;

    public FirebaseMethods(Context mContext) {
        context = mContext;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://pallavi-kaushik-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
    }

    public void insertData(ArrayList<Data> stockData) {

        for (Data data : stockData) {
            myRef.child(context.getString(R.string.data))
                    .child(data.getName())
                    .child(data.getDate())
                    .setValue(data);
        }
    }

    public void deleteData() {
        myRef.child(context.getString(R.string.data))
                .removeValue();
    }

    public void signInTheOnlyUser() {

        String email = context.getString(R.string.email);
        String password= context.getString(R.string.password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Log in Unsuccessful" + task.getException());

                        Toast.makeText(context, "Cannot Sign In", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
