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

package data.node;


import java.util.List;
import java.util.stream.Collectors;

/**
 * RouterNode
 * is a rule contributer node
 */
class RouterNode extends DefaultRuleContributerNode{
    /**
     * constructor
     * @param data    data string for the node
     */
    RouterNode(String data) {
        super(data, NodeTypeEnum.ROUTER);
    }

    /**
     * node specific data, which cannot be handled by the default nodes
     * @param data    data string for the node
     */
    @Override
    void addNodeSpecificData(String data){

    }

    /**
     * returns whether this type of node is a codebook node
     * @return false
     */
    @Override
    public boolean isCodebookNode() {
        return false;
    }

    /**
     * generates the rule for this node which leads to the child
     * @param childId    a childId whence this node leads
     * @return string representation of the rule
     */
    @Override
    String getMyRule(String childId){
        List<String> matches = outputComponent.getMatchedId(childId);
        List<String> rules = multiPartsComponent.getRules();
        if(rules.size()>1){
            System.err.println("multiple rule variables in router node?? "+id);
        }
        return "["+rules.get(0)+" == "+matches.stream().collect(Collectors.joining(" OR "))+"]";
    }

}
