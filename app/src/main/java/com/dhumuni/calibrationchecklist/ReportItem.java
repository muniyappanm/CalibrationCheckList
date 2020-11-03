package com.dhumuni.calibrationchecklist;

public class ReportItem {
    private int medit,mdelte;
    private String mData;
    public ReportItem(String Data,int edit,int delete) {
        mData = Data;
        medit=edit;
        mdelte=delete;
    }

    public String getData() { return mData;}
    public int getedit() {return medit; }
    public int getdelete() { return mdelte;}
}
