# Quarkus RESTEasy with CloudEvents

Run `mvn quarkus:dev`.

To make request:

``` shell script
curl -v "http://localhost:8080/api/fact" \
  -X POST \
  -H "Ce-Id: 536808d3-88be-4077-9d7a-a3f162705f79" \
  -H "ce-spEcversion: 1.0" \
  -H "ce-type: fact" \ 
  -H "ce-source: http://localhost" \                          
  -H "content-type: text/plain" \      
  -d "500";
```