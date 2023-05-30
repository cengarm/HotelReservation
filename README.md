# HotelReservation
This is a Hotel Reservation application. Developed using Spring Boot and Java
An application I made to improve myself on backend.
I would like to thank all my friends and teachers who helped me with this program.
Using this program, you can add a hotel and rent a room from the hotel. At the same time, you can get information about the hotel. You can see it's available.
### I will use this program as an example in all the programs I will make in the future. I dedicate myself to this work.
## SCREENSHOTS IN SWAGGER UI :
![image](https://github.com/cengarm/HotelReservation/assets/126611512/6b8c2c73-e634-4bd6-97d9-f7216fbb806b)
![image](https://github.com/cengarm/HotelReservation/assets/126611512/d47e53d0-b617-426e-9f44-ec54c0122240)
![image](https://github.com/cengarm/HotelReservation/assets/126611512/3acef979-a00d-451f-b709-cdf70b9685d6)
![image](https://github.com/cengarm/HotelReservation/assets/126611512/6387a343-5f9f-46ff-b566-3a2db753c5b5)

# SCREENSHOTS IN POSTRGRESQL : 
![image](https://github.com/cengarm/HotelReservation/assets/126611512/a723e9be-4609-4f31-8ee3-9a75e78bf980)
![image](https://github.com/cengarm/HotelReservation/assets/126611512/81b52f28-4ec8-4b7d-920e-2cc9655c2da8)

## DEPENDENCIES :

 <dependencies>
 
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.6.11</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>${project.parent.version}</version>
            <type>maven-plugin</type>
        </dependency>
        <dependency>
            <groupId>javax.validation</groupId>
            <artifactId>validation-api</artifactId>
            <version>2.0.1.Final</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>6.0.13.Final</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>javax.el</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.4.4</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
            <version>6.1.0</version>
        </dependency>
    </dependencies>
    
   ## RESOURCES :
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/HotelReservation
spring.datasource.username=postgres
spring.datasource.password=****
spring.jpa.properties.javax.persistence.validation.mode = none
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
server.forward-headers-strategy: framework
