package br.com.cantinhoinc.sqlquerybuilder.builder;

import br.com.cantinhoinc.sqlquerybuilder.validation.Validator;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

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
