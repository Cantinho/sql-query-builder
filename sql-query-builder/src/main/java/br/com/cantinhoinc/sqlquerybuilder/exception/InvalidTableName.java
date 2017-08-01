package br.com.cantinhoinc.sqlquerybuilder.exception;

/**
 * Created by Marcus Oliveira.
 */
public class InvalidTableName extends IllegalArgumentException {

    public InvalidTableName(String s) {
        super(s);
    }
}
