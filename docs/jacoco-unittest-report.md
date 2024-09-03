# Jacoco for UnitTest report
## Add jacoco plugin configuration to pom.xml file
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <excludes>
<!--						<exclude>com/thanhxv/dto/**</exclude>-->
<!--						<exclude>com/thanhxv/entity/**</exclude>-->
<!--						<exclude>com/thanhxv/mapper/**</exclude>-->
<!--						<exclude>com/thanhxv/configuration/**</exclude>-->
        </excludes>
    </configuration>
</plugin>
```

## Execute report by Jacoco
### Execute by command line
- Chua cai Maven, On terminal, `cd [root project]`
```shell
cd ..
./mvnw test jacoco:report 
```

- Da cai Maven, On terminal, `cd [root project]`
```shell
cd ..
mvn test jacoco:report
```

- Chon maven => Lifecyle `test` + `Ctrl` + Plugins => jacoco => `jacoco:report`

## Result 
- Open on the browser `./target/site/index.html`
- Sample:

| identity-service          |                     |      |                 |      |        |      |        |       |        |         |        |         |
|---------------------------|---------------------|------|-----------------|------|--------|------|--------|-------|--------|---------|--------|---------|
| Element                   | Missed Instructions | Cov. | Missed Branches | Cov. | Missed | Cxty | Missed | Lines | Missed | Methods | Missed | Classes |
| com.thanhxv.dto.request   | 1,537180            | 10%  | 241             | 0%   | 250    | 284  | 43     | 60    | 129    | 163     | 14     | 18      |
| com.thanhxv.dto.response  | 87192               | 9%   | 134             | 0%   | 143    | 159  | 26     | 34    | 76     | 92      | 8      | 10      |
| com.thanhxv.service       | 486144              | 22%  | 14              | 12%  | 29     | 41   | 110    | 133   | 22     | 33      | 0      | 4       |
| com.thanhxv.mapper        | 27884               | 23%  | 293             | 9%   | 26     | 32   | 72     | 94    | 10     | 16      | 0      | 3       |
| com.thanhxv.entity        | 204142              | 41%  |                 | n/a  | 42     | 66   | 18     | 30    | 42     | 66      | 4      | 8       |
| com.thanhxv.configuration | 123119              | 49%  | 6               | 0%   | 7      | 18   | 36     | 60    | 4      | 15      | 0      | 4       |
| com.thanhxv.controller    | 12047               | 28%  |                 | n/a  | 15     | 23   | 39     | 50    | 15     | 23      | 0      | 4       |
| com.thanhxv.exception     | 86184               | 68%  |                 | 50%  | 7      | 17   | 25     | 65    | 6      | 16      | 0      | 3       |
| com.thanhxv               |                     | 37%  |                 | n/a  | 1      | 2    | 2      | 3     | 1      | 2       | 0      | 1       |
| com.thanhxv.validator     | 28                  | 90%  |                 | 50%  | 2      | 5    | 1      | 8     | 0      | 3       | 0      | 1       |
| com.thanhxv.enums         | 15                  | 100% |                 | n/a  | 0      | 1    | 0      | 3     | 0      | 1       | 0      | 1       |
| Total                     | 3,713 of 4,751      | 21%  | 427 of 436      | 2%   | 522    | 648  | 372    | 540   | 305    | 430     | 26     | 57      |

- Explain:
  - Missed Instructions & Cov. => UnitTest cover dc bao nhieu % code
  - Missed Branches & Cov. => Cac dieu kien re nhanh (if/else)
  - Missed & Cxty (Complexity) => Do do phuc tap cua code. Do phuc tap lien quan den dieu kien re nhanh (if/else switch/case) => Complexity cang thap cang tot
  - Lines => Line of code (chua format)
  - Methods => So method
  - Classes => So class

## Mot so class nhu DTO, Entity k can coverage thi co the `exclude`
```xml
  <configuration>
      <excludes>
          <exclude>com/thanhxv/dto/**</exclude>
          <exclude>com/thanhxv/entity/**</exclude>
          <exclude>com/thanhxv/mapper/**</exclude>
          <exclude>com/thanhxv/configuration/**</exclude>
      </excludes>
  </configuration>
```