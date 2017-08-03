import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.SingleSelectStatementBuilder;
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
public class SingleSelectStatementBuilderTest {

    private SingleSelectStatementBuilder singleSelectStatementBuilder1;
    private SingleSelectStatementBuilder singleSelectStatementBuilder2;
    private SingleSelectStatementBuilder singleSelectStatementBuilder3;

    @Test
    public void testEmptyBuilder() {
        singleSelectStatementBuilder1 = new SingleSelectStatementBuilder();
        try {
            CustomStatement invalidCustomStatement = singleSelectStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testEmptyTableName() {
        singleSelectStatementBuilder1 = new SingleSelectStatementBuilder();

        singleSelectStatementBuilder1.condition("column_key", "column_value");

        try {
            CustomStatement invalidCustomStatement = singleSelectStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Invalid table name{null}",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidColumnSet() {
        singleSelectStatementBuilder1 = new SingleSelectStatementBuilder();

        singleSelectStatementBuilder1.table("table_name");

        try {
            CustomStatement invalidCustomStatement = singleSelectStatementBuilder1.build();
            fail("Invalid custom statement was built. " + invalidCustomStatement.toString());
        } catch (Exception e) {
            assertEquals("Empty or null column set.",
                    e.getMessage());
        }
    }

    @Test
    public void testValidBuild() {
        singleSelectStatementBuilder1 = new SingleSelectStatementBuilder();

        singleSelectStatementBuilder1.table("table_name");
        singleSelectStatementBuilder1.condition("column_key", "column_value");

        try {
            CustomStatement customStatement = singleSelectStatementBuilder1.build();
            assertEquals("SELECT * FROM table_name WHERE column_key=?",
                    customStatement.getQuery());
            assertEquals("CustomStatement{query='SELECT * FROM table_name WHERE column_key=?', " +
                    "values=[column_value]}",
                    customStatement.toString());

            String keywordlessQuery = customStatement.getQuery()
                    .replace("SELECT", "")
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
        singleSelectStatementBuilder1 = new SingleSelectStatementBuilder();

        singleSelectStatementBuilder1.table("table_name1");
        singleSelectStatementBuilder1.condition("column_key1", "column_value");

        singleSelectStatementBuilder2 = new SingleSelectStatementBuilder();

        singleSelectStatementBuilder2.table("table_name2");
        singleSelectStatementBuilder2.condition("column_key2", "column_value");

        singleSelectStatementBuilder3 = new SingleSelectStatementBuilder();

        singleSelectStatementBuilder3.table("table_name3");
        singleSelectStatementBuilder3.condition("column_key3", "column_value");


        assertEquals("CustomStatement{query='SELECT * FROM table_name1 " +
                        "WHERE column_key1=?', values=[column_value]}",
                singleSelectStatementBuilder1.build().toString());
        assertEquals("CustomStatement{query='SELECT * FROM table_name2 " +
                        "WHERE column_key2=?', values=[column_value]}",
                singleSelectStatementBuilder2.build().toString());
        assertEquals("CustomStatement{query='SELECT * FROM table_name3 " +
                        "WHERE column_key3=?', values=[column_value]}",
                singleSelectStatementBuilder3.build().toString());
    }

}
