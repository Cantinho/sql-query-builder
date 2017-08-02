package br.com.cantinhoinc.sqlquerybuilder.builder;

/**
 * Created by Marcus Oliveira.
 */
public class AlternateSelectBuilder {

    private String[] columns;

    private String[] tables;

    private Join[] joins;

    private Condition[] conditions;

    private String[] operators;


    public AlternateSelectBuilder select(final String... columns) {
        this.columns = columns;
        return this;
    }

    public AlternateSelectBuilder select() {
        this.columns = new String[]{};
        return this;
    }

    public AlternateSelectBuilder from(final String... tables) {
        if (columns == null) {
            throw new IllegalArgumentException("Invalid columns");
        }
        this.tables = tables;
        return this;
    }

    public AlternateSelectBuilder join(final Join... joins) {
        if (tables == null) {
            throw new IllegalArgumentException("Invalid table set");
        }
        this.joins = joins;
        return this;
    }

//    public AlternateSelectBuilder where(final Condition[] conditions, final String[] operators) {
//        if (tables == null) {
//            throw new IllegalArgumentException("Invalid table set");
//        }
//
//        if (conditions.length != operators.length + 1) {
//            throw new IllegalArgumentException("Number of conditions and operator do not match");
//        }
//        this.conditions = conditions;
//        this.operators = operators;
//        return this;
//    }

    public AlternateSelectBuilder where(Condition condition) {
        if (tables == null) {
            throw new IllegalArgumentException("Invalid table set");
        }

        if (conditions != null || operators != null) {
            throw new IllegalArgumentException("Invalid where clause");
        }
        conditions = new Condition[]{condition};
        operators = new String[]{};
        return this;
    }

    public AlternateSelectBuilder and(final Condition condition) {
        if (tables == null) {
            throw new IllegalArgumentException("Invalid table set");
        }
        if (conditions == null || operators == null || conditions.length == 0) {
            throw new IllegalArgumentException("Invalid where clause");
        }

        operators = concatenate(operators, "AND");
        conditions = concatenate(conditions, condition);
        return this;
    }

    public AlternateSelectBuilder or(final Condition condition) {
        if (tables == null) {
            throw new IllegalArgumentException("Invalid table set");
        }

        if (conditions == null || operators == null || conditions.length == 0) {
            throw new IllegalArgumentException("Invalid where clause");
        }

        operators = concatenate(operators, "OR");
        conditions = concatenate(conditions, condition);
        return this;
    }

    private static String[] concatenate(final String[] array, final String element) {
        String[] result = new String[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = element;
        return result;
    }

    private static Condition[] concatenate(final Condition[] array, final Condition element) {
        Condition[] result = new Condition[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = element;
        return result;
    }


    public String build() {
        if (tables == null) {
            throw new IllegalArgumentException("Invalid table set");
        }


        int startPosition = 0;

        if (joins != null && joins.length > 0) {
            if (conditions == null || conditions.length == 0) {
                startPosition = 1;
                where(Condition.make(
                        joins[0].joinPointA.value(), "=", joins[0].joinPointB.value()));
            }
            for (int i = startPosition; i < joins.length; i++) {
                Join join = joins[i];
                and(Condition.make(
                        join.joinPointA.value(), "=", join.joinPointB.value()));
            }
        }

        String query = "SELECT ";

        if (columns.length == 0) {
            query += "* ";
        } else {
            query += String.join(", ", columns) + " ";
        }

        query += "FROM " + String.join(", ", tables) + " ";

        if (joins != null || conditions != null) {

            query += "WHERE ";

            if (conditions != null && conditions.length > 0) {
                query += conditions[0].value() + " ";

                for (int i = 1; i < conditions.length; i++) {
                    query += operators[i - 1] + " " + conditions[i].value() + " ";
                }
            }

            if (joins != null && joins.length > 0) {
                query += "";//TODO add join clause
            }
        }


        return query.trim();
    }
}

