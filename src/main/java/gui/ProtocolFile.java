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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * class for the protocol file, which contains a protocolName and a projectName on each line
 */
class ProtocolFile {
    private static final Logger logger = LogManager.getLogger(ProtocolFile.class.getName());
    private static ProtocolFile protocolFile=new ProtocolFile();
    private String fileName="";

    private List<String> protocols;
    private List<String> projects;

    /**
     * reads the provided file
     * @param fileName    the fileName to read
     */
    private void readFile(String fileName){
        String line;
        protocols = new ArrayList<>();
        projects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(fileName)), "ISO-8859-1"))) {
            while ((line = br.readLine()) != null) {
                String [] splitLine = line.split("\t");
                protocols.add(splitLine[0]);
                projects.add(splitLine[1]);
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, "Problem reading the Protocol File: "+e.getMessage());
        }
    }

    /**
     * returns a list of the protocols found in the file
     * @param fileName    fileName to read
     * @return list of protocols
     */
    static List<String> getProtocols(String fileName){
        // if the current protocolFile is equal to the one we need to read
        // don't read an simply return the protocols
        if(protocolFile.fileName.equalsIgnoreCase(fileName)){
            return protocolFile.protocols;
        }
        // otherwise parse the file
        else{
            protocolFile = new ProtocolFile();
            protocolFile.readFile(fileName);
            return protocolFile.protocols;
        }
    }

    /**
     * return the projectName that belongs to this protocol
     * @param protocol    the protocol name
     * @return the project name
     */
    static String getProject(String protocol){
        int index = protocolFile.protocols.indexOf(protocol);
        return protocolFile.projects.get(index);
    }
}
