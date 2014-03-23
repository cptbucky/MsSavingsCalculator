package com.avantics.savingscalc.common.email;

import android.content.res.Resources;
import android.os.Environment;

import com.avantics.savingscalc.common.ExcelExporter;
import com.avantics.savingscalc.common.entities.Quote;

public class ExcelQuoteSource implements IAttachmentSource {

    private final Quote _quote;
    private final Resources _resources;

    public ExcelQuoteSource(Resources resources, Quote quote){
        this._resources = resources;
        this._quote = quote;
    }

    @Override
    public String GetAttachmentSource() {
        String filePath;

        if (_quote.Name.getValue().equals("")) {
            filePath = String.format("%s/MsSavingsCalculator_Quote.xls", Environment.getExternalStorageDirectory());
        } else {
            filePath = String.format("%s/MsSavingsCalculator_Quote - %s.xls", Environment.getExternalStorageDirectory(), _quote.Name.getValue());
        }

        ExcelExporter xlEngine = new ExcelExporter(filePath);

        xlEngine.CreateQuoteWorkSheet(_quote, _resources);

        return filePath;
    }
}
