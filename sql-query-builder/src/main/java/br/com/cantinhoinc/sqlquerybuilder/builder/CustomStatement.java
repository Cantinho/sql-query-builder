package br.com.cantinhoinc.sqlquerybuilder.builder;

import java.util.Arrays;

public class CustomStatement {

    String query;

    String[] values;

    public CustomStatement(String query, String[] values) {
        this.query = query;
        this.values = values;
    }

    public CustomStatement(String query) {
        this.query = query;
        this.values = new String[]{};
    }

    public String getQuery() {
        return query;
    }

    public String[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "CustomStatement{" +
                "query='" + query + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
