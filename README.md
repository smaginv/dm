### Spring Boot, Spring Data, Spring Security, PostgreSQL, Ehcache, MapStruct, Lombok, OpenAPI, Apache Kafka

### REST API Simple debt manager

### The following components are required to run this application:

**1. PostgreSQL**

**2. Apache Kafka (bootstrap.servers: "localhost:9092")**

### Application launch:

**1. Clone the repository**

```bash
git clone https://github.com/smaginv/dm.git
```

**2. Create PostgreSQL database**

```bash
CREATE DATABASE dm;
CREATE DATABASE lm;
```

**3. Change the PostgreSQL username and password in the application.yml files**

**4. Run "schema.sql" and "data.sql" into lm module**

**4. Run the app using maven**

```bash
cd dm/dm
mvn spring-boot:run
cd ../lm
mvn spring-boot:run
```

**dm module (debt manager) start running at context path:**  
http://localhost:8081/api

**Swagger/OpenAPI Specification:**  
http://localhost:8081/api/swagger-ui/index.html

---

**lm module (log manager) start running at context path:**  
http://localhost:8082/api

**Swagger/OpenAPI Specification:**  
http://localhost:8082/api/swagger-ui/index.html