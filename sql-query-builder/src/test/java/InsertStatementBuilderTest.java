import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.InsertStatementBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Marcus Oliveira.
 */
public class InsertStatementBuilderTest {

    private InsertStatementBuilder insertStatementBuilder1;
    private InsertStatementBuilder insertStatementBuilder2;
    private InsertStatementBuilder insertStatementBuilder3;

    @Test
    public void testEmptyBuilder() {
        insertStatementBuilder1 = new InsertStatementBuilder();
        try {
            CustomStatement invalidCustomStatement = insertStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testEmptyTableName() {
        insertStatementBuilder1 = new InsertStatementBuilder();

        insertStatementBuilder1.column("column_key", "column_value");

        try {
            CustomStatement invalidCustomStatement = insertStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidColumnSet() {
        insertStatementBuilder1 = new InsertStatementBuilder();

        insertStatementBuilder1.table("table_name");

        try {
            CustomStatement invalidCustomStatement = insertStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Empty or null column set.",
                    e.getMessage());
        }
    }

    @Test
    public void testValidBuild() {
        insertStatementBuilder1 = new InsertStatementBuilder();

        insertStatementBuilder1.table("table_name");
        insertStatementBuilder1.column("column_key", "column_value");

        try {
            CustomStatement customStatement = insertStatementBuilder1.build();
            assertEquals("INSERT INTO table_name SET (column_key) VALUES (?)",
                    customStatement.getQuery());
            assertEquals("CustomStatement{query='INSERT INTO table_name SET (column_key) " +
                    "VALUES (?)', values=[column_value]}", customStatement.toString());

            String keywordlessQuery = customStatement.getQuery()
                    .replace("INSERT", "")
                    .replace("INTO", "")
                    .replace("SET", "")
                    .replace("VALUES", "");

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
        insertStatementBuilder1 = new InsertStatementBuilder();

        insertStatementBuilder1.table("table_name1");
        insertStatementBuilder1.column("column_key1", "column_value");

        insertStatementBuilder2 = new InsertStatementBuilder();

        insertStatementBuilder2.table("table_name2");
        insertStatementBuilder2.column("column_key2", "column_value");

        insertStatementBuilder3 = new InsertStatementBuilder();

        insertStatementBuilder3.table("table_name3");
        insertStatementBuilder3.column("column_key3", "column_value");


        assertEquals("CustomStatement{query='INSERT INTO table_name1 SET (column_key1) " +
                        "VALUES (?)', values=[column_value]}",
                insertStatementBuilder1.build().toString());
        assertEquals("CustomStatement{query='INSERT INTO table_name2 SET (column_key2) " +
                        "VALUES (?)', values=[column_value]}",
                insertStatementBuilder2.build().toString());
        assertEquals("CustomStatement{query='INSERT INTO table_name3 SET (column_key3) " +
                        "VALUES (?)', values=[column_value]}",
                insertStatementBuilder3.build().toString());
    }

}
