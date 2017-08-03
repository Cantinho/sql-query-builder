import br.com.cantinhoinc.sqlquerybuilder.builder.AlternateSelectBuilder;
import br.com.cantinhoinc.sqlquerybuilder.builder.Condition;
import br.com.cantinhoinc.sqlquerybuilder.builder.Join;
import br.com.cantinhoinc.sqlquerybuilder.builder.Point;
import models.Product;
import models.ProductPriceGreaterThanCondition;
import models.ProductPriceLessThanCondition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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



    /**
     * Builds a select * from a table.
     */
    @Test
    public void testSelect1() {
        final String QUERY = "SELECT * FROM Product";
        AlternateSelectBuilder builder = new AlternateSelectBuilder();

        String query = builder
                .select()
                .from(Product.class)
                .build();

        Assert.assertEquals(QUERY, query);
    }

    /**
     * Builds a select code, name, price from a table.
     */
    @Test
    public void testSelect2() {
        final String QUERY = "SELECT code, name, price FROM Product";
        AlternateSelectBuilder builder = new AlternateSelectBuilder();

        String query = builder
                .select(Product.class)
                .from(Product.class)
                .build();

        Assert.assertEquals(QUERY, query);
    }

    /**
     * Builds a select code, name, price from a table where price is greater than 1000.0.
     */
    @Test
    public void testSelect3() {
        final String QUERY = "SELECT code, name, price FROM Product WHERE price > 1000.0";
        AlternateSelectBuilder builder = new AlternateSelectBuilder();


        String query = builder
                .select(Product.class)
                .from(Product.class)
                .where(new ProductPriceGreaterThanCondition(1000.0))
                .build();

        Assert.assertEquals(QUERY, query);
    }

    /**
     * Builds a select code, name, price from a table where price is less than 1000.0.
     */
    @Test
    public void testSelect4() {
        final String QUERY = "SELECT code, name, price FROM Product WHERE price < 1000.0";
        AlternateSelectBuilder builder = new AlternateSelectBuilder();


        String query = builder
                .select(Product.class)
                .from(Product.class)
                .where(new ProductPriceLessThanCondition(1000.0))
                .build();

        Assert.assertEquals(QUERY, query);
    }

    /**
     * Builds a select code, name, price from a table where price is less than 1000.0.
     */
    @Test
    public void testSelect5() {
        final String QUERY = "SELECT code, name, price FROM Product WHERE price >= 1000.0";
        AlternateSelectBuilder builder = new AlternateSelectBuilder();


        String query = builder
                .select(Product.class)
                .from(Product.class)
                .where(new ProductPriceLessThanCondition(1000.0))
                .build();

        Assert.assertEquals(QUERY, query);
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
