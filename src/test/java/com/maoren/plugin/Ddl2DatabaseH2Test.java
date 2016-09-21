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
public class Ddl2DatabaseH2Test {
    @Test
    public void ss(){
        String jdbcDriver="org.h2.Driver";
        String jdbcUrl="jdbc:h2:mem:tmp-domain-db;DB_CLOSE_DELAY=-1";
        //String jdbcUrl="jdbc:h2:/tmp/tmp-domain-db";
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

        Database db = new DatabaseIO().read(new File(schemaFile));

        try {
            Files.createDirectories(Paths.get(schemaFile).getParent());
            String sql=pf.getCreateTablesSql(db,true,false);
            System.out.println(sql);
             pf.createTables(db,false,false);
        } catch (IOException e) {
            System.err.print("error//");
        }
        new org.apache.ddlutils.io.DatabaseDataIO().writeDataToDatabase(pf,db,new String[] {dataFile});
    }
}
