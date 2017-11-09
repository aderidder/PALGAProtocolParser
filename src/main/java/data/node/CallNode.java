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

/**
 * Callnode
 */
public class CallNode extends DefaultNode{
    /**
     * constructor
     * @param data    data string for the node
     */
    CallNode(String data) {
        super(data, NodeTypeEnum.CALL);
    }

    /**
     * node specific data, which cannot be handled by the default nodes
     * @param data    data string for the node
     */
    @Override
    void addNodeSpecificData(String data){
        // nothing specific to add
    }

    /**
     * returns whether this type of node is a codebook node
     * @return false
     */
    @Override
    public boolean isCodebookNode() {
        return false;
    }
}
