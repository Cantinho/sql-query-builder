package br.com.cantinhoinc.sqlquerybuilder.builder;

public class Join {

    Point joinPointA;
    Point joinPointB;

    private Join() {}

    public Join(Point joinPointA, Point joinPointB) {
        this.joinPointA = joinPointA;
        this.joinPointB = joinPointB;
    }

}
