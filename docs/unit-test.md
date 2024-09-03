## Neu trong method test lay thong tin dang nhap
```java
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
```
### Su dung annotation `@WithMockUser`
```java
@Test
@WithMockUser(username = "bravo")
void getMyInfo_userNotFound_error() {
```
- Dependency on `pom.xml`
  ```xml
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
