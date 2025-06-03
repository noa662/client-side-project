# Client Application - Inquiry Management System

## Overview
This is the client-side application for the Inquiry Management System.  
It allows users to create new inquiries, view their statuses, and interact with the server via a simple command-line interface.

## Features
- Open new inquiries (requests, questions, complaints)
- View status of existing inquiries
- Communicate with the server to send and receive inquiry data

## Requirements
- Java 11 or higher

## Installation and Running
1. Clone the repository:
```
git clone https://github.com/noa662/client-side-project.git
```
2. Build and run the application:
```
mvn clean install
java -jar target/client-side-project.jar
```

## Communication
The client connects to the server application using **Socket communication** for real-time management of inquiries.

## Links
- [Server Repository](https://github.com/noa662/server-side-project)
- [Client Repository](https://github.com/noa662/client-side-project)
