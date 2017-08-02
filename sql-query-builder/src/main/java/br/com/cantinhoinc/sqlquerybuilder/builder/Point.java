package br.com.cantinhoinc.sqlquerybuilder.builder;

public class Point {
    String table;
    String column;

    private Point() {
    }

    public Point(String table, String column) {
        this.table = table;
        this.column = column;
    }

    public String value() {
        return table+"."+column;
    }
}