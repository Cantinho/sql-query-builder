package br.com.cantinhoinc.sqlquerybuilder.validation;

import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidColumn;
import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidColumnSet;
import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidCondition;
import br.com.cantinhoinc.sqlquerybuilder.exception.InvalidTableName;

import java.util.SortedMap;

/**
 * Created by Marcus Oliveira.
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
