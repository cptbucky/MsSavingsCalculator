package com.avantics.savingscalcpremium;

import android.util.Log;

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
	/**
	 * 
	 * @param filePath
	 *            - the name to give the new workbook file
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
	 * 
	 * @param wb
	 *            - WritableWorkbook to create new sheet in
	 * @param sheetName
	 *            - name to be given to new sheet
	 * @param sheetIndex
	 *            - position in sheet tabs at bottom of workbook
	 * @return - a new WritableSheet in given WritableWorkbook
	 */
	public WritableSheet createSheet(WritableWorkbook wb, String sheetName,
			int sheetIndex) {
		// create a new WritableSheet and return it
		return wb.createSheet(sheetName, sheetIndex);
	}

	/**
	 * 
	 * @param columnPosition
	 *            - column to place new cell in
	 * @param rowPosition
	 *            - row to place new cell in
	 * @param contents
	 *            - string value to place in cell
	 * @param headerCell
	 *            - whether to give this cell special formatting
	 * @param sheet
	 *            - WritableSheet to place cell in
	 * @throws RowsExceededException
	 *             - thrown if adding cell exceeds .xls row limit
	 * @throws WriteException
	 *             - Idunno, might be thrown
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
}
