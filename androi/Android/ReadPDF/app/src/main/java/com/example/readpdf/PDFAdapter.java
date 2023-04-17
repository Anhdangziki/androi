package com.example.readpdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;


public class PDFAdapter extends  RecyclerView.Adapter<PDFAdapter.PDFViewHoder> {
    public interface IClickItem {
        void clickItem(File file);
    }

    private Context context;
    private List<File> pdfFile;
    private  IClickItem iClickItem;


    public PDFAdapter(Context context, List<File> pdfFile, IClickItem iClickItem) {
        this.context = context;
        this.pdfFile = pdfFile;
        this.iClickItem = iClickItem;
    }

    @NonNull
    @Override
    public PDFViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PDFViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHoder holder, int position) {
        holder.tvName.setText(pdfFile.get(position).getName());
        holder.tvName.setSelected(true);
        holder.iView.setOnClickListener(v -> iClickItem.clickItem(pdfFile.get(position)));
    }

    @Override
    public int getItemCount() {
        return pdfFile.size();
    }

    public class PDFViewHoder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private CardView iView;
        public PDFViewHoder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            iView = itemView.findViewById(R.id.item_view);
        }
    }

}
