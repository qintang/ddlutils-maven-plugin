# ddlutils-maven-plugin 
   数据库scheme/data导入到处

## 依赖
*   Apache DdlUtils [qintang/ddlutils][1], fork版本,加入对H2的支持

#如何使用
## `注意`由于现在没有发布到中央maven库,源代码下载编译安装到本地
```shell
git clone https://github.com/qintang/ddlutils-maven-plugin.git 
cd ddlutils-maven-plugin
mvn clean install
```

## 1.在项目中引入插件
```xml
<plugin>
    <groupId>com.maoren.plugin</groupId>
    <artifactId>ddlutils-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <configuration>
        <jdbcDriver>com.mysql.jdbc.Driver</jdbcDriver>
        <jdbcUrl><![CDATA[jdbc:mysql://localhost:3306/tt?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true]]></jdbcUrl>
        <jdbcUser>root</jdbcUser>
        <jdbcPassword>root</jdbcPassword>
        <exportData>true</exportData>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.32</version>
        </dependency>
    </dependencies>
</plugin>
```
## 2.配置选项
* schemaFile scheme xml文件位置,(导入或导出)
  * 默认配置:${project.basedir}/target/generated-resources/resources/domain.xml
* dataFile 数据库中数据(导入或导出)
  * 默认配置:${project.basedir}/target/generated-resources/resources/domain.data.xml
* exportData 是否导出导入数据,默认不操作数据
  
* jdbcDriver,jdbcUrl,jdbcUser,jdbcPassword 配置导入导出数据库,必须参数
  
## 3.执行goal
```shell
#导出数据库中 schema到xml
mvn ddlutils:generate-domain-xml

#xml 导入到数据库
mvn ddlutils:domain-xml-2database

```

[1]: https://github.com/rubenqba/ddlutils

