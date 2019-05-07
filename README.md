### OpenShift test project
#### run this locally
1. docker environment is required
2. run "mvn clean package; ./dockerbuild.sh" in "app" folder
3. use "sudo docker-compose up" to run the app and redis
4. use browser to visit port 80, for example: http://localhost:80/

#### Issues
- because of network restriction, unable to push image to docker hub. So use docker hub automatic build function to pull source code from github and build image.
- there's no way to do maven build before building image on docker hub, so the built jar is also committed to github.
- seems "spring.redis.port=${REDIS_PORT:6379}" it's not working on OpenShift, reports below error, default value 6379 is not recognized, needs to set Environment Variables instead. It works fine on local.
        Failed to bind properties under 'spring.redis.port' to int:
        Property: spring.redis.port
        Value: ${REDIS_PORT:6379}
        Origin: class path resource application.properties: 7:19
        Reason: failed to convert java.lang.String to int
