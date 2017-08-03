package br.com.cantinhoinc.sqlquerybuilder.builder;

import br.com.cantinhoinc.sqlquerybuilder.annotation.Column;
import br.com.cantinhoinc.sqlquerybuilder.annotation.Key;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
public class ReflectiveParser {

    public static <CustomClass> CustomStatement parseInsertCustomStatement(final CustomClass object, final String tableName) {

        InsertStatementBuilder customStatement = new InsertStatementBuilder()
                .table(tableName);
        Field[] fields = object.getClass().getDeclaredFields();
        for (int counter = 0; counter < fields.length; counter++) {
            Field field = fields[counter];
            if (!Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    Column columnValue = field.getAnnotation(Column.class);
                    if (value != null && columnValue != null) {
                        Key keyValue = field.getAnnotation(Key.class);
                        customStatement = customStatement
                                .column(columnValue.value(), value.toString());
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return customStatement.build();
    }

    public static <CustomClass> CustomStatement parseUpdateCustomStatement(final CustomClass object, final String tableName) {

        UpdateStatementBuilder customStatement = new UpdateStatementBuilder()
                .table(tableName);
        Field[] fields = object.getClass().getDeclaredFields();
        for (int counter = 0; counter < fields.length; counter++) {
            Field field = fields[counter];
            if (!Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    Column columnValue = field.getAnnotation(Column.class);
                    if (value != null && columnValue != null) {
                        Key keyValue = field.getAnnotation(Key.class);
                        if (keyValue != null && keyValue.value()) {
                            customStatement = customStatement
                                    .condition(columnValue.value(), value.toString());
                        } else {
                            customStatement = customStatement
                                    .column(columnValue.value(), value.toString());
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return customStatement.build();
    }

    public static <CustomClass> CustomStatement parseDeleteCustomStatement(final CustomClass object, final String tableName) {

        DeleteStatementBuilder customStatement = new DeleteStatementBuilder()
                .table(tableName);
        Field[] fields = object.getClass().getDeclaredFields();
        for (int counter = 0; counter < fields.length; counter++) {
            Field field = fields[counter];
            if (!Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    Column columnValue = field.getAnnotation(Column.class);
                    if (value != null && columnValue != null) {
                        Key keyValue = field.getAnnotation(Key.class);
                        if (keyValue != null && keyValue.value()) {
                            customStatement = customStatement
                                    .condition(columnValue.value(), value.toString());
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return customStatement.build();
    }

    public static <CustomClass> CustomStatement parseSingleSelectCustomStatement(final CustomClass object, final String tableName) {

        SingleSelectStatementBuilder customStatement = new SingleSelectStatementBuilder()
                .table(tableName);
        Field[] fields = object.getClass().getDeclaredFields();
        for (int counter = 0; counter < fields.length; counter++) {
            Field field = fields[counter];
            if (!Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    Column columnValue = field.getAnnotation(Column.class);
                    if (value != null && columnValue != null) {
                        Key keyValue = field.getAnnotation(Key.class);
                        if (keyValue != null && keyValue.value()) {
                            customStatement = customStatement
                                    .condition(columnValue.value(), value.toString());
                        }

                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return customStatement.build();
    }

}