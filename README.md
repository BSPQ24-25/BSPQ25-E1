# BSPQ25-E1

## Important Links
- GitHub Pages: https://bspq24-25.github.io/BSPQ25-E1/
- Web: https://bspq25-e1.onrender.com

## Team Members

- Ane Altuna  
- Iker Ruesgas  
- Jon Lasa  
- Izaro Serrano  
- Isaac Herbozo  
- Ibon Castro  

## 📦 Project Overview

BSPQ25-E1 is a Spring Boot-based application developed as part of the BSPQ25 course project. It features clean architecture, unit and integration testing, logging, documentation, performance profiling, and continuous integration automation using GitHub Actions.

## 🚀 How to Run the Application

Open a terminal and navigate to the root folder of the project. Then run the following command to start the Spring Boot server:

```bash
mvn spring-boot:run
```

Once the application starts, open your web browser and go to: http://localhost:7777  
The application should now be running and accessible.

## ✅ Running the Tests

This project includes three types of tests: unit, integration, and performance. Use the following commands depending on the test type:

Unit Tests: 80 % coverage according to JaCoCo
```bash
mvn test
```


Integration Tests:  
```bash
mvn clean verify -Pintegration
```

Performance Tests:  
```bash
mvn clean verify -Pperformance
```

Ensure that the appropriate Maven profiles (integration, performance) are defined in the pom.xml.

## 🧰 Technologies Used

- Spring Boot – Web application framework
- Supabase - Cloud-based database (PostgreSQL)
- Mockito – Mocking framework for unit testing  
- JUnit – Unit testing framework  
- JaCoCo – Code coverage analysis  
- Log4j – Logging framework  
- Doxygen – Source code documentation generator  
- Docker – Containerization platform  
- GitHub Actions – Continuous integration and automation
