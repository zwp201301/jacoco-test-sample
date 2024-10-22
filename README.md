## 1、加载javaagent
先编译，然后启动脚本：
```shell
java -javaagent:doc/jacocoagent.jar=includes=com.jacoco.*,output=tcpserver,port=7195,address=192.168.8.28,classdumpdir=classdumpdir/classes/ \
-jar target/jacoco-test-sample.jar
```

## 2、使用jacoco cli客户端
```shell
java -jar doc/jacococli.jar dump --address 172.27.3.242 --port 7195 --destfile target/jacoco.exec

java -jar doc/jacococli.jar report target/jacoco.exec --classfiles target/classes --sourcefiles src/main/java \
 --html target --xml target/site/jacoco/report.xml --csv target/site/jacoco/report.csv
```

## 3、使用maven-jacoco-plugin
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.3</version>
    <configuration>
        <address>172.27.3.242</address>
        <port>7195</port>
        <destFile>${project.build.directory}/jacoco.exec</destFile>
        <reset>false</reset>
        <append>true</append>
        <sourceEncoding>utf-8</sourceEncoding>
        <excludes>
            <exclude>com/jacoco/JacocoTestApplication.class</exclude>
        </excludes>
    </configuration>
    <executions>
        <execution>
            <id>dump</id>
            <goals>
                <goal>dump</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
            <configuration>
                <dataFile>${project.build.directory}/jacoco.exec</dataFile>
                <outputDirectory>${project.reporting.outputDirectory}/jacoco</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 4、ant集成jacoco
```shell
ant -buildfile doc/build.xml build
```

## 5、diff-cover增量覆盖统计
```shell
# 假设当前是测试分支test，要对比的分支是master。这里的origin/master是远程的master分支
diff-cover target/site/jacoco/report.xml --compare-branch origin/master --diff-range-notation .. --src-roots src/main/java \
  --html-report target/site/jacoco/diff.html
```