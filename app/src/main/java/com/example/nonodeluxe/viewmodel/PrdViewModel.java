package com.example.nonodeluxe.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.nonodeluxe.util.FirebaseQueryLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrdViewModel extends ViewModel {

    private static final DatabaseReference ref =
            FirebaseDatabase.getInstance().getReference("/real/product");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ref);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData(){
        return liveData;
    }



}
