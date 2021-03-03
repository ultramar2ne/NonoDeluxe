package com.example.nonodeluxe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nonodeluxe.PrdInfoActivity;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.ScanCodeActivity;
import com.example.nonodeluxe.model.PrdItem;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private ArrayList<PrdItem> prdItems = new ArrayList<>();

    private int MY_PERMISSION_REQUEST_CAMERA = 0;
    private ZXingScannerView scannerView;

    public ScanCodeFragment() {
    }

    public static ScanCodeFragment newInstance(ArrayList<PrdItem> prdItems){
        ScanCodeFragment fragment = new ScanCodeFragment();
        Bundle args = new Bundle();
        args.putSerializable("ETC",prdItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            prdItems = (ArrayList<PrdItem>) getArguments().getSerializable("ETC");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scanner,container,false);

        RelativeLayout relativeLayout = view.findViewById(R.id.layout_scanner);

        scannerView = new ZXingScannerView(view.getContext());

        return view;


    }

    @Override
    public void handleResult(Result result) {
        onSearchResult(result);
    }

    private void onSearchResult(Result result) {
        int resultcode = Integer.parseInt(result.getText());

        for (PrdItem currentItem : prdItems){
            if (resultcode == currentItem.getBarcode()){
                Intent intent = new Intent(getActivity(), PrdInfoActivity.class);
                intent.putExtra("prd_name",currentItem.getName());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(),"잘못됨",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
