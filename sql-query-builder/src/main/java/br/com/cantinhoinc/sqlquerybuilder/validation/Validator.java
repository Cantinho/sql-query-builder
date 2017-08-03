package br.com.cantinhoinc.sqlquerybuilder.validation;

import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidColumn;
import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidColumnSet;
import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidCondition;
import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidTableName;

import java.util.SortedMap;

/**
 * Copyright 2016 Cantinho. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * @author Marcus Vinícius Souza de Oliveira - https://github.com/mvncius
 * @author Samir Trajano Feitosa - https://github.com/samirtf
 * @author Cantinho - Github https://github.com/Cantinho
 * @since 2016
 * @license Apache 2.0
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 *
 */
public final class Validator {

    public static void tableName(final String tableName) {
        if (checkForInvalidKey(tableName)) {
            throw new InvalidTableName("Invalid table name{" + tableName + "}");
        }
    }

    public static void columnSet(final SortedMap<String, String> columnSet) {
        if (columnSet == null || columnSet.size() == 0) {
            throw new InvalidColumnSet("Empty or null column set.");
        }
        for (String column : columnSet.keySet()) {
            if (checkForInvalidKey(column)) {
                throw new InvalidColumn("Invalid column{" + column + "}");
            }
        }
    }

    public static void conditionSet(final SortedMap<String, String> conditionSet) {
        if (conditionSet == null || conditionSet.size() == 0) {
            throw new InvalidColumnSet("Empty or null column set.");
        }
        for (String condition : conditionSet.keySet()) {
            if (checkForInvalidKey(condition)) {
                throw new InvalidCondition("Invalid condition{" + condition + "}");
            }
        }
    }

    private static boolean checkForInvalidKey(final String key) {
        return key == null ||
                key.trim().isEmpty() ||
                !key.matches("(?i)[a-z][a-z0-9_]*");

    }
}
