# Example of RESTful API Server with [Finch](https://finagle.github.io/finch/)
- finatra example is [here](https://github.com/yasszu/finatra-server).
## Getting Started

1. Start a MySQL server  
    ```
    $ docker-compose up -d    
    ```

2. Start the API server

    ``` 
    $ sbt 'run-main app.ApiServer'
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

### POST /user

```
$ curl -i -X POST http://localhost:8080/user -d '{"name":"test","email":"sample@test.com","comment":"testtesttest"}'
```

### GET /user/:id

```
$ curl -i -X GET 'http://localhost:8080/user/1'
```

### GET /users?page=0&count=3

```
$ curl -i -X GET 'http://localhost:8080/users?page=0&count=3'
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
