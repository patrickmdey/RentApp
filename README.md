# RentApp - Proyecto de Aplicación Web 2021
http://pawserver.it.itba.edu.ar/paw-2021b-3/

This repository contains a Java web application designed to function as a renting service, facilitating communication between two customers for renting transactions. The application is built using Java 1.8, Spring 4.2.5, PostgreSQL as the database backend, and Thymeleaf for the mailing system.

## Project Stages

### Stage 1: Spring MVC Setup with JDBC and JSTL

In the initial stage, the project was set up as a Spring MVC application. It utilized JDBC for database communication and JSTL for dynamic web page rendering. The main goals of this stage were:

- **Spring MVC:** Creation of a basic Spring MVC project structure.
- **JDBC:** Implementation of database connectivity and data manipulation.
- **JSTL:** Utilization of JSTL for rendering dynamic content on web pages.

### Stage 2: Migration to Hibernate and JPA

Building upon the foundation of the first stage, the project underwent an iteration where JDBC was replaced with Hibernate and JPA for improved database management. Key objectives of this stage included:

- **Hibernate and JPA Integration:** Integration of Hibernate and JPA for enhanced data persistence.
- **Database Migration:** Adaptation of existing data models to work seamlessly with Hibernate.
- **Refactoring:** Refinement of codebase to align with Hibernate and JPA best practices.

### Stage 3: Migration to API RESTful Architecture with Jersey and React.js + Bootstrap

In the final stage, the project transitioned into a RESTful API architecture using Jersey for backend API development. The frontend was developed using React.js along with the Bootstrap framework. Notable accomplishments of this stage encompassed:

- **API Migration:** Conversion of application logic into RESTful services using Jersey.
- **Frontend Development:** Creation of a responsive and user-friendly frontend using React.js and Bootstrap.
- **JWT Token Integration:** Incorporation of JWT token-based authentication for enhanced security.

## Additional Components and Tools

Throughout all stages of development, the following components and tools were utilized:

- **Spring Security:** Integration of Spring Security to enforce authentication and authorization measures.
- **SLF4j Logging:** Implementation of logging using the SLF4j framework to facilitate effective debugging and monitoring.
- **JUnit Testing:** Extensive use of JUnit for unit and integration testing to ensure robustness and reliability of the application.

This project showcases the evolution of a Java web application, starting from a basic Spring MVC setup and progressing to a sophisticated RESTful API architecture with frontend enhancements. It reflects the utilization of modern development practices and tools, resulting in a comprehensive and feature-rich renting service web application.
