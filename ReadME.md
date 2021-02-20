# Notification System Design

## Design Diagram
![Design Diagram](/images/System.png)

## TechStack
1. Springboot v2.4.3
2. Java 8
3. Redis
4. Postgres DB

## Read More About,
1. Springboot sleuth - https://spring.io/projects/spring-cloud-sleuth
2. Java 8 - https://openjdk.java.net/projects/jdk/8/
3. Redis - https://redis.io/
4. Postgres - https://www.postgresql.org/

## Project Setup

## Redis Download
```
1. Download redis from following link - https://redis.io/download
2. Extract the redis and set the path to Environment variables
3. Open the terminal and run - redis-server
```

## To start the application
```
1. git@github.com:NirakhRastogi/Notification-System.git
2. cd Notification-System
3. Run all the applications
```

# Endpoints
## To register a user
```
POST http://localhost:8080/register/${userId}

Request Body
{
    "clientId": "abcx",
    "rateLimit": 6
}
```

## To hit test controller
```
GET http://localhost:8080/ping

Headers
    x-api-clinet-id: "abcx"
    x-api-token: client-token
```