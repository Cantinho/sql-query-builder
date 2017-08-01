package br.com.cantinhoinc.sqlquerybuilder.exception;

/**
 * Created by Marcus Oliveira.
 */
public class InvalidColumnSet  extends IllegalArgumentException {
    public InvalidColumnSet(String s) {
        super(s);
    }
}
