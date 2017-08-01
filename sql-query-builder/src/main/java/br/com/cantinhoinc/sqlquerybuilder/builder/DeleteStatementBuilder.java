package br.com.cantinhoinc.sqlquerybuilder.builder;

import br.com.cantinhoinc.sqlquerybuilder.validation.Validator;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Marcus Oliveira.
 */
public class DeleteStatementBuilder {

    private String tableName;

    private SortedMap<String, String> conditionSet;

    public DeleteStatementBuilder() {
        conditionSet = new TreeMap<>();
    }

    public DeleteStatementBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteStatementBuilder condition(final String key, final String value) {
        conditionSet.put(key, value);
        return this;
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

    private String makeQuery() {
        return "DELETE FROM " + tableName +
                " WHERE " + makeWhereClause(conditionSet);
    }

    private String[] makeKeySequence() {
        return convertArray( conditionSet.values().toArray());
    }

    public CustomStatement build() {
        Validator.tableName(tableName);
        Validator.conditionSet(conditionSet);

        return new CustomStatement(makeQuery(), makeKeySequence());
    }
}
