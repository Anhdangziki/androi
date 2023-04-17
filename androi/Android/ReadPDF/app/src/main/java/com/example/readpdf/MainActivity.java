package com.example.readpdf;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1;
    private PDFAdapter adapter;
    private List<File> pdfList;
    private RecyclerView rcvView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requsetPermission();
        initUI();
    }

    private void requsetPermission() {
        try {
            // if version android < 6 -> don't request
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            // if version android > 6
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            }
        }catch (Exception ex) {
            Log.e("err: ", ex.getMessage());
        }
    }

    public ArrayList<File> findPDF(File file) {
        ArrayList<File> list = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                list.addAll(findPDF(singleFile));
            } else if (singleFile.getName().endsWith(".pdf")) {
                list.add(singleFile);
            }
        }
        return list;
    }
    private void initUI() {
        try {
            rcvView = findViewById(R.id.rcv_view);
            rcvView.setHasFixedSize(true);
            rcvView.setLayoutManager(new GridLayoutManager(this, 3));
            pdfList = new ArrayList<>();
            pdfList.addAll(findPDF(Environment.getExternalStorageDirectory()));
            adapter = new PDFAdapter(this, pdfList, file -> onClickItem(file));
            rcvView.setAdapter(adapter);
        }catch (Exception ex) {
            Log.e("err: ", ex.getMessage());
        }

    }

    private void onClickItem(File file) {
        startActivity(new Intent(this, DocumentActivity.class).putExtra("pdf_file", file.getAbsolutePath()));
    }
}