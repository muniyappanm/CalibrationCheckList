package com.dhumuni.calibrationchecklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class ReportItemAdapter extends RecyclerView.Adapter<ReportItemAdapter.ExampleViewHolder> {
    private ArrayList<ReportItem> mReportList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemEdit(int position, TextView Data);
        void onItemDelete(int position, TextView Data);
        void onItemPrint(int position,TextView Data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView Data;
        public ImageView edit;
        public ImageView delete;
        public ImageView print;
        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            Data = itemView.findViewById(R.id.data);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            print = itemView.findViewById(R.id.print);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                    }
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemEdit(position,Data);
                        }
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDelete(position,Data);
                        }
                    }
                }
            });
            print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemPrint(position,Data);
                        }
                    }
                }
            });
        }
    }

    public ReportItemAdapter(ArrayList<ReportItem> exampleList) {
        mReportList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reportitem, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ReportItem currentItem = mReportList.get(position);
        holder.Data.setText(currentItem.getData());
        holder.edit.setImageResource(currentItem.getedit());
        holder.delete.setImageResource(currentItem.getdelete());
        holder.print.setImageResource(currentItem.getprint());
    }

    @Override
    public int getItemCount() {
        return mReportList.size();
    }
}
