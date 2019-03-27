package codebook;

import data.Protocol;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import utils.ExcelUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 3/21/2019.
 */
abstract class DefaultPalgaCodebook extends DefaultCodebook {
    /**
     * constructor
     * @param protocol             the protocol which should be written to the codebook
     * @param captionOverwriter    caption overwriting and tracking of conflicting captions
     */
    DefaultPalgaCodebook (Protocol protocol, CaptionOverwriter captionOverwriter, String codebookType, boolean writeInSeparateSheets){
        super(protocol, captionOverwriter, codebookType, writeInSeparateSheets);
    }

    /**
     * write codebook to Excel, everything in a single worksheet
     * @param outputDir    where the file should be written
     */
    @Override
    public void writeToExcelSingleSheet(String outputDir){
//        List<String> mainHeaderNames = getWriteToExcelMainHeader();

        Workbook workbook = ExcelUtils.createXLSXWorkbook();
//        CellStyle headerStyle = ExcelUtils.createHeaderStyle(workbook);
//        Sheet mainsheet = ExcelUtils.createSheetWithHeader(workbook, "CODEBOOK", mainHeaderNames, headerStyle);
        Sheet mainsheet = addMainWorksheet(workbook, getWriteToExcelMainHeader());

        for(List<CodebookItem> codebookItems:codebookItemMap.values()){
            for(CodebookItem codebookItem:codebookItems){
                ExcelUtils.writeValues(mainsheet, getWriteToExcelValuesSingleSheet(codebookItem));
            }
        }
        ExcelUtils.writeXLSXWorkBook(workbook, getCodebookOutputName(outputDir));
    }

    abstract List<String> getWriteToExcelMainHeader();

    /**
     * get a list with the values that will be written to the main sheet
     * @param codebookItem    the codebook item which has the values that are to be written
     * @return list with values
     */

    abstract List<String> getWriteToExcelValuesSingleSheet(CodebookItem codebookItem);

    /**
     * write codebook to Excel, options for concepts in separate sheets
     * @param outputDir    where the file should be written
     */
    @Override
    public void writeToExcelOptionsInSheets(String outputDir){
        List<String> sheetHeaderList = Arrays.asList("PALGA_VALUE", "PALGA_DESCRIPTION", "CODESYSTEM");

        Workbook workbook = ExcelUtils.createXLSXWorkbook();
        Sheet mainsheet = addMainWorksheet(workbook, getWriteToExcelMainHeader());

        for(List<CodebookItem> codebookItems:codebookItemMap.values()){
            for(CodebookItem codebookItem:codebookItems){
                if(codebookItem.hasOptions()) {
                    ExcelUtils.writeValues(mainsheet, getWriteToExcelOptionsValuesOptionsRef(codebookItem));
                    addOptionsWorksheet(workbook, codebookItem.getPathAsRef(), sheetHeaderList, codebookItem.getOptions());
                }
                else {
                    ExcelUtils.writeValues(mainsheet, getWriteToExcelOptionsValuesNoOptionsRef(codebookItem));
                }
            }
        }
        ExcelUtils.writeXLSXWorkBook(workbook, getCodebookOutputName(outputDir));
    }


    /**
     * get a list with the values to be written when the codebook item has no options
     * @param codebookItem    the codebook item which has the values that are to be written
     * @return list with values
     */
    abstract List<String> getWriteToExcelOptionsValuesNoOptionsRef(CodebookItem codebookItem);

    /**
     * get a list with the values to be written when the codebook item does have options
     * @param codebookItem    the codebook item which has the values that are to be written
     * @return list with values
     */
    abstract List<String> getWriteToExcelOptionsValuesOptionsRef(CodebookItem codebookItem);


    /**
     * checks whether two codebookitems can be merged into a single codebookitem
     * @param codebookItem1        first codebook item
     * @param codebookItem2        second codebook item
     * @param captionOverwriter    caption overwriting and tracking of conflicting captions
     * @return true/false
     */
    @Override
    boolean mayMergeForCodebook(CodebookItem codebookItem1, CodebookItem codebookItem2, CaptionOverwriter captionOverwriter) {
        boolean canMerge=true;

        if(!codebookItem1.getData_type().equalsIgnoreCase(codebookItem2.getData_type())){
            canMerge = false;
        }
        else if(!codebookItem1.getValidationRule().equalsIgnoreCase(codebookItem2.getValidationRule())){
            canMerge = false;
        }
        else if(!codebookItem1.getPartialRulesString().equalsIgnoreCase(codebookItem2.getPartialRulesString())){
            canMerge = false;
        }

        return canMerge;
    }

}
