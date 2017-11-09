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
 * interface for rulecontributor nodes
 * implemented by DefaultRuleContributorNode
 */
interface RuleContributorNode {

    /**
     * returns the rules that lead from this node to the child, merged with the rules of the
     * parents that lead to this node
     * @param childId    the target node
     * @return a string with the rule
     */
    String getRules(String childId);
}
