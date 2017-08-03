import br.com.cantinhoinc.sqlquerybuilder.builder.CustomStatement;
import br.com.cantinhoinc.sqlquerybuilder.builder.InsertStatementBuilder;
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
