import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.DeleteStatementBuilder;
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
