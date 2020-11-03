package com.dhumuni.calibrationchecklist;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemYes(int position, TextView Sno, EditText Remarks);

        void onItemNo(int position, TextView Sno, EditText Remarks,ImageView yes, ImageView no, ImageView ok);

        void onItemOk(int position, TextView Sno, EditText Remarks,ImageView yes, ImageView no, ImageView ok);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mSno;
        public TextView mParticulars;
        public EditText mRemarks;
        public ImageView yes;
        public ImageView no;
        public ImageView ok;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mSno = itemView.findViewById(R.id.sno);
            mParticulars = itemView.findViewById(R.id.particulars);
            mRemarks = itemView.findViewById(R.id.remarks);
            yes = itemView.findViewById(R.id.yes);
            no = itemView.findViewById(R.id.no);
            ok = itemView.findViewById(R.id.ok);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                    }
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemYes(position,mSno,mRemarks);
                        }
                    }
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mRemarks.setEnabled(true);
                            listener.onItemNo(position,mSno, mRemarks,yes, no, ok);
                        }
                    }
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mRemarks.setEnabled(true);
                            listener.onItemOk(position,mSno, mRemarks,yes, no, ok);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_exampleitem, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mSno.setText(currentItem.Sno());
        holder.mParticulars.setText(currentItem.Particulars());
        holder.mRemarks.setEnabled(false);
        holder.mRemarks.setText(currentItem.getRemarks());
        holder.mRemarks.setEnabled(false);
        holder.yes.setImageResource(currentItem.getyes());
        holder.no.setImageResource(currentItem.getno());
        holder.ok.setImageResource(currentItem.getok());
        holder.ok.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
