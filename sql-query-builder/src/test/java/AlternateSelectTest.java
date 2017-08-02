import br.com.cantinhoinc.sqlquerybuilder.builder.AlternateSelectBuilder;
import br.com.cantinhoinc.sqlquerybuilder.builder.Condition;
import br.com.cantinhoinc.sqlquerybuilder.builder.Join;
import br.com.cantinhoinc.sqlquerybuilder.builder.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Marcus Oliveira.
 */
public class AlternateSelectTest {


    String[] tables = new String[]{"table1", "table2"};

    Condition condition1;
    Condition condition2;
    Condition condition3;

    Condition[] conditions;// = new String[] {"column1=2","column2>6", "column<7"};

    final String BASE_TEST_QUERY = "SELECT * FROM table1, table2 WHERE column1=2 AND column2>6 OR column3<7";

    final String JOIN_TEST_QUERY = "SELECT * FROM table1, table2 WHERE column1=2 AND column2>6 OR column3<7 AND table1.column1=table2.column1";

    @Before
    public void setup() {
        condition1 = Condition.make("column1","=","2");
        condition2 = Condition.make("column2",">","6");
        condition3 = Condition.make("column3","<","7");
        conditions = new Condition[] {condition1, condition2, condition3};
    }

    @Test
    public void test2() {
        AlternateSelectBuilder builder = new AlternateSelectBuilder();

        String query = builder
                .select()
                .from(tables)
                .where(condition1)
                .and(condition2)
                .or(condition3)
                .build();

        Assert.assertEquals(BASE_TEST_QUERY, query);
    }

    @Test
    public void test3() {
        AlternateSelectBuilder builder = new AlternateSelectBuilder();

        String query = builder
                .select()
                .from(tables)
                .join(
                        new Join(
                                new Point(
                                        tables[0],
                                        "column1" ),
                                new Point(
                                        tables[1],
                                        "column1")))
                .where(condition1)
                .and(condition2)
                .or(condition3)
                .build();

        Assert.assertEquals(JOIN_TEST_QUERY, query);
    }

}
