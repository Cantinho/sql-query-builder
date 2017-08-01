package br.com.cantinhoinc.sqlquerybuilder.exception;

/**
 * Created by Marcus Oliveira.
 */
public class InvalidColumn extends IllegalArgumentException {
    public InvalidColumn(String s) {
        super(s);
    }
}
