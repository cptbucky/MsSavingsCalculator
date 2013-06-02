package com.avantics.savingscalcpremium;

import android.content.res.Resources;
import android.util.Log;

import com.avantics.savingscalc.common.UiBindingManager;
import com.avantics.savingscalcpremium.fragments.PremiumQuoteFragment;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelExporter {
    public static void CreateWorksheetFromBinder(String filePath, UiBindingManager binder, Resources resources) {
        ExcelExporter xlExporter = new ExcelExporter();

        WritableWorkbook wb = xlExporter.createWorkbook(filePath);

        WritableSheet ws = xlExporter.createSheet(wb, "Quote", 1);

        int rowOffset = 0;
        int currentRowIndex = rowOffset;

        try {
            xlExporter.writeCell(2, currentRowIndex, resources.getString(R.string.statement_total_exc_terminal), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.stet.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(2, currentRowIndex, resources.getString(R.string.customer_teminal), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.csterminal.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(2, currentRowIndex, resources.getString(R.string.statement_total), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.cstotal.getValue().toString(), false, ws);

            currentRowIndex += 2;
            xlExporter.writeCell(1, currentRowIndex, resources.getString(R.string.statement_total), false, ws);
            xlExporter.writeCell(2, currentRowIndex, PremiumQuoteFragment.VENDOR_RATE_LABEL, false, ws);
            xlExporter.writeCell(3, currentRowIndex, resources.getString(R.string.calculated_total), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.cc_short), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.ccst.getValue().toString(), false, ws);
            xlExporter.writeCell(2, currentRowIndex, binder.ccfr.getValue().toString(), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.cct.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.bc_short), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.bcst.getValue().toString(), false, ws);
            xlExporter.writeCell(2, currentRowIndex, binder.bcfr.getValue().toString(), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.bct.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.dc_short), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.dcst.getValue().toString(), false, ws);
            xlExporter.writeCell(2, currentRowIndex, binder.dcfr.getValue().toString(), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.dct.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(1, currentRowIndex, PremiumQuoteFragment.VENDOR_INCPCI_LABEL, false, ws);
            xlExporter.writeCell(2, currentRowIndex, binder.fincpcirate.getValue().toString(), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.fincpci.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(1, currentRowIndex, PremiumQuoteFragment.VENDOR_TERMINAL_LABEL, false, ws);
            xlExporter.writeCell(2, currentRowIndex, binder.vendorterminal.getValue().toString(), false, ws);
            xlExporter.writeCell(3, currentRowIndex, binder.vendorterminaltotal.getValue().toString(), false, ws);

            currentRowIndex += 2;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.saving_percentage), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.savingsPercentage.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.savings_month), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.savings1month.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.saving_year), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.savings1year.getValue().toString(), false, ws);

            currentRowIndex++;
            xlExporter.writeCell(0, currentRowIndex, resources.getString(R.string.saving_4years), false, ws);
            xlExporter.writeCell(1, currentRowIndex, binder.savings4years.getValue().toString(), false, ws);

            xlExporter.setColumnWidth(ws, 1, 25);
            xlExporter.setColumnWidth(ws, 2, 30);
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
     * @param headerCell     - whether to give this cell special formatting
     * @param sheet          - WritableSheet to place cell in
     * @throws RowsExceededException - thrown if adding cell exceeds .xls row limit
     * @throws WriteException        - Idunno, might be thrown
     */
    public void writeCell(int columnPosition, int rowPosition, String contents,
                          boolean headerCell, WritableSheet sheet)
            throws RowsExceededException, WriteException {
        // create a new cell with contents at position
        Label newCell = new Label(columnPosition, rowPosition, contents);

        if (headerCell) {
            // give header cells size 10 Arial bolded
            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            // center align the cells' contents
            headerFormat.setAlignment(Alignment.CENTRE);
            newCell.setCellFormat(headerFormat);
        }

        sheet.addCell(newCell);
    }

    public void setColumnWidth(WritableSheet sheet, int columnPosition, int columnWidth) {
        sheet.setColumnView(columnPosition, columnWidth);
    }
}
