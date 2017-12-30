/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.bvt.sql.sqlite;

import com.alibaba.druid.sql.MysqlTest;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Column;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Assert;

import java.util.List;

public class SQLite_SelectTest_1 extends MysqlTest {

    public void test_0() throws Exception {
        String sql = "SELECT xiaojie,event_cnt,country_code,area_code,carrier,country,province,city,type,subtype,displayname,slogan,logo,source,state,priority,is_change FROM ktv_xiaojie_20170921 WHERE day_modify > 20170921 AND priority >= 4499000 ORDER BY priority DESC LIMIT 500000";

        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.SQLITE);
        SQLStatement stmt = statementList.get(0);
        print(statementList);

        Assert.assertEquals(1, statementList.size());

        SchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        stmt.accept(visitor);

        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());
//        System.out.println("coditions : " + visitor.getConditions());
//        System.out.println("orderBy : " + visitor.getOrderByColumns());
        
        Assert.assertEquals(1, visitor.getTables().size());
        Assert.assertEquals(18, visitor.getColumns().size());
        Assert.assertEquals(2, visitor.getConditions().size());

        Assert.assertTrue(visitor.getTables().containsKey(new TableStat.Name("ktv_xiaojie_20170921")));

        Assert.assertTrue(visitor.getColumns().contains(new Column("ktv_xiaojie_20170921", "xiaojie")));
        Assert.assertTrue(visitor.getColumns().contains(new Column("ktv_xiaojie_20170921", "event_cnt")));
    }
}
