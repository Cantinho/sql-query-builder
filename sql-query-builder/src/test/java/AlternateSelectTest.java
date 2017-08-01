import br.com.cantinhoinc.sqlquerybuilder.builder.AlternateSelectBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Marcus Oliveira.
 */
public class AlternateSelectTest {

    @Test
    public void test() {

        String[] tables = new String[] {"table1","table2"};

        String[] conditions = new String[] {"column1=2","column2>6", "column<7"};

        String[] operators = new String[] {"AND","OR"};



        AlternateSelectBuilder builder = new AlternateSelectBuilder();

        String query = builder
                .select()
                .from(tables)
                .where(conditions, operators)
                .build();

        Assert.assertEquals("", query);
    }
}
