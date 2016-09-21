package com.maoren.plugin;


import org.apache.ddlutils.Platform;
import  org.apache.ddlutils.PlatformBuilder;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by maoren on 16-9-20.
 */
@Mojo(
        name = "domain-xml-2database",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES
)
public class Ddl2DatabaseMojo extends AbstractMojo {
    @Parameter(
            defaultValue = "${project}"
    )
    private MavenProject mavenProject;

    /**
     * Location of the schema file.
     *
     * @parameter
     * @required
     */
    @Parameter(
            defaultValue = "${project.basedir}/target/generated-resources/resources/domain.xml"
    )
    private String schemaFile;

    /**
     * Location of the data file.
     *
     * @parameter
     * @required
     */
    @Parameter(
            defaultValue = "${project.basedir}/target/generated-resources/resources/domain.data.xml"
    )
    private String dataFile;

    /**
     * Platform type
     *
     * @parameter
     * @required
     */
    @Parameter(
            required = true
    )
    private String jdbcDriver;

    /**
     * database connection url
     *
     * @parameter
     * @required
     */
    @Parameter(
            required = true
    )
    private String jdbcUrl;

    /**
     * Datasource username
     *
     * @parameter
     * @required
     */
    @Parameter(
            required = true
    )
    private String jdbcUser;

    /**
     * Datasource password
     *
     * @parameter
     * @required
     */
    @Parameter(
            required = true
    )
    private String jdbcPassword;

    /**
     * is export data
     * */
    @Parameter()
    private Boolean  exportData=false;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Platform pf = PlatformBuilder.builder()
                .jdbcDriver(jdbcDriver)
                .jdbcUrl(jdbcUrl)
                .jdbcUser(jdbcUser)
                .jdbcPassword(jdbcPassword)
                .build();

        Database db = pf.readModelFromDatabase(null);

        try {
            Files.createDirectories(Paths.get(schemaFile).getParent());
            new DatabaseIO().read(new File(schemaFile));
        } catch (IOException e) {
            throw new MojoExecutionException("Error creating schema file", e);
        }

        /*if (exportData){
            org.apache.ddlutils.Platform apf= ApachePlatformBuilder.builder()
                    .jdbcDriver(jdbcDriver)
                    .jdbcUrl(jdbcUrl)
                    .jdbcUser(jdbcUser)
                    .jdbcPassword(jdbcPassword)
                    .build();
            try {
                if (StringUtils.isNotBlank(dataFile)) {
                    Files.createDirectories(Paths.get(dataFile).getParent());
                    new org.apache.ddlutils.io.DatabaseDataIO().writeDataToXML(apf,
                            apf.readModelFromDatabase(null), dataFile, "UTF-8");
                }
            } catch (IOException e) {
                throw new MojoExecutionException("Error creating data file", e);
            }
        }*/
    }
}
