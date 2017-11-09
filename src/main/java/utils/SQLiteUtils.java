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

package utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL helper functions
*/
public class SQLiteUtils {
    private static final Logger logger = LogManager.getLogger(SQLiteUtils.class.getName());
    private static Connection conn=null;
    private static String database="";

    /**
     * set the database
     * @param database    database location
     */
    public static void setDatabase(String database){
        SQLiteUtils.database = database;
    }

    /**
     * open the database
     */
    public static void openDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    /**
     * close the database
     */
    public static void closeDB(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.ERROR, ex.getMessage());
        }
    }

    /**
     * perform the project query, which attempts to retrieve the data column for a project
     * @return data string
     */
    public static String doProjectQuery(){
//        String sql = "select data from project where data like '%version%' and data not like '%obsolete%' and project = '"+projectName+"'";
        String sql = "select value from standalone where key=\"settings\"";
        try (Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
//            return rs.getString("data");
            return rs.getString("value");
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return "";
    }

    /**
     * try this if we switch to version without protocol-list file
     * @return the list which has most entries, which we guess will have the proper prefix (e.g. colonbiopt_)
     */
    public static List<String> getLogicNetNames(){
        Map<String, List<String>> prefixNetNames = new HashMap<>();

        String sql="select name from logicnet";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                String fullName = rs.getString("name");
                String prefix = fullName.substring(0, fullName.indexOf("_"));
                if(!prefixNetNames.containsKey(prefix)){
                    prefixNetNames.put(prefix, new ArrayList<>());
                }
                List<String> netList = prefixNetNames.get(prefix);
                netList.add(fullName);
            }

            return getMaxList(prefixNetNames);

        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return new ArrayList<>();
    }

    private static List<String> getMaxList(Map<String, List<String>> prefixNetNames){
        int maxSize=0;
        String prefix="";
        for(Map.Entry<String, List<String>> entry:prefixNetNames.entrySet()){
            int size = entry.getValue().size();
            if(size>maxSize){
                prefix = entry.getKey();
                maxSize = size;
            }
        }
        return prefixNetNames.get(prefix);
    }

    /**
     * perform query on the logicnet table and retrieve the names of the nets, based on the netprefix
     * @param netPrefix the prefix of the nets
     * @return list of loggicnet names
     */
    public static List<String> getLogicNetNames(String netPrefix){
        List<String> netList = new ArrayList<>();
        String sql="select name from logicnet where name like '"+netPrefix+"_%' and name not like '%_discontinued%'";
        try (Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                netList.add(rs.getString("name"));
            }
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return netList;
    }

    /**
     * perform query on the logicnet table and retrieve the data of the nets, based on a string of nets
     * @param netNames    a string which contains the names of the nets in propber sql format
     * @return list with the data for the nets
     */
    public static List<String> getLogicNetData(String netNames){
        List<String> netData = new ArrayList<>();
        String sql="select data from logicnet where name in ("+netNames+")";
        try (Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                netData.add(rs.getString("data"));
            }
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return netData;
    }

    // this one (or something like it) we'll probably need to find out some defaults
    // e.g. in the colonbiopt_workflow net there is: id = 210, value = "$(protocol_settings.gescheiden_default)"
    // its value seems to reside in the query below
    public static void doParameterQuery(){
        String sql="select distinct vfo1.caption, p.*\n" +
                "from vfile vfi, parameter p,  vfolder vfo1, vfolder vfo2, vfolder vfo3\n" +
                "where vfo1.caption like \"%colonbiopt%\" and vfo2.caption =\"resources\" and vfo1.id = vfo2.parent and vfo3.caption =\"parameters\" and vfo2.id = vfo3.parent\n" +
                "      and vfi.folder = vfo3.id and vfi.item=p.id\n" +
                "order by vfo1.caption";
        try (Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("name") + "\t" +
                        rs.getString("data"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

}
