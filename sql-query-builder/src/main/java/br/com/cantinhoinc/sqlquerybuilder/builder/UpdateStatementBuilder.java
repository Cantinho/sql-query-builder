package br.com.cantinhoinc.sqlquerybuilder.builder;

import br.com.cantinhoinc.sqlquerybuilder.validation.Validator;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class UpdateStatementBuilder {

    private String tableName;

    private SortedMap<String, String> columnSet;

    private SortedMap<String, String> conditionSet;

    public UpdateStatementBuilder() {
        columnSet = new TreeMap<>();
        conditionSet = new TreeMap<>();
    }

    public UpdateStatementBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UpdateStatementBuilder column(final String key, final String value) {
        columnSet.put(key, value);
        return this;
    }

    public UpdateStatementBuilder condition(final String key, final String value) {
        conditionSet.put(key, value);
        return this;
    }

    private String makeNewValuesAssignments(Map<String, String> columnSet) {
        final String SEPARATOR = ", ";
        final String ASSIGNMENT_SUFFIX = "=?";

        return String.join(
                SEPARATOR,
                addSuffixToElements(
                        populateArray(
                                new String[columnSet.size()],
                                convertArray(columnSet.keySet().toArray())),
                        ASSIGNMENT_SUFFIX));
    }

    private String makeWhereClause(Map<String, String> conditionSet) {
        final String SEPARATOR = " AND ";
        final String ASSIGNMENT_SUFFIX = "=?";

        return String.join(
                SEPARATOR,
                addSuffixToElements(
                populateArray(
                        new String[conditionSet.size()],
                        convertArray( conditionSet.keySet().toArray())),
                        ASSIGNMENT_SUFFIX));
    }

    private String[] populateArray(String[] array, String[] values) {
        for (int i = 0; i < array.length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    private String[] convertArray(Object[] array) {
        String[] result= new String[array.length];
        for (int i=0; i<array.length; i++) {
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

    private static String[] concatenate(final String[] arrayA, final String[] arrayB) {
        String[] result = new String[arrayA.length+arrayB.length];
        System.arraycopy(arrayA, 0, result, 0, arrayA.length);
        System.arraycopy(arrayB, 0, result, arrayA.length, arrayB.length);
        return result;
    }

    private String makeQuery() {
        return "UPDATE " + tableName +
                " SET " + makeNewValuesAssignments(columnSet) +
                " WHERE " + makeWhereClause(conditionSet);
    }

    private String[] makeKeySequence() {
        return concatenate(convertArray(columnSet.values().toArray()), convertArray( conditionSet.values().toArray()));
    }

    public CustomStatement build() {
        Validator.tableName(tableName);
        Validator.columnSet(columnSet);
        Validator.conditionSet(conditionSet);

        return new CustomStatement(makeQuery(), makeKeySequence());
    }
}
