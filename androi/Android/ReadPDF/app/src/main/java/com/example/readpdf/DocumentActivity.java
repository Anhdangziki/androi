package com.example.readpdf;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class DocumentActivity extends AppCompatActivity {

    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        initUI();
        getData();
    }

    private void getData() {
        try {
            String strFile = "";
            strFile = getIntent().getStringExtra("pdf_file");
            File file = new File(strFile);
            Uri path = Uri.fromFile(file);
            pdfView.fromUri(path).load();
        }catch (Exception ex) {
            Log.e("err: ", ex.getMessage());
        }
    }

    private void initUI() {
        pdfView = findViewById(R.id.pdf_view);
    }
}