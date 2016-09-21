import org.apache.commons.lang3.StringUtils;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformBuilder;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by maoren on 16-9-20.
 */
public class GenerateDdlMojoTest {

    @Test
    public void ss(){
        String jdbcDriver="com.mysql.jdbc.Driver";
        String jdbcUrl="jdbc:mysql://localhost:3306/ccms6";
        String jdbcUser="root";
        String jdbcPassword="root";
        String schemaFile="/tmp/aa.xml";
        String dataFile="/tmp/aa.data.xml";


        Platform pf = PlatformBuilder.builder()
                .jdbcDriver(jdbcDriver)
                .jdbcUrl(jdbcUrl)
                .jdbcUser(jdbcUser)
                .jdbcPassword(jdbcPassword)
                .build();
        pf.setDelimitedIdentifierModeOn(true);//处理关键字
        Database db = pf.readModelFromDatabase(null);

        try {
            Files.createDirectories(Paths.get(schemaFile).getParent());
            new DatabaseIO().write(db, schemaFile);
        } catch (IOException e) {
        }

        try {
            if (StringUtils.isNotBlank(dataFile)) {
                Files.createDirectories(Paths.get(dataFile).getParent());
                new org.apache.ddlutils.io.DatabaseDataIO().writeDataToXML(pf,
                        pf.readModelFromDatabase(null), dataFile, "UTF-8");
            }
        } catch (IOException e) {
        }
    }
}
