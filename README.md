## RemoteJALSE
A Spring-based RESTful + WebSocket [JALSE](https://github.com/Ellzord/JALSE) server.

### Building and running with Gradle
1. Build using ```./gradlew build``` (see [Building Java Projects with Gradle](https://spring.io/guides/gs/gradle/#_build_your_project_with_gradle_wrapper))
2. Run using ```./gradle run``` (See [The Application Plugin](http://gradle.org/docs/current/userguide/application_plugin.html))

### GET Endpoints
* GET /jalse - lists all active JALSE instances
* GET /jalse/entities - lists top level entities
* GET /jalse/summary - summary of JALSE/entity instance

### Common GET request parameters
JALSE:
```
?jalseID=63eee6a2-8900-4021-b905-bed7e6ed0419
```
Entity:
```
jalseID=63eee6a2-8900-4021-b905-bed7e6ed0419&entityID=8bcb861b-2f99-4079-883f-972f4e2c1706
```

### POST Endpoints
* POST /jalse/new - creates a new JALSE instance
* POST /jalse/kill - deletes a JALSE instance
* POST /jalse/entities/new - creates an entity
* POST /jalse/entities/kill - kills an entity

### Common POST request bodies
JALSE:
```javascript
{
  "id": "63eee6a2-8900-4021-b905-bed7e6ed0419"
}
```
Entity:
```javascript
{
  "jalseID": "63eee6a2-8900-4021-b905-bed7e6ed0419",
  "id": "8bcb861b-2f99-4079-883f-972f4e2c1706"
}
```

### Possible future changes
* Entity types
* Attributes (Endpoint/streaming)
* Actions
* Testing

### More
See the [Wiki](https://github.com/Ellzord/JALSE/wiki) for more information.