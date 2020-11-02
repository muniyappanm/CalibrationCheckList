package com.dhumuni.calibrationchecklist;
public class ExampleItem {

    private int myes,mno,mok;
    private String mSno,mParticulars,mRemarks;
    private  String[] Particular,Numbers;
    public ExampleItem(String Sno, String Particulars, String Remarks,int yes,int no,int ok) {
        mSno = Sno;
        mParticulars = Particulars;
        mRemarks = Remarks;
        myes=yes;
        mno=no;
        mok=ok;
    }

    public ExampleItem() {
            Particular= new String[]{"WHETHER TT CALIBRATION AT IOC SANKARI TERMINAL IS DUE?",
                    "WHETHER IOC NEW COLOUR CODING PAINTED?",
                    "IS TT MFG ON & AFTER 01.10.2006 IF YES WHETHER ABS BREAK SYSTEM IS PROVIDED? (ANY LOCK BRAKE SYSTEM) YEAR OF MFG",
                    "WHETHER THE TANK /COMPARTMENT DIMENSIONS ARE MATCHING WITH EXPLOSIVE DRAWING?",
                    "WHETHER ISI MARKED 10 KG DCP WITH TT NO. PAINTED ON IT & SERVICE RECORD AVAILABLE?",
                    "WHETHER 2KG CO2 FE WITH TT NO. PAINTED ON IT & SERVICE RECORD AVAILABLE?",
                    "WHETHER VALID POLLUTION CONTROL CERTIFICATE IS AVAILABLE?",
                    "WHETHER TREM CARD IS AVAILABLE?",
                    "WHETHER CCOE APPROVED SPARK ARRESTER PROVIDED WITHOUT LEAK?",
                    "WHETHER LHS & RHS MIRROR AVAILABLE IN THE TT?",
                    "WHETHER MASTER CUT OFF SWITCH IS IN WORKING CONDITION?",
                    "WHETHER RUBBER CAP PROVIDED FOR BATTERY TERMINAL?",
                    "WHETHER ANY OIL LEAK FROM THE ENGINE?",
                    "WHETHER ANY LOOSE ELECTRICAL WIRING OBSERVED?",
                    "WHETHER SEAT BELT IS AVAILABLE?",
                    "WHETHER REVERSE HORN AVAILABLE IN THE TT?",
                    "WHETHER FIRST AID BOX WITH TT NO.PAINTED ON IT IS FIXED IN THE CABIN WITH VALID MATERIAL?",
                    "WHETHER CAM LOCK COUPLING WITH RUBBER HOSE IN GOOD CONDITION & PAINTED TT ON THE HOSE?",
                    "WHETHER EARTH CLEAT WELDED ON THE TANK WITHOUT PAINT FOR EARTH CONNECTION?",
                    "WHETHER TANK BODY WITHOUT ANY DENT?",
                    "WHETHER HAZCHEM LABEL PAINTED AS PER THE REQUIREMENT?",
                    "WHETHER ALL FUSIBLE LINKS IN GOOD CONDITION?",
                    "FUEL TANK CAP IS INTACT?",
                    "WHETHER FUEL TANK CAP IS INTACT & NO LEAK?",
                    "WHETHER MANIFOLD VALVE BOX STAINLESS STEEL AS PER REQUIREMENT?",
                    "WHETHER MANIFOLD VALVE CAP PROVIDED WITH WASHER FOR LEAK FREE?",
                    "WHETHER TT NO. PAINTED ON THE TOP OF THE CABIN?",
                    "WHETHER SINGLE LOCKING SYSTEM ROD AS PER REQUIREMENT?",
                    "WHETHER STAINLESS STEEL MAN HOLE COVERS IN GOOD CONDITION?",
                    "WHETHER COFFEE DAM RING PROVIDED AROUND THE MAN HOLE?",
                    "WHETHER FUSIBLE PLUG IN GOOD CONDITION IN ALL THE COMPARTMENTS?",
                    "WHETHER PV VALVE PROVIDED IN ALL COMPARTMENTS?",
                    "WHETHER FILL PIPE PROVIDED WITH PROPER CAP WITH WASHER?",
                    "WHETHER DIP PIPE PROVIDED WITH PROPER CAP WITH WASHER?",
                    "WHETHER MAN HOLE IS AT THE CENTER OF THE COMPARTMENT?",
                    "WHETHER DIP PIPE IS AT THE CENTER OF THE COMPARTMENT?",
                    "TT MASTER UPDATED IN SAP FOR USING THE TT FOR PLANNING?",
                    "LOAD TAKEN BEFORE STOPPING FOR CALIBRATION-MS+HSD+SKO",
                    "DEALER/TRANSPORTER SHALL BE FULLY RESPONSIBLE FOR DEGASSING OF TT BY OVERFLOWING WATER IN EACH COMPARTMENT BEFORE CARRYING OUT ANY HOT WORK IN THE TT"};

        Numbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
                "35", "36", "37", "38", "39"};


    }
    public String Sno() { return mSno;}
    public String Particulars() {return mParticulars; }
    public String getRemarks() { return mRemarks;}
    public int getyes() {return myes; }
    public int getno() { return mno;}
    public int getok() { return mok;}
    public void changeText(String Remarks) { mRemarks = Remarks; }
    public String[] GetParticular() { return Particular;}
    public String[] GetNumbers(){return Numbers; }
}