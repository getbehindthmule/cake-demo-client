This project can be build using gradle, for example

`./gradlew clean build`

It can then be run with the following command

`java -jar build/libs/cake-manager-client-0.1.0.jar`

The client will connect to the associated cake-manager server application and retreive the current list of cakes. It will
prompt the user to add a further cake. If no cake is required then hit RETURN to terminate the client.

Entering a cake name will prompt the user for further cake property information. When ready, the cake is pushed to the 
server and an upated list is displayed. The client then terminates

Remember to ensure the server application is running before the client is run.
