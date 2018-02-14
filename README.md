# REST APIs with Finagle
## Getting Started

* Create a Database on MySQL

```sql                                             
CREATE SCHEMA IF NOT EXISTS `web_crawler` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

* Start server

``` 
$ sbt 'run-main app.Server'
```
    
## Deploy application
* Create a JAR file

```
$ sbt assembly

```

* Run process

```
$ java -jar target/scala-2.12/finch-server-assembly-1.0.jar &
```    

## Requests     

### GET /user/:id

```
$ curl -i -X GET 'http://localhost:8080/user/1'
```

### GET /users?page=0&count=3

```
$ curl -i -X GET 'http://localhost:8080/users?page=0&count=3'
```

### POST /user

```
$ curl -i -X POST http://localhost:8080/user -d '{"name":"test","email":"sample@test.com","comment":"testtesttest"}'
```

### PUT /user/:id

```
$ curl -i -X PUT \
  http://localhost:8080/user/:id \
  -H 'content-type: application/json' \
  -d '{"name":"ABC","email":"ABC@test.com","comment":"testtesttest"}'
```

### DELETE /user/:id

```
$ curl -i -X DELETE http://localhost:8080/user/:id
```
