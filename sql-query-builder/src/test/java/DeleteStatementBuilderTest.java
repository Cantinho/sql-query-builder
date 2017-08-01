import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.DeleteStatementBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Marcus Oliveira.
 */
public class DeleteStatementBuilderTest {

    private DeleteStatementBuilder deleteStatementBuilder1;
    private DeleteStatementBuilder deleteStatementBuilder2;
    private DeleteStatementBuilder deleteStatementBuilder3;

    @Test
    public void testEmptyBuilder() {
        deleteStatementBuilder1 = new DeleteStatementBuilder();
        try {
            CustomStatement invalidCustomStatement = deleteStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testEmptyTableName() {
        deleteStatementBuilder1 = new DeleteStatementBuilder();

        deleteStatementBuilder1.condition("column_key", "column_value");

        try {
            CustomStatement invalidCustomStatement = deleteStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidColumnSet() {
        deleteStatementBuilder1 = new DeleteStatementBuilder();

        deleteStatementBuilder1.table("table_name");

        try {
            CustomStatement invalidCustomStatement = deleteStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Empty or null column set.",
                    e.getMessage());
        }
    }

    @Test
    public void testValidBuild() {
        deleteStatementBuilder1 = new DeleteStatementBuilder();

        deleteStatementBuilder1.table("table_name");
        deleteStatementBuilder1.condition("column_key", "column_value");

        try {
            CustomStatement customStatement = deleteStatementBuilder1.build();
            assertEquals("DELETE FROM table_name WHERE column_key=?",
                    customStatement.getQuery());
            assertEquals("CustomStatement{query='DELETE FROM table_name " +
                    "WHERE column_key=?', values=[column_value]}",
                    customStatement.toString());

            String keywordlessQuery = customStatement.getQuery()
                    .replace("DELETE", "")
                    .replace("FROM", "")
                    .replace("WHERE", "");

            //Query does not contain upper case letters.
            assertEquals(
                    keywordlessQuery,
                    keywordlessQuery.toLowerCase());

        } catch (Exception e) {
            fail("Failed to build a valid custom statement. " +e.getMessage());
        }
    }

    @Test
    public void testSimultaneousBuilds() {
        deleteStatementBuilder1 = new DeleteStatementBuilder();

        deleteStatementBuilder1.table("table_name1");
        deleteStatementBuilder1.condition("column_key1", "column_value");

        deleteStatementBuilder2 = new DeleteStatementBuilder();

        deleteStatementBuilder2.table("table_name2");
        deleteStatementBuilder2.condition("column_key2", "column_value");

        deleteStatementBuilder3 = new DeleteStatementBuilder();

        deleteStatementBuilder3.table("table_name3");
        deleteStatementBuilder3.condition("column_key3", "column_value");


        assertEquals("CustomStatement{query='DELETE FROM table_name1 " +
                        "WHERE column_key1=?', values=[column_value]}",
                deleteStatementBuilder1.build().toString());
        assertEquals("CustomStatement{query='DELETE FROM table_name2 " +
                        "WHERE column_key2=?', values=[column_value]}",
                deleteStatementBuilder2.build().toString());
        assertEquals("CustomStatement{query='DELETE FROM table_name3 " +
                        "WHERE column_key3=?', values=[column_value]}",
                deleteStatementBuilder3.build().toString());
    }

}
