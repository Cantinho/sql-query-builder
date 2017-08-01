package br.com.cantinhoinc.sqlquerybuilder.builder;

/**
 * Created by Marcus Oliveira.
 */
public class AlternateSelectBuilder {

    private String[] columns;

    private String[] tables;

    private String[] joins;

    private String[] conditions;

    private String[] operators;


    public AlternateSelectBuilder select(final String[] columns) {
        this.columns = columns;
        return this;
    }

    public AlternateSelectBuilder select() {
        this.columns = new String[] {};
        return this;
    }

    public AlternateSelectBuilder from(final String[] tables) {
        if (columns==null) {
            throw new IllegalArgumentException("Invalid columns");
        }
        this.tables = tables;
        return this;
    }

    public AlternateSelectBuilder join(final String[] joins) {
        if (tables==null) {
            throw new IllegalArgumentException("Invalid table set");
        }
        this.joins = joins;
        return this;
    }

    public AlternateSelectBuilder where(final String[] conditions, final String[] operators) {
        if (tables==null) {
            throw new IllegalArgumentException("Invalid table set");
        }

        if (conditions.length!=operators.length+1) {
            throw new IllegalArgumentException("Number of conditions and operator do not match");
        }
        this.conditions = conditions;
        this.operators = operators;
        return this;
    }


    public String build() {
        if (tables==null) {
            throw new IllegalArgumentException("Invalid table set");
        }

        String query = "SELECT ";

        if (columns.length==0) {
            query += "* ";
        } else {
            query += String.join(", ", columns) +" ";
        }

        query += String.join(", ", tables)+" ";

        if (joins!=null || conditions!=null) {

            query += "WHERE ";

            if (conditions!=null && conditions.length>0) {
                query += conditions[0] + " ";

                for (int i=1; i<conditions.length; i++) {
                    query += operators[i-1]+ " "+conditions[i] + " ";
                }
            }

            if (joins!=null && joins.length>0) {
                query += "";//TODO add join clause
            }
        }



        return query;
    }
}

