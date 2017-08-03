import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.UpdateStatementBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Copyright 2016 Cantinho. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * @author Marcus Vinícius Souza de Oliveira - https://github.com/mvncius
 * @author Samir Trajano Feitosa - https://github.com/samirtf
 * @author Cantinho - Github https://github.com/Cantinho
 * @since 2016
 * @license Apache 2.0
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 *
 */
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
