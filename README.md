## RemoteJALSE
A Spring-based RESTful + WebSocket [JALSE](https://github.com/Ellzord/JALSE) server.

### Building and running with Gradle
1. Build using ```./gradlew build``` (see [Building Java Projects with Gradle](https://spring.io/guides/gs/gradle/#_build_your_project_with_gradle_wrapper))
2. Run using ```./gradle run``` (See [The Application Plugin](http://gradle.org/docs/current/userguide/application_plugin.html))

### Current Endpoints
* GET /jalse - lists all active JALSE instances
* POST /jalse/create - creates a new JALSE instance
* POST /jalse/delete - deletes a JALSE instance
* GET /jalse/{jalseID} - summary of JALSE instance
* GET /jalse/{jalseID}/{entityID} - summary of entity
* GET /jalse/{jalseID}/entities - lists top level entities
* GET /jalse/{jalseID}/{entityID}/entities - lists n level entities

### Possible future Endpoints
* POST /jalse/{jalseID}/entities/create - creates an entity
* POST /jalse/{jalseID}/entities/kill - kills an entity
* GET /jalse/{jalseID}/{entityID}/attributes - gets all attributes for an entity
* GET /jalse/{jalseID}/{entityID}/attribute?name=scary&type=boolean - gets specific attribute of type

### Common request body
```javascript
{
  "id": "63eee6a2-8900-4021-b905-bed7e6ed0419"
}
```

### More
See the [Wiki](https://github.com/Ellzord/JALSE/wiki) for more information.