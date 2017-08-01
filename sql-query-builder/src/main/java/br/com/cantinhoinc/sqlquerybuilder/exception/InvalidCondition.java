package br.com.cantinhoinc.sqlquerybuilder.exception;

/**
 * Created by Marcus Oliveira.
 */
public class InvalidCondition  extends IllegalArgumentException {
    public InvalidCondition(String s) {
        super(s);
    }
}
