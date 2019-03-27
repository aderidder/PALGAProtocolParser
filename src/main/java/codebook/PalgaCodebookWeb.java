/*
 * Copyright 2017 NKI/AvL
 *
 * This file is part of PALGAProtocolParser.
 *
 * PALGAProtocolParser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PALGAProtocolParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PALGAProtocolParser. If not, see <http://www.gnu.org/licenses/>
 */

package codebook;

import data.Protocol;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Codebook for PALGA, tailored to their wishes
 */
class PalgaCodebookWeb extends DefaultPalgaCodebook {
    /**
     * constructor
     * @param protocol             the protocol which should be written to the codebook
     * @param captionOverwriter    caption overwriting and tracking of conflicting captions
     */
    PalgaCodebookWeb(Protocol protocol, CaptionOverwriter captionOverwriter, boolean writeInSeparateSheets){
        super(protocol, captionOverwriter, "PALGAWEB", writeInSeparateSheets);
    }


    /**
     * get a list with the headernames for the main sheet
     * @return list with the headernames for the main sheet
     */
    List<String> getWriteToExcelMainHeader(){
        List<String> mainHeaderNames = new ArrayList<>(Arrays.asList("path","caption","input_type","data_type", "options", "field_validation"));
        return mainHeaderNames;
    }

    /**
     * get a list with the values that will be written to the main sheet
     * @param codebookItem    the codebook item which has the values that are to be written
     * @return list with values
     */
    List<String> getWriteToExcelValuesSingleSheet(CodebookItem codebookItem){
        List<String> argumentsList = new ArrayList<>(Arrays.asList(codebookItem.getPath(), codebookItem.getCaption(), codebookItem.get_name(), codebookItem.getData_type(),  codebookItem.getOptionsString(), codebookItem.getValidationRule()));
        return argumentsList;
    }

    /**
     * get a list with the values to be written when the codebook item has no options
     * @param codebookItem    the codebook item which has the values that are to be written
     * @return list with values
     */
    List<String> getWriteToExcelOptionsValuesNoOptionsRef(CodebookItem codebookItem){
        List<String> argumentsList = new ArrayList<>(Arrays.asList(codebookItem.getPath(), codebookItem.getCaption(), codebookItem.get_name(), codebookItem.getData_type(), "", codebookItem.getValidationRule()));
        return argumentsList;
    }

    /**
     * get a list with the values to be written when the codebook item does have options
     * @param codebookItem    the codebook item which has the values that are to be written
     * @return list with values
     */
    List<String> getWriteToExcelOptionsValuesOptionsRef(CodebookItem codebookItem){
        List<String> argumentsList = new ArrayList<>(Arrays.asList(codebookItem.getPath(), codebookItem.getCaption(), codebookItem.get_name(), codebookItem.getData_type(), codebookItem.getPathAsRef(), codebookItem.getValidationRule()));
        return argumentsList;
    }

}
