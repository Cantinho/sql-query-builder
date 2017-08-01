package br.com.cantinhoinc.sqlquerybuilder.exception;

/**
 * Created by Marcus Oliveira.
 */
public class InvalidConditionSet  extends IllegalArgumentException {
    public InvalidConditionSet(String s) {
        super(s);
    }
}
