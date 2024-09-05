# Maven

## Cach 1 Build & Run bang `mvnw`
- Khi init project bang **Spring init** spring boot no add vao cho project
- File `mvnw` giup build project bang Maven

```shell
./mvnw package
```

```shell
./mvnw package -DskipTests
```

- Sau khi build xong file `.jar` se nam trong `target`
  - ten file `jar` nay chinh la `artifactId` + `version` trong file `pom`

- Chay project bang file `jar`
  - cd to `target\`
  ```shell
  java -jar .\identity-service-0.0.1-SNAPSHOT.jar
  ```

## Cach 2 Build & Run bang Maven tren may local
- Dau tien can cai Maven
- Khi **build** bang **Docker image** thi can dung truc tiep cau lenh cua Maven


