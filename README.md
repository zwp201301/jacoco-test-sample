## 1、加载javaagent
先编译，然后启动脚本：
```shell
java -javaagent:doc/jacocoagent.jar=includes=com.jacoco.*,output=tcpserver,port=7195,address=172.27.3.242,classdumpdir=classdumpdir/classes/ \
-jar target/jacoco-test-sample.jar
```

## 2、使用jacoco cli客户端
```shell
java -jar doc/jacococli.jar dump --address 172.27.3.242 --port 7195 \
  --destfile target/jacoco.exec

java -jar doc/jacococli.jar report target/jacoco.exec \
  --classfiles target/classes \
  --sourcefiles src/main/java \
  --html target \
  --xml target/site/jacoco/report.xml \
  --csv target/site/jacoco/report.csv
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
接下来三步走，即可得到测试覆盖率报告。
- mvn package
- mvn jacoco:dump
- mvn jacoco:report

## 4、ant集成jacoco
```shell
ant -buildfile doc/build.xml build
```

## 5、单元测试
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.3</version>
    <configuration>
        <excludes>
            <exclude>com/jacoco/JacocoTestApplication.class</exclude>
        </excludes>
    </configuration>
    <executions>
        <execution>
            <id>prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <!-- 强制要求覆盖率 -->
        <execution>
            <id>check-code-coverage</id>
            <phase>test</phase>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <rules>
                    <rule>
                        <element>BUNDLE</element>
                        <limits>
                            <limit>
                                <counter>INSTRUCTION</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.80</minimum> <!-- 至少80%的代码被执行 -->
                            </limit>
                            <!-- 可以添加更多的规则 -->
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 6、diff-cover增量覆盖统计
```shell
# 假设当前是测试分支test，要对比的分支是master。这里的origin/master是远程的master分支
diff-cover target/site/jacoco/report.xml --compare-branch origin/master --diff-range-notation .. --src-roots src/main/java \
  --html-report target/site/jacoco/diff.html
```

如果是docker容器运行diff-cover, 需要先将整个工程挂载到容器中。
```shell
docker pull twistersfury/diff-cover

# 一键运行
docker run --rm -v ../jacoco-test-sample:/app twistersfury/diff-cover \
  diff-cover target/site/jacoco/jacoco.xml --compare-branch test \
  --diff-range-notation .. --src-roots src/main/java \
  --html-report target/site/jacoco/diff.html
```
