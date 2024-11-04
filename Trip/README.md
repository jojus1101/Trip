# Joachim Justesen Documentation

## Todo
1. Opret givet database
2. Ændre DB name til givet database navn i config fil
3. Kør main
4. Fjern entititer i Hibernate.
5. Fjern entititer og dto'er til sidst

### 3.3.3
GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:43:51 GMT
Content-Type: application/json
Content-Length: 863

[
{
"id": 1,
"name": "Beach Holiday",
"price": 499.99,
"starttime": [
2024,
7,
1,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "LEISURE"
},
{
"id": 2,
"name": "Ski Holiday",
"price": 4999.99,
"starttime": [
2024,
2,
1,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "LEISURE"
},
{
"id": 3,
"name": "Beach Holiday",
"price": 299.99,
"starttime": [
2024,
7,
1,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "LEISURE"
},
{
"id": 4,
"name": "Beach Holiday",
"price": 500.99,
"starttime": [
2024,
10,
25,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "BUSINESS"
},
{
"id": 5,
"name": "Beach Holiday",
"price": 299.99,
"starttime": [
2024,
7,
1,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "CULTURAL"
},
{
"id": 6,
"name": "Tivoli",
"price": 100.0,
"starttime": [
2024,
7,
11,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "ADVENTURE"
}
]
Response file saved.


GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:45:13 GMT
Content-Type: application/json
Content-Length: 143

{
"id": 1,
"name": "Beach Holiday",
"price": 499.99,
"starttime": [
2024,
7,
1,
10,
0
],
"endtime": [
2024,
7,
15,
10,
0
],
"startposition": null,
"category": "LEISURE"
}


HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:07:45 GMT
Content-Type: application/json
Content-Length: 44

{
"message": "Database has been populated"
}

PUT http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:21:38 GMT
Content-Type: application/json
Content-Length: 150

{
"id": 1,
"name": "Beach Holiday",
"price": 500.99,
"starttime": "2024-10-25T10:00:00",
"endtime": "2024-10-30T10:00:00",
"startposition": null,
"category": "SEA"
}

DELETE http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:23:35 GMT
Content-Type: application/json
Content-Length: 25
{
"message": "Trip deleted"
}


### Task 8

GET http://localhost:7070/api/trips

HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:25:24 GMT
Content-Type: text/plain
Content-Length: 58

Unauthorized with roles: [user]. Needed roles are: [ADMIN]

GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:26:25 GMT
Content-Type: application/json
Content-Length: 904

[
{
"id": 1,
"name": "Beach Holiday",
"price": 499.99,
"starttime": "2024-07-01T10:00:00",
"endtime": "2024-07-15T10:00:00",
"startposition": null,
"category": "SEA"
},
{
"id": 2,
"name": "Ski Holiday",
"price": 4999.99,
"starttime": "2024-02-01T10:00:00",
"endtime": "2024-07-15T10:00:00",
"startposition": null,
"category": "SNOW"
},
{
"id": 3,
"name": "Beach Holiday",
"price": 299.99,
"starttime": "2024-07-01T10:00:00",
"endtime": "2024-07-15T10:00:00",
"startposition": null,
"category": "SEA"
},
{
"id": 4,
"name": "Beach Holiday",
"price": 500.99,
"starttime": "2024-10-25T10:00:00",
"endtime": "2024-07-15T10:00:00",
"startposition": null,
"category": "FOREST"
},
{
"id": 5,
"name": "Tivoli",
"price": 100.0,
"starttime": "2024-07-11T10:00:00",
"endtime": "2024-07-15T10:00:00",
"startposition": null,
"category": "CITY"
},
{
"id": 6,
"name": "Beach Holiday",
"price": 299.99,
"starttime": "2024-07-01T10:00:00",
"endtime": "2024-07-15T10:00:00",
"startposition": null,
"category": "CITY"
}
]

Now works logged in and admin.

3.3.5

In conclusion, while both PUT and POST could theoretically be used to add a guide to a trip, using PUT provides clear benefits in terms of idempotency, semantic clarity, and resource representation. Adopting this approach aligns with RESTful design principles, leading to a more robust and understandable API.