package br.com.cantinhoinc.sqlquerybuilder.builder;

/**
 * Created by Marcus Oliveira.
 */
public class Condition {

    private String left;

    private String right;

    private String operator;

    public static Condition make(
            String left,
            String operator,
            String right) {
        Condition condition = new Condition();
        condition.setLeft(left);
        condition.setOperator(operator);
        condition.setRight(right);
        return condition;
    }


    private Condition() {
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String value() {
        return left + operator + right;
    }
}
