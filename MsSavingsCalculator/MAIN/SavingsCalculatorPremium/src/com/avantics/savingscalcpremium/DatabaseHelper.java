package com.avantics.savingscalcpremium;

//import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import com.avantics.savingscalc.common.Quote;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbName = "savingsCalculatorDb";

    static final String tableQuotes = "Quotes";
    static final String colID = "QuoteId";
    static final String colName = "Name";
    static final String colCTET = "CustTotalExcTerminal";
    static final String colCT = "CustTerminal";
    static final String colCCST = "CCStatementTotal";
    static final String colCCR = "CCRate";
    static final String colBCST = "BCStatementTotal";
    static final String colBCR = "BCRate";
    static final String colDCST = "DCStatementTotal";
    static final String colDCR = "DCRate";
    static final String colFincPCIRate = "FincPCIRate";
    static final String colVendorTerminal = "VendorTerminal";

    String[] columns = new String[]{colID, colName, colCTET, colCT,
            colCCST, colCCR, colBCST, colBCR, colDCST, colDCR, colFincPCIRate,
            colVendorTerminal};

    SQLiteDatabase db;

    public DatabaseHelper(Context context, int version) {
        super(context, dbName, null, version);

        db = getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        String createStatement = String
                .format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT UNIQUE, %s DOUB, %s DOUB, %s DOUB, %s DOUB, %s DOUB, %s DOUB, %s DOUB, %s DOUB, %s DOUB, %s DOUB)",
                        tableQuotes, colID, colName, colCTET, colCT, colCCST,
                        colCCR, colBCST, colBCR, colDCST, colDCR, colFincPCIRate,
                        colVendorTerminal);

        db.execSQL(createStatement);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableQuotes);

        onCreate(db);
    }

    public void addQuote(Quote quote) {
        ContentValues cv = new ContentValues();
        cv.put(colName, quote.Name);
        cv.put(colCTET, quote.cstet);
        cv.put(colCT, quote.csterminal);
        cv.put(colCCST, quote.ccst);
        cv.put(colCCR, quote.ccfr);
        cv.put(colBCST, quote.bcst);
        cv.put(colBCR, quote.bcfr);
        cv.put(colDCST, quote.dcst);
        cv.put(colDCR, quote.dcfr);
        cv.put(colFincPCIRate, quote.fincpcirate);
        cv.put(colVendorTerminal, quote.vendorterminal);
        db.insert(tableQuotes, null, cv);
    }

    public SparseArray<String> getAvailableQuoteNames() {
        String[] columns = new String[]{colID, colName};
        Cursor c = db.query(tableQuotes, columns, null, null, null, null, null);

        SparseArray<String> quotes = new SparseArray<String>();

        while (c.moveToNext()) {
            quotes.put(c.getInt(0), c.getString(1));
        }

        return quotes;
    }

    public boolean quoteExists(String quoteName) {
        Cursor c = db.query(tableQuotes, columns, colName + " == ?",
                new String[]{quoteName}, null, null, null);

        return c.getCount() > 0;
    }

    public Quote getQuote(String quoteName) {
        Cursor c = db.query(tableQuotes, columns, colName + " == ?",
                new String[]{String.valueOf(quoteName)}, null, null, null);

        Quote quote = new Quote();

        while (c.moveToNext()) {
            quote.Id = c.getInt(0);
            quote.Name = c.getString(1);
            quote.cstet = c.getDouble(2);
            quote.csterminal = c.getDouble(3);
            quote.ccst = c.getDouble(4);
            quote.ccfr = c.getDouble(5);
            quote.bcst = c.getDouble(6);
            quote.bcfr = c.getDouble(7);
            quote.dcst = c.getDouble(8);
            quote.dcfr = c.getDouble(9);
            quote.fincpcirate = c.getDouble(10);
            quote.vendorterminal = c.getDouble(11);
        }

        return quote;
    }

//	public Quote[] getQuotes() {
//		Cursor c = db.query(tableQuotes, columns, null, null, null, null, null);
//
//		Quote[] quotes = new Quote[c.getCount()];
//		int i = 0;
//		while (c.moveToNext()) {
//			Quote quote = new Quote();
//
//			quote.Id = c.getInt(0);
//			quote.Name = c.getString(1);
//			quote.cstet = c.getDouble(2);
//			quote.csterminal = c.getDouble(3);
//			quote.ccst = c.getDouble(4);
//			quote.ccfr = c.getDouble(5);
//			quote.bcst = c.getDouble(6);
//			quote.bcfr = c.getDouble(7);
//			quote.dcst = c.getDouble(8);
//			quote.dcfr = c.getDouble(9);
//			quote.vendorterminal = c.getDouble(10);
//
//			quotes[i] = quote;
//
//			i++;
//		}
//
//		return quotes;
//	}

    public void updateQuote(Quote quote) {
        ContentValues cv = new ContentValues();
        cv.put(colName, quote.Name);
        cv.put(colCTET, quote.cstet);
        cv.put(colCT, quote.csterminal);
        cv.put(colCCST, quote.ccst);
        cv.put(colCCR, quote.ccfr);
        cv.put(colBCST, quote.bcst);
        cv.put(colBCR, quote.bcfr);
        cv.put(colDCST, quote.dcst);
        cv.put(colDCR, quote.dcfr);
        cv.put(colFincPCIRate, quote.fincpcirate);
        cv.put(colVendorTerminal, quote.vendorterminal);
        db.update(tableQuotes, cv, colName + " == ?", new String[]{String.valueOf(quote.Name)});
    }

    public void deleteQuote(String title) {
        this.db.delete(tableQuotes, String.format("%s='%s'", colName, title), null);
    }
}
