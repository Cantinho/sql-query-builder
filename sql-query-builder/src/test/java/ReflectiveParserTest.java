import br.com.cantinhoinc.sqlquerybuilder.annotation.Column;
import br.com.cantinhoinc.sqlquerybuilder.annotation.Key;
import br.com.cantinhoinc.sqlquerybuilder.builder.ReflectiveParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReflectiveParserTest {

    class Product {
        @Key
        @Column("code")
        Integer code;

        @Column("price")
        Double price;

        @Column("name")
        String name;
    }

    class Person {
        @Key
        @Column("p.ssn")
        String ssn;

        @Column("p.name")
        String name;

        @Column("a.address")
        String address;

        @Column("p.age")
        Integer age;
    }

    private Product productSample;
    private Person personSample;

    @Before
    public void setup() {
        productSample = new Product();
        productSample.code = 42;
        productSample.name = "Pencil";
        productSample.price = 4.2;

        personSample = new Person();
        personSample.ssn = "42424242";
        personSample.name = "Ororo Monroe";
        personSample.age = 42;
        personSample.address = "The Mansion, 42424\n Bayview, CA";
    }

    @Test
    public void testInsertStatement() {
        assertEquals("CustomStatement{query='INSERT INTO table_product SET (code, name, price) " +
                        "VALUES (?, ?, ?)', values=[42, Pencil, 4.2]}",
                ReflectiveParser.parseInsertCustomStatement(productSample, "table_product").toString());
        assertEquals("CustomStatement{query='INSERT INTO table_person SET (address, age, name, ssn) " +
                        "VALUES (?, ?, ?, ?)', values=[The Mansion, 42424\n Bayview, CA, 42, Ororo Monroe, 42424242]}",
                ReflectiveParser.parseInsertCustomStatement(personSample, "table_person").toString());
    }

    @Test
    public void testUpdateStatement() {
        assertEquals("CustomStatement{query='UPDATE table_product SET name=?, price=? " +
                        "WHERE code=?', values=[Pencil, 4.2, 42]}",
                ReflectiveParser.parseUpdateCustomStatement(productSample, "table_product").toString());
        assertEquals("CustomStatement{query='UPDATE table_person SET address=?, age=?, name=? " +
                        "WHERE ssn=?', values=[The Mansion, 42424\n Bayview, CA, 42, Ororo Monroe, 42424242]}",
                ReflectiveParser.parseUpdateCustomStatement(personSample, "table_person").toString());
    }

    @Test
    public void testSingleSelectStatement() {
        assertEquals("CustomStatement{query='SELECT * FROM table_product WHERE code=?', values=[42]}",
                ReflectiveParser.parseSingleSelectCustomStatement(productSample, "table_product").toString());
        assertEquals("CustomStatement{query='SELECT * FROM table_person WHERE ssn=?', values=[42424242]}",
                ReflectiveParser.parseSingleSelectCustomStatement(personSample, "table_person").toString());
    }

    @Test
    public void testDeleteStatement() {
        assertEquals("CustomStatement{query='DELETE FROM table_product WHERE code=?', values=[42]}",
                ReflectiveParser.parseDeleteCustomStatement(productSample, "table_product").toString());
        assertEquals("CustomStatement{query='DELETE FROM table_person WHERE ssn=?', values=[42424242]}",
                ReflectiveParser.parseDeleteCustomStatement(personSample, "table_person").toString());
    }
}
