package com.maoren.plugin;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformBuilder;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by maoren on 16-9-20.
 */
public class Ddl2DatabaseMysqlTest {
    @Test
    public void ss() throws IOException {
        String jdbcDriver="com.mysql.jdbc.Driver";
        String jdbcUrl="jdbc:mysql://localhost:3306/tt?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        String jdbcUser="root";
        String jdbcPassword="root";

        String schemaFile="/tmp/domain.xml";
        String dataFile="/tmp/domain.data.xml";


        Platform pf = PlatformBuilder.builder()
                .jdbcDriver(jdbcDriver)
                .jdbcUrl(jdbcUrl)
                .jdbcUser(jdbcUser)
                .jdbcPassword(jdbcPassword)
                .build();
        pf.setDelimitedIdentifierModeOn(true);//处理关键字
        Database db = new DatabaseIO().read(new File(schemaFile));

        try {
            Files.createDirectories(Paths.get(schemaFile).getParent());
            String sql=pf.getCreateTablesSql(db,true,false);
            System.out.println(sql);
            //写入schem到mysql数据库
            pf.createTables(db,true,false);
        } catch (IOException e) {
            throw e;
        }

        //读取数据到mysql数据库
        new org.apache.ddlutils.io.DatabaseDataIO().writeDataToDatabase(pf,
                pf.readModelFromDatabase(null),new String[] {dataFile});

    }
}
