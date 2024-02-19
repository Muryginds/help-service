## Support Service
The Support Service is a web application designed to provide support phrases.
It allows users to retrieve random support phrases and add new support phrases to the system.

###
The objective of this project was to gradually implement various technologies in a step-by-step manner: 

<br><a href='https://github.com/Muryginds/help-service/pull/1'>`Spring 1`</a> - 
Simple web service with servlet with in-memory storage compiling war archive deploying on tomcat
<br><a href='https://github.com/Muryginds/help-service/pull/2'>`Spring 2`</a> - 
Self-made dispatcher servlet and application context + logging using proxy pattern
<br><a href='https://github.com/Muryginds/help-service/pull/3'>`Spring 3`</a> -
Spring boot + in-memory message broker implemented as external library + BeanPostProcessor configuration

### Endpoints
The service exposes the following endpoints:

- `GET /api/v1/support: Retrieve a random support phrase`
- `POST /api/v1/support: Add a new support phrase to the system`
