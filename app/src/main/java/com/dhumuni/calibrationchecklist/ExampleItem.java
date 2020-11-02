package com.dhumuni.calibrationchecklist;
public class ExampleItem {
    private int myes,mno,mok;
    private String mSno,mParticulars,mRemarks;

    public ExampleItem(String Sno, String Particulars, String Remarks,int yes,int no,int ok) {
        mSno = Sno;
        mParticulars = Particulars;
        mRemarks = Remarks;
        myes=yes;
        mno=no;
        mok=ok;
    }


    public String Sno() { return mSno;}

    public String Particulars() {return mParticulars; }

    public String getRemarks() { return mRemarks;}

    public int getyes() {return myes; }

    public int getno() { return mno;}

    public int getok() { return mok;}

    public void changeText(String Remarks) {
        mRemarks = Remarks;
    }
}