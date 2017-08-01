import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.UpdateStatementBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UpdateStatementBuilderTest {

    private UpdateStatementBuilder updateStatementBuilder1;
    private UpdateStatementBuilder updateStatementBuilder2;
    private UpdateStatementBuilder updateStatementBuilder3;

    @Test
    public void testEmptyBuilder() {
        updateStatementBuilder1 = new UpdateStatementBuilder();
        try {
            CustomStatement invalidCustomStatement = updateStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testEmptyTableName() {
        updateStatementBuilder1 = new UpdateStatementBuilder();

        updateStatementBuilder1.column("column_key", "column_value");
        updateStatementBuilder1.condition("condition_key", "condition_value");

        try {
            CustomStatement invalidCustomStatement = updateStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidColumnSet() {
        updateStatementBuilder1 = new UpdateStatementBuilder();

        updateStatementBuilder1.table("table_name");
        updateStatementBuilder1.column("column_key", "column_value");

        try {
            CustomStatement invalidCustomStatement = updateStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Empty or null column set.",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidConditionSet() {
        updateStatementBuilder1 = new UpdateStatementBuilder();

        updateStatementBuilder1.table("table_name");
        updateStatementBuilder1.condition("condition_key", "condition_value");

        try {
            CustomStatement invalidCustomStatement = updateStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Empty or null column set.",
                    e.getMessage());
        }
    }

    @Test
    public void testValidBuild() {
        updateStatementBuilder1 = new UpdateStatementBuilder();

        updateStatementBuilder1.table("table_name");
        updateStatementBuilder1.column("column_key", "column_value");
        updateStatementBuilder1.condition("condition_key", "condition_value");

        try {
            CustomStatement customStatement = updateStatementBuilder1.build();
            assertEquals("UPDATE table_name SET column_key=? WHERE condition_key=?",
                    customStatement.getQuery());
            assertEquals("CustomStatement{query='UPDATE table_name SET column_key=? WHERE condition_key=?', " +
                    "values=[column_value, condition_value]}", customStatement.toString());

            String keywordlessQuery = customStatement.getQuery()
                    .replace("UPDATE", "")
                    .replace("SET", "")
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
        updateStatementBuilder1 = new UpdateStatementBuilder();

        updateStatementBuilder1.table("table_name1");
        updateStatementBuilder1.column("column_key1", "column_value");
        updateStatementBuilder1.condition("condition_key1", "condition_value");

        updateStatementBuilder2 = new UpdateStatementBuilder();

        updateStatementBuilder2.table("table_name2");
        updateStatementBuilder2.column("column_key2", "column_value");
        updateStatementBuilder2.condition("condition_key2", "condition_value");

        updateStatementBuilder3 = new UpdateStatementBuilder();

        updateStatementBuilder3.table("table_name3");
        updateStatementBuilder3.column("column_key3", "column_value");
        updateStatementBuilder3.condition("condition_key3", "condition_value");


        assertEquals("CustomStatement{query='UPDATE table_name1 SET column_key1=? WHERE condition_key1=?', " +
                        "values=[column_value, condition_value]}",
                updateStatementBuilder1.build().toString());
        assertEquals("CustomStatement{query='UPDATE table_name2 SET column_key2=? WHERE condition_key2=?', " +
                        "values=[column_value, condition_value]}",
                updateStatementBuilder2.build().toString());
        assertEquals("CustomStatement{query='UPDATE table_name3 SET column_key3=? WHERE condition_key3=?', " +
                        "values=[column_value, condition_value]}",
                updateStatementBuilder3.build().toString());
    }

}
