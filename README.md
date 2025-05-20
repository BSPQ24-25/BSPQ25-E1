# BSPQ25-E1

## Important Links
- [GitHub Pages](https://bspq24-25.github.io/BSPQ25-E1/)
- [Web](https://bspq25-e1.onrender.com)

## Documentation
Pages corresponding to documentation:
  1. [Technical documentation](https://bspq24-25.github.io/BSPQ25-E1/doxygen/html/) created with Doxygen
  2. [Test reports](https://bspq24-25.github.io/BSPQ25-E1/site/) created after running Unit, Performance and Integration tests and JaCoCo for coverage

## Team Members

- Ane Altuna  
- Iker Ruesgas  
- Jon Lasa  
- Izaro Serrano  
- Isaac Herbozo  
- Ibon Castro  

## 📦 Project Overview

BSPQ25-E1 is a Spring Boot-based application developed as part of the BSPQ25 course project. It features clean architecture, unit and integration testing, logging, documentation, performance profiling, and continuous integration automation using GitHub Actions.

## 📦 Burndown chart

Sprint 1 - March 13th -- March 30th

![image](https://github.com/user-attachments/assets/815fd5dc-53f4-41fd-ba59-b64d889ce22f)

Sprint 2 - April 1st -- April 27th

![image](https://github.com/user-attachments/assets/821403f8-5886-4937-94ac-9f74c7cbf224)

Sprint 3 - April 29th -- May 18th

![image](https://github.com/user-attachments/assets/843f52dc-3d0d-473f-b528-556f5f17fd9e)


## 🚀 How to Run the Application

Open a terminal and navigate to the root folder of the project. Then run the following command to start the Spring Boot server:

```bash
mvn spring-boot:run
```

Once the application starts, open your web browser and go to: http://localhost:8080
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
