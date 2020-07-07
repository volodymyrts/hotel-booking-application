# Hotel Booking Application

### Read task in Task.txt file

**Instruction:**

To run application install Maven, cd to application folder and execute command "mvn spring-boot:run" from command prompt.

Once the application (web service) is started, you cat send requests to http://localhost:8080/{endpoint} (you can use Postman for POST requests).

**List of available endpoints:**

1. GET /rooms/available/{dateIn}/{dateOut} - view list of available rooms for specified dates (date format example: 2018-03-01).
Example: http://localhost:8080/rooms/available/2018-03-02/2018-03-13

2. GET /rooms/category/{category_id} - view rooms filtered by category.
Example: http://localhost:8080/rooms/category/1
   GET /rooms/all - view list of all rooms.
Example: http://localhost:8080/rooms/all

3. POST /users/create - create new user.
Example: http://localhost:8080/users/create with JSON body:
```
JSON example:
{
    "name": "John2",
    "surname": "Doe2"
}
```

4. POST /bookings/create - book the room for specified days.
Example: http://localhost:8080/bookings/create with JSON body:
```
JSON example:
{
    "dateIn": "2018-03-06",
    "dateOut": "2018-03-08",
    "room": {
        "id": 2
    },
    "user": {
        "id": 1
    },
    "options": [
        {
            "id": 1
        }
    ]
}
```

5. GET /bookings/{user_id} - view user's bookings.
Example: http://localhost:8080/bookings/1

6. GET /bookings/price/{booking_id} - get total price of the booking (room for dates period + cost of additional options).
Example: http://localhost:8080/bookings/price/1

7. GET /bookings/all - view all bookings.
Example: http://localhost:8080/bookings/all

Additional endpoints:
8. GET /users/all - view list of all users.
Example: http://localhost:8080/users/all
   GET /users/{user_id} - get user by id.
Example: http://localhost:8080/users/1
   