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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parsing helper functions
 */
public class ParseUtils {
    /**
     * returns first pattern match found in data or empty String if no match was found
     * @param data       the data string
     * @param pattern    the pattern to apply
     * @return first match or empty string
     */
    public static String getValue(String data, Pattern pattern){
        Matcher matcher = pattern.matcher(data);
        if(matcher.matches()){
            return matcher.group(1);
        }
        return "";
    }

    /**
     * returns the part of the data that begins with the element and ends when the indentation
     * reaches the same level again. This is used to select a substring of the data, e.g. to select
     * the "element {data, data, data, data}" from "before, element {data, data, data, data}, after"
     * @param data       the data string
     * @param element    the element to look for
     * @return the string which starts with the element and stops when the same level of indentation is reached
     */
    public static String getElementData(String data, String element){
        // class to keep track of the "{" and "}" tokens to determine when to stop reading
        class TokenTracker {
            private int level=0;

            boolean stopReading(String line){
                if(line.contains("{")) level++;
                else if(line.contains("}")) level--;
                return level == 0;
            }
        }

        TokenTracker token = new TokenTracker();
        String output="";
        if(data.contains(element)) {
            // take the data found after the element and split by new line
            String[] splitData = data.substring(data.indexOf(element)).split("\n");
            for (String line : splitData) {
                output += line + "\n";
                // keep track of indentation level and see whether we should stop reading
                if (token.stopReading(line)) {
                    break;
                }
            }
        }
        return output;
    }

    /**
     * the "parts" entry of the data can consist of multiple entries. we need all of them.
     * this function takes the data and returns all the entries as separate list entries
     * @param data    the data
     * @return list with the parts entries
     */
    public static List<String> partsSplitter(String data){

        // keep track of the level and decides whether we may add the line or whether
        // the line is a new element
        class LevelSplit{
            private int level;
            private int curLevel=0;

            LevelSplit(int level){
                this.level = level;
            }

            /**
             * if line contains a {, level up
             * @param line    the line to check
             */
            void checkLevelUp(String line){
                if(line.contains("{")){
                    curLevel++;
                }
            }

            /**
             * if line contains a }, level down
             * @param line    the line to check
             */
            void checkLevelDown(String line){
                if(line.contains("}")){
                    curLevel--;
                }
            }

            /**
             * checks whether the line will lead to a new list element
             * @param line    the line to check
             * @return true/false
             */
            boolean newElement(String line){
                return curLevel == level && line.contains("}");
            }

            /**
             * checks whether the line may be added to the current element
             * @return true/false
             */
            boolean mayAddLine(){
                return curLevel >= level;
            }
        }

        // create a new level split at level 2
        LevelSplit levelSplit = new LevelSplit(2);
        List<String> items = new ArrayList<>();
        String [] splitData = data.split("\n");
        String curItem="";

        for(String line:splitData){
            levelSplit.checkLevelUp(line);
            if(levelSplit.mayAddLine()){
                curItem+=line+"\n";
            }
            // if we meet the requirements for a new element, save the current one and clean the string
            if(levelSplit.newElement(line)){
                items.add(curItem);
                curItem="";
            }
            levelSplit.checkLevelDown(line);
        }
        return items;
    }

    /**
     * general string pattern for finding an element
     * @param element    the element we're looking for
     * @return general string pattern
     */
    public static Pattern getStringPattern(String element){
        return Pattern.compile(".*?"+element+" = \"(.*?)\".*", Pattern.DOTALL);
    }

    /**
     * general int pattern for finding an element
     * @param element    the element we're looking for
     * @return general int pattern
     */
    public static Pattern getIntPattern(String element){
        return Pattern.compile(".*?"+element+" = (\\w+).*", Pattern.DOTALL);
    }
}
