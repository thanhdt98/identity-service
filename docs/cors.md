# CORS
## Define
- Api chi dc goi tu 1 so domain nhat dinh
- Khi 1 trang web request toi server thi se thuc hien `preflight` truoc
  - Sau khi goi `preflight` api co method `OPTIONS` se nhan dc ket qua co cho phep `origin` (domain) dc phep truy cap hay khong ?
  - Neu chat che hon co the kiem tra ca endpoint, header
- Muc dich `cors` la bao ve cac trang web gia mao (gia mao giao dien) call api
- `CORS policy` chi co tren trinh duyet, Postman khong bi

## Implement
- Co the xu ly ca tren tang `gateway` hoac tang `service`

### Implement on service
- Su dung filter cua spring cung cap `CorsFilter`
- ```java
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

# Note
- **Can check filter authentication**