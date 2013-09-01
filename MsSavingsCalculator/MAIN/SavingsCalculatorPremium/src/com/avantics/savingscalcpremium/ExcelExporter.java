package com.avantics.savingscalcpremium;

import android.content.res.Resources;
import android.util.Log;
import com.avantics.savingscalc.common.Quote;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;

public class ExcelExporter {

    private final WritableSheet ws;
    private final WritableWorkbook wb;
    private WritableCellFormat headerFormat;
    private WritableCellFormat savingsPercentageFormat;
    private WritableCellFormat numberFormat;

    public ExcelExporter(String filePath) {
        // give header cells size 10 Arial bolded
        WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12,
                WritableFont.BOLD);
        headerFormat = new WritableCellFormat(headerFont);
        // center align the cells' contents
        try {
            headerFormat.setAlignment(Alignment.CENTRE);
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        WritableFont savingsPercentageFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        savingsPercentageFormat = new WritableCellFormat(savingsPercentageFont);

        try {
            savingsPercentageFormat.setAlignment(Alignment.CENTRE);
            savingsPercentageFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        numberFormat = new WritableCellFormat(new NumberFormat("0.00"));

        wb = createWorkbook(filePath);

        ws = createSheet(wb, "MSSC Quote", 1);
    }

//    public void CreateQuoteWorkSheet(String filePath, Quote quote, Resources resources) {
//        ExcelExporter xlExporter = new ExcelExporter();
//
//        WritableWorkbook wb = xlExporter.createWorkbook(filePath);
//
//        WritableSheet ws = xlExporter.createSheet(wb, "Quote", 1);
//
//        int rowOffset = 0;
//        int currentRowIndex = rowOffset;
//
//        xlExporter.writeCell(2, currentRowIndex, resources.getString(R.string.statement_total_exc_terminal), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.CustomerTotalExcludingTerminal.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(2, currentRowIndex, resources.getString(R.string.customer_teminal), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.CustomerTerminal.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(2, currentRowIndex, resources.getString(R.string.statement_total), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.CustomerStatementTotal.getValue().toString(), ws);
//
//        currentRowIndex += 2;
//        xlExporter.writeCell(1, currentRowIndex, resources.getString(R.string.statement_total), ws);
//        xlExporter.writeCell(2, currentRowIndex, PremiumQuoteFragment.VENDOR_HEADER_LABEL, ws);
//        xlExporter.writeCell(3, currentRowIndex, resources.getString(R.string.calculated_total), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.cc_short), ws);
//        xlExporter.writeCell(1, currentRowIndex, quote.CreditCardStatementTotal.getValue().toString(), ws);
//        xlExporter.writeCell(2, currentRowIndex, quote.CreditCardRate.getValue().toString(), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.CreditCardTotal.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.bc_short), ws);
//        xlExporter.writeCell(1, currentRowIndex, quote.BankCardStatementTotal.getValue().toString(), ws);
//        xlExporter.writeCell(2, currentRowIndex, quote.BankCardRate.getValue().toString(), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.BankCardTotal.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.dc_short), ws);
//        xlExporter.writeCell(1, currentRowIndex, quote.DebitCardStatementTotal.getValue().toString(), ws);
//        xlExporter.writeCell(2, currentRowIndex, quote.DebitCardRate.getValue().toString(), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.DebitCardTotal.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(1, currentRowIndex, PremiumQuoteFragment.VENDOR_INCPCI_LABEL, ws);
//        xlExporter.writeCell(2, currentRowIndex, quote.FIncludingPciRate.getValue().toString(), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.FIncludingPciTotal.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(1, currentRowIndex, PremiumQuoteFragment.VENDOR_TERMINAL_LABEL, ws);
//        xlExporter.writeCell(2, currentRowIndex, quote.VendorTerminal.getValue().toString(), ws);
//        xlExporter.writeCell(3, currentRowIndex, quote.VendorTerminalTotal.getValue().toString(), ws);
//
//        currentRowIndex += 2;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.saving_percentage), ws);
//
//        Double savingsPercent = (quote.SavingsPercentage.getValue() * 100);
//        xlExporter.writeCell(1, currentRowIndex, savingsPercent.toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.savings_month), ws);
//        xlExporter.writeCell(1, currentRowIndex, quote.SavingsOneMonth.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.saving_year), ws);
//        xlExporter.writeCell(1, currentRowIndex, quote.SavingsOneYear.getValue().toString(), ws);
//
//        currentRowIndex++;
//        xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.saving_4years), ws);
//        xlExporter.writeCell(1, currentRowIndex, quote.SavingsFourYears.getValue().toString(), ws);
//
//        xlExporter.setColumnWidth(ws, 1, 25);
//        xlExporter.setColumnWidth(ws, 2, 30);
//
//        try {
//            wb.write();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        try {
//            wb.close();
//        } catch (WriteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public void CreateQuoteWorkSheet_NEW(Quote quote, Resources resources) {
        int rowOffset = 0;

        try {

            /* Incumbent */
            writeHeaderCell(1, 1, resources.getString(R.string.incumbent_cost));

            writeCell(1, 2, resources.getString(R.string.client_statement_total));
            writeValueCell(2, 2, quote.CustomerStatementTotal.getValue().toString());

            writeCell(1, 4, resources.getString(R.string.cc_long));
            writeValueCell(2, 4, quote.CreditCardStatementTotal.getValue().toString());

            writeCell(1, 5, resources.getString(R.string.bc_long));
            writeValueCell(2, 5, quote.BankCardStatementTotal.getValue().toString());

            writeCell(1, 6, resources.getString(R.string.dc_long));
            writeValueCell(2, 6, quote.DebitCardStatementTotal.getValue().toString());
            /* Incumbent */

            /* Proposed */
            writeHeaderCell(4, 1, resources.getString(R.string.proposed_rate));

            writeCell(4, 2, resources.getString(R.string.cc_rate_long));
            writeValueCell(5, 2, quote.CreditCardRate.getValue().toString());

            writeCell(4, 3, resources.getString(R.string.bc_rate_long));
            writeValueCell(5, 3, quote.BankCardRate.getValue().toString());

            writeCell(4, 4, resources.getString(R.string.dc_rate_long));
            writeValueCell(5, 4, quote.DebitCardRate.getValue().toString());

            writeCell(4, 5, resources.getString(R.string.vendor_terminal));
            writeValueCell(5, 5, quote.VendorTerminal.getValue().toString());

            writeCell(4, 6, resources.getString(R.string.vendor_inc_pci));
            writeValueCell(5, 6, quote.FIncludingPciRate.getValue().toString());
            /* Proposed */

            /* Summary */
            writeHeaderCell(7, 1, resources.getString(R.string.summary_section_header));

            writeCell(8, 2, resources.getString(R.string.client_statement_total));
            writeCell(9, 2, resources.getString(R.string.vendor_rate));
            writeCell(10, 2, resources.getString(R.string.vendor_statement_total));

            writeCell(7, 3, resources.getString(R.string.cc_short));
            writeValueCell(8, 3, quote.CreditCardStatementTotal.getValue().toString());
            writeValueCell(9, 3, quote.CreditCardRate.getValue().toString());
            writeValueCell(10, 3, quote.CreditCardTotal.getValue().toString());

            writeCell(7, 4, resources.getString(R.string.bc_short));
            writeValueCell(8, 4, quote.BankCardStatementTotal.getValue().toString());
            writeValueCell(9, 4, quote.BankCardRate.getValue().toString());
            writeValueCell(10, 4, quote.BankCardTotal.getValue().toString());

            writeCell(7, 5, resources.getString(R.string.dc_short));
            writeValueCell(8, 5, quote.DebitCardStatementTotal.getValue().toString());
            writeValueCell(9, 5, quote.DebitCardRate.getValue().toString());
            writeValueCell(10, 5, quote.DebitCardTotal.getValue().toString());

            writeCell(8, 6, resources.getString(R.string.vendor_terminal));
            writeValueCell(9, 6, quote.VendorTerminal.getValue().toString());
            writeValueCell(10, 6, quote.VendorTerminalTotal.getValue().toString());

            writeCell(8, 7, resources.getString(R.string.vendor_inc_pci));
            writeValueCell(9, 7, quote.FIncludingPciRate.getValue().toString());
            writeValueCell(10, 7, quote.FIncludingPciTotal.getValue().toString());

            writeHeaderCell(7, 9, resources.getString(R.string.savings_section_header));
            writeCell(10, 9, "");

            writeCell(7, 10, resources.getString(R.string.savings_month));
            writeValueCell(9, 10, quote.SavingsOneMonth.getValue().toString());
            writeCell(10, 10, quote.SavingsPercentage.getValue().toString(), savingsPercentageFormat);

            writeCell(7, 11, resources.getString(R.string.saving_year));
            writeValueCell(9, 11, quote.SavingsOneYear.getValue().toString());

            writeCell(7, 12, resources.getString(R.string.saving_4years));
            writeValueCell(9, 12, quote.SavingsFourYears.getValue().toString());

            /* Summary */

            setColumnWidth(1, 20);
            setColumnWidth(4, 20);
            setColumnWidth(7, 5);
            setColumnWidth(8, 15);
            setColumnWidth(9, 15);
            setColumnWidth(10, 15);

            ws.mergeCells(1, 1, 2, 1);
            ws.mergeCells(4, 1, 5, 1);
            ws.mergeCells(7, 1, 10, 1);

            ws.mergeCells(7, 9, 10, 9);
            ws.mergeCells(7, 10, 8, 10);
            ws.mergeCells(7, 11, 8, 11);
            ws.mergeCells(7, 12, 8, 12);
            ws.mergeCells(10, 10, 10, 12);
        } catch (RowsExceededException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            wb.write();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            wb.close();
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeCell(int i, int i1, String string) {
        try {
            writeCell(i, i1, string, null);
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void writeHeaderCell(int i, int i1, String string) {
        try {
            writeCell(i, i1, string, headerFormat);
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void writeValueCell(int i, int i1, String string) {
        try {
            writeCell(i, i1, string, numberFormat);
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * @param filePath - the name to give the new workbook file
     * @return - a new WritableWorkbook with the given fileName
     */
    public WritableWorkbook createWorkbook(String filePath) {
        // exports must use a temp file while writing to avoid memory hogging
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setUseTemporaryFileDuringWrite(true);

        // get the sdcard's directory
//		File sdCard = Environment.getExternalStorageDirectory();
        // add on the your app's path
//		File dir = new File(dirPath);
        // make them in case they're not there
//		dir.mkdirs();
        // create a standard java.io.File object for the Workbook to use
        File wbfile = new File(filePath);

        WritableWorkbook wb = null;

        try {
            // create a new WritableWorkbook using the java.io.File and
            // WorkbookSettings from above
            wb = Workbook.createWorkbook(wbfile, wbSettings);
        } catch (IOException ex) {
            Log.e("test", ex.getStackTrace().toString());
            Log.e("test", ex.getMessage());
        }

        return wb;
    }

    /**
     * @param wb         - WritableWorkbook to create new sheet in
     * @param sheetName  - name to be given to new sheet
     * @param sheetIndex - position in sheet tabs at bottom of workbook
     * @return - a new WritableSheet in given WritableWorkbook
     */
    public WritableSheet createSheet(WritableWorkbook wb, String sheetName,
                                     int sheetIndex) {
        // create a new WritableSheet and return it
        return wb.createSheet(sheetName, sheetIndex);
    }

    /**
     * @param columnPosition - column to place new cell in
     * @param rowPosition    - row to place new cell in
     * @param contents       - string value to place in cell
     * @param CellFormat     - whether to give this cell special formatting
     * @throws RowsExceededException - thrown if adding cell exceeds .xls row limit
     * @throws WriteException        - Idunno, might be thrown
     */
    public void writeCell(int columnPosition, int rowPosition, String contents, WritableCellFormat CellFormat)
            throws RowsExceededException, WriteException {
        // create a new cell with contents at position
        Label newCell = new Label(columnPosition, rowPosition, contents);

        if (CellFormat != null) {
            newCell.setCellFormat(CellFormat);
        }

        ws.addCell(newCell);
    }

    public void setColumnWidth(int columnPosition, int columnWidth) {
        ws.setColumnView(columnPosition, columnWidth);
    }
}
