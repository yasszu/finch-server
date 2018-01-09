# REST APIs with Finagle
## Getting Started

1. Create a Database (MySQL)  
2. Start server

    ``` 
    $ sbt 'run-main app.Server -db.name=dbname -db.user=username -db.password=password'
    ```

## Requests     

### GET /user/:id

```
$ curl -i -X GET http://localhost:8080/user/:id
```

### GET /users

```
$ curl -i -X GET http://localhost:8080/users
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