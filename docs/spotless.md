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