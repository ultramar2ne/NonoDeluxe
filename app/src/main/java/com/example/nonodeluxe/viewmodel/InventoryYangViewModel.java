package com.example.nonodeluxe.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nonodeluxe.util.FirebaseQueryLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventoryYangViewModel extends ViewModel {

    private static final DatabaseReference ref =
            FirebaseDatabase.getInstance().getReference("/dist/inventory");

    private FirebaseQueryLiveData liveData;

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData(){
        liveData = new FirebaseQueryLiveData(ref);
        return liveData;
    }

    public LiveData<DataSnapshot> getRestrictedSnapshotLiveData(){
        liveData = new FirebaseQueryLiveData(ref.limitToFirst(5));
//        liveData = new FirebaseQueryLiveData(ref.orderByChild("name/2"));
        return liveData;
    }

}
