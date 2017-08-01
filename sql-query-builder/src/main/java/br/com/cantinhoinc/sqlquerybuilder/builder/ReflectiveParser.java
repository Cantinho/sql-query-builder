package br.com.cantinhoinc.sqlquerybuilder.builder;

import br.com.cantinhoinc.sqlquerybuilder.annotation.Column;
import br.com.cantinhoinc.sqlquerybuilder.annotation.Key;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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