package br.com.cantinhoinc.sqlquerybuilder.builder;

import br.com.cantinhoinc.sqlquerybuilder.validation.Validator;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

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
public class InsertStatementBuilder {

    private String tableName;

    private SortedMap<String, String> columnSet;

    public InsertStatementBuilder() {
        columnSet = new TreeMap<>();
    }

    public InsertStatementBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertStatementBuilder column(final String key, final String value) {
        columnSet.put(key, value);
        return this;
    }

    private String makeList(String[] array) {
        final String SEPARATOR = ", ";

        return String.join(
                SEPARATOR,
                array);
    }

    private String makeParameterList(final String[] array) {
        final String SEPARATOR = ", ";

        Arrays.fill(array, "?");

        return String.join(
                SEPARATOR,
                array);
    }

    private String[] populateArray(String[] array, String[] values) {
        for (int i = 0; i < array.length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    private String[] convertArray(Object[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].toString();
        }
        return result;
    }

    private String[] addSuffixToElements(String[] array, String suffix) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] + suffix;
        }
        return array;
    }

    private String[] getColumnKeys() {
        return convertArray(columnSet.keySet().toArray());
    }

    private String makeQuery() {
        return "INSERT INTO " + tableName +
                " SET (" + makeList(convertArray(columnSet.keySet().toArray())) + ")" +
                " VALUES (" + makeParameterList(convertArray(columnSet.values().toArray())) + ")";
    }

    private String[] makeKeySequence() {
        return convertArray(columnSet.values().toArray());
    }

    public CustomStatement build() {
        Validator.tableName(tableName);
        Validator.columnSet(columnSet);

        return new CustomStatement(makeQuery(), makeKeySequence());
    }
}
