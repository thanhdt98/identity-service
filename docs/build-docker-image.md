# Build image docker
## Dockerfile sample 
```dockerfile
# Stage 1: build
# Start with a Maven image that includes JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with maven
RUN mvn package -DskipTests

#Stage 2: create image
# Start with Amazon Correto JDK 21
FROM amazoncorretto:21.0.4

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Giai thich
### Stage 1 build
- `FROM maven:3.9.8-amazoncorretto-21 AS build` 
  - Can image `maven` da cai san `java` do project dung **java 21** nen o day dung image `maven:3.9.8-amazoncorretto-21`
  - Search in **Docker Hub** `https://hub.docker.com/_/maven/tags`
    - **Image cua OpenJDK da ngung support nen khuyen cao dung cua amazoncorretto**
  - Hoac co the dung 1 Unix don thuan de cai maven len do va su dung
  - `AS build` la dat ten cho image do la `build`
- `WORKDIR /app`
  - tao thu muc va chuyen `thu muc hien tai` ve `/app`
    - `thu muc hien tai` nam trong image  `maven:3.9.8-amazoncorretto-21`
- `COPY pom.xml .`
  - copy file `pom.xml` vao folder `/app`
  - khi lenh nay dc chay no tim file `pom.xml` cung cap voi file `Dockerfile` de copy
- `COPY src ./src`
  - copy toan bo tu folder `src` vao folder `/app/src`
  - khi lenh nay dc chay no tim folder `src` cung cap voi file `Dockerfile` de copy
- Chi can file `pom.xml` va folder `src` la co the build
- `RUN mvn package -DskipTests`
  - Lenh build bang maven

### Stage 2 create image
**Lay 1 image khac de dong goi ung dung vao image do de chay**
**Note**
- Hien tai 1 so devOps dung thang image `maven:3.9.8-amazoncorretto-21` de dung lam image dong goi de chay
  - Tuy nhien co 1 so diem khong tot:
    - trong image do da co maven, ma khi chay ung dung chung ta khong can maven
    - trong image don con co rat nhieu file tam/ du thua nen khi build image se nang gay lang phi

**Step by Step**
- `FROM amazoncorretto:21.0.4`
  - Lay image chua java 21
- `WORKDIR /app`
  - tao folder `/app` trong image `amazoncorretto:21.0.4`
- `COPY --from=build /app/target/*.jar app.jar`
  - copy tu `build` file `/app/target/*.jar` vao thu muc `app` cua image `amazoncorretto:21.0.4` va doi ten thanh `app.jar`
- `ENTRYPOINT ["java", "-jar", "app.jar"]`
  - tao chi dan cho docker biet de chay ung dung
  - tuong ung voi lenh `java -jar app.jar`
  - neu can them param thi co the them

---
## Build image by Dockerfile
```shell
docker build -t identity-service:0.0.1 .
```

## Run
```shell
sudo docker run --name identity-service -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://172.17.0.3:3306/identity_service identity-service:0.0.1
```
### giai thich
- `-e DBMS_CONNECTION` la env environment da cai dat trong file `.properties`
- `172.17.0.3` la IP docker gan cho container khi run container
  - co the xem thong tin nay bang lenh
    - ```shell
        docker inspect [container-id]

  - **Tuy nhien IP nay co the thay doi theo moi lan run container nen khong thuan tien**
    - Docker co co che `network` co the xu ly duoc dieu nay
    - `Network` giong `DNS registry` vd giong nhu khi vao `google` khong can go thang IP ma chi can name
    - => co the truy cap vao container image bang ten ma k can IP
### tao Network
```shell
docker network create thanhxv-network
```
### Start MySQL in thanhxv-network
```shell
docker run --network thanhxv-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```
### Run your application in thanhxv-network
```shell
docker run --name identity-service --network thanhxv-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/identity_service identity-service:0.0.1
```
#### giai thich
- `-e DBMS_CONNECTION=jdbc:mysql://mysql:3306/identity_service`
  - `mysql` la ten container mysql duoc tao