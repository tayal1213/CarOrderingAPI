Problem Statement
Build a simple application for the use case of car inventory management & sales.

The application shall cater to the below users & their requirements;

-            Buyers – The users should be able retrieve list of available cars and buy cars.

-            Admin – The users should be able to update stock. (Add or remove cars from the Inventory anytime)


Solution:

Application is built using Spring Boot H2 in memory Db, Spring security for roles
and JPA

Main Class: StartCarManagementApplication.java

Two InMemory users are define as mentioned below

       - atul 
        -jpmorgan
with roles user and admin respectively.
password for both is password and would be required to pass as Basic Auth Parameter while accessing API


ProductController.java has all the endpoints exposed

1. GET /products/avaiable       (Both atul and jpmorgan can access)
2. DELETE /products/remove/{id} (Only jpmorgan can access)
3. POST /products/add           (Only jpmorgan can access)
4. POST /product/buy            ( atul can access)
5. GET /orders                  (only jpmorgan can access)
6. GET /customers               (only jpmorgan can access)

During application load we have added below product

Product Name "Suzuki Swift"
Qty:5
Info: "Suzuki Car Model Swift Make 2010",
Status "ACTIVE" 

Sample Product Add request:

    {
        "productName": "BMW",
        "stockAvailble": 5,
        "productInfo": "BMW Car Model S  Make 2019",
        "productStatus": "ACTIVE"
    }

Sample Response:

[
{
"productId": 1,
"productName": "Suzuki Swift",
"stockAvailble": 5,
"productInfo": "Suzuki Car Model Swift Make 2010",
"productStatus": "ACTIVE"
},
{
"productId": 2,
"productName": "BMW",
"stockAvailble": 5,
"productInfo": "BMW Car Model S  Make 2019",
"productStatus": "ACTIVE"
}
]

Product Buy Request:

    {
        
        "productName": "BMW",
        "quantity": 1
    
    }


Get ALl Orders Sample response:

[
{
"orderId": 1,
"quantity": 1,
"orderDate": "2022-05-30T05:10:18.764+0000",
"orderStatus": null,
"productDetails": {
"productId": 2,
"productName": "BMW",
"stockAvailble": 4,
"productInfo": "BMW Car Model S  Make 2019",
"productStatus": "ACTIVE"
},
"customerInfo": {
"customerId": 1,
"name": "atul",
"address": "Singapore",
"emailAddress": "atul.tayal@gamil.com",
"created_ts": "2022-05-30T05:06:22.955+0000"
}
}
]



Please reach out to tayal1213@yahoo.com if any concern.