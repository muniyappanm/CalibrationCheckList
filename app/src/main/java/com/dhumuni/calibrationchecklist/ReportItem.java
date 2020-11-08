package com.dhumuni.calibrationchecklist;

public class ReportItem {
    private int medit,mdelte,mprint;
    private String mData;
    public ReportItem(String Data,int edit,int delete,int print) {
        mData = Data;
        medit=edit;
        mdelte=delete;
        mprint=print;
    }
    public String getData() { return mData;}
    public int getedit() {return medit; }
    public int getdelete() { return mdelte;}
    public int getprint() {return mprint;}
}
