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

package gui;

import data.Protocol;

import java.util.List;

/**
 * class that stores everything required for a run
 */
class RunParameters {
    private String codebookType;

    private String outputDir="";
    private String workspaceFileName="";
    private String overwriteFileName="";
    private String protocolFileName="";

    private boolean storeOptionsInSeparateSheets=false;

    private Protocol protocol = new Protocol();

    RunParameters(){

    }

    /**
     * return the protocol name
     * @return the protocol name
     */
    String getProtocolName() {
        return protocol.getProtocolName();
    }

    /**
     * sets the protocol name
     * @param protocolName    the name of the protocol
     */
    void setProtocolName(String protocolName) {
        protocol.setProtocolName(protocolName);
    }

    /**
     * sets the project name
     * @param projectName    the name of the project
     */
    void setProjectName(String projectName){
        protocol.setProjectName(projectName);
    }

    /**
     * get the nets that have been selected in the wizard
     * @return a list with the selected nets
     */
    List<String> getSelectedNets() {
        return protocol.getSelectedNets();
    }

    /**
     * set the selected nets to what has been selected in the wizard
     * @param selectedNets    list of the selected nets
     */
    void setSelectedNets(List<String> selectedNets) {
        protocol.setSelectedNets(selectedNets);
    }

    /**
     * get the complete filename of the database (the name seems to be workspace.db, hence the name of the function)
     * @return the filename
     */
    String getWorkspaceFileName() {
        return workspaceFileName;
    }

    /**
     * set the complete filename of the database (the name seems to be workspace.db, hence the name of the function)
     * @param workspaceFileName name of the workspace file
     */
    void setWorkspaceFileName(String workspaceFileName) {
        this.workspaceFileName = workspaceFileName;
    }

    /**
     * get the name of the overwrites file, which is used to overwrite concept captions
     * @return full name of the file
     */
    String getOverwriteFileName() {
        return overwriteFileName;
    }

    /**
     * set the name of the overwrites file, which is used to overwrite concept captions
     * @param overwriteFileName    full name of the file
     */
    void setOverwriteFileName(String overwriteFileName) {
        this.overwriteFileName = overwriteFileName;
    }

    /**
     * get the output directory
     * @return output directory
     */
    String getOutputDir() {
        return outputDir;
    }

    /**
     * set the output directory
     * @param outputDir    the output directory
     */
    void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * get the type of codebook which was selected in the wizard. determines columns which will appear in the output
     * @return the selected type of codebook
     */
    String getCodebookType() {
        return codebookType;
    }

    /**
     * set the type of codebook selected in the wizard
     * @param codebookType    the selected type of codebook
     */
    void setCodebookType(String codebookType) {
        this.codebookType = codebookType;
    }

    /**
     * get the full name of the protocol file, which contains a protocolName and projectName on every line
     * @return the full name of the protocol file
     */
    String getProtocolFileName() {
        return protocolFileName;
    }

    /**
     * set the protocol filename, which was selected in the wizard
     * @param protocolFileName    the name of the protocolfile
     */
    void setProtocolFileName(String protocolFileName) {
        this.protocolFileName = protocolFileName;
    }

    /**
     * loads extra information about the protocol, such as the version
     */
    void loadProtocolInfo(){
        protocol.loadProtocolInfo();
    }

    /**
     * returns the version of the protocol
     * @return version of the protocol
     */
    String getProtocolVersion(){
        return protocol.getVersion();
    }

    /**
     * set whether the concept options (e.g. male, female) should be written to separate sheets (e.g. gender)
     * @param value    true/false
     */
    void setStoreOptionsInSeparateSheets(Boolean value){
        storeOptionsInSeparateSheets = value;
    }

    /**
     * return whether the concept options (e.g. male, female) should be written to separate sheets (e.g. gender)
     * @return true/false
     */
    boolean getStoreOptionsInSeparateSheets(){
        return storeOptionsInSeparateSheets;
    }

    /**
     * returns the protocol object, which contains the data etc.
     * @return the protocol object
     */
    public Protocol getProtocol() {
        return protocol;
    }
}
