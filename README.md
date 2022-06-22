# OpenWT Boat Challenge

Code by Gabriel Ineichen

## Run the code

To start the project, first run the frontend build and afterwards start the backend server,
which will serve the frontend static files and provide a REST API with wich the frontend communicates.

```
$> cd frontend
$> npm install && npm run build
$> cd ../backend
$> mvn spring-boot:run
```


## Frontend configuration
To change the location of the REST API the field `VITE_SERVER_URL` in the .env file can be adjusted.
After that the client must be recompiled.

## Backend configuration
The backend server can be configured with the entries in the application.properties file.
Any spring-boot specific configurations can be changed, for example server address and port.

The application has currently one admin user which can access the API.
The Username and password can also be specified.
The Default configuration is username admin and a randomly generated password,
which is stated at the start of the server.