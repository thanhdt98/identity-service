# Format code with Spotless
## Link
- https://github.com/diffplug/spotless

## How to setup on the project
### Setup plugin on `pom.xml`
```xml
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>${spotless.version}</version>
    <configuration>
        <java>
            <removeUnusedImports />
            <toggleOffOn/>
            <trimTrailingWhitespace/>
            <endWithNewline/>
            <indent>
                <tabs>true</tabs>
                <spacesPerTab>4</spacesPerTab>
            </indent>
            <palantirJavaFormat/>
            <importOrder>
                <!-- Specify either order or file, but not both -->
                <order>java,jakarta,org,com,com.diffplug,</order>
            </importOrder>
        </java>
    </configuration>
    <!--				<executions>-->
    <!--					<execution>-->
    <!--						<phase>compile</phase>-->
    <!--						<goals>-->
    <!--							<goal>check</goal>-->
    <!--						</goals>-->
    <!--					</execution>-->
    <!--				</executions>-->
</plugin>
```

## Giai thich
### `excution`
```shell
<executions>
    <execution>
        <phase>compile</phase>
        <goals>
            <goal>apply</goal>
        </goals>
    </execution>
</executions>
```
- Khi `compile` thi spotless se `apply` format 
- Co the su dung `check` de **cicd** kiem tra format truoc khi **build**

## Format
### By maven 
- Maven => Plugins => `spotless:check`
  - Co the su dung `spotless:check` trong pipeline neu Merge request chua format se tu choi merge
- `spotless:apply`

### By command line
```shell
mvn spotless:if [[ $? == 0 ]]; then
    echo "Succeed"
else
    echo "Failed"
fi
```

```shell
cd ..
mvn spotless:check
```

```shell
cd ..
mvn spotless:apply
```

## How to use in code
- Muon spotless khong format 1 doan nao do
```java
//spotless:off
log.info("khong muon cho spotless format");
//spotless:on
log.info("tiep tuc cho phep spotless format");
```