## priceCrawler

This is a spring boot service. The service has various APIs that can help crawl Amazon(.com) and extract the SKU details. It uses PostgreSQL as database for storing data. The code uses Strategy Pattern inorder to support adding algorithms for new retailer like Walmart, Instacart etc.   

### How to run?

- Database used is PostgreSql. Install it in your machine.
- Create a database `commerce` with user `postgres` and password `postgres`
- Alternatively, you can change the configuration in `application.properties`
- In the terminal run `mvn spring-boot:run`.
- The server will be up and running. 


### APIs 

#### GetHtml
/products/html - GET

Query Params -
- sku = string
- url = string

```Response
{
   "timeTaken": "81 ms",
   "data": {html}
}
```

Example - http://localhost:7007/products/html?sku=B07Q7L6SRG 

---

#### GetProductDetails
/products - GET

Query Params -

- sku = string
- url = string

```
	Response
{
   "timeTaken": "81 ms",
   "data": {
       "id": "cd8ef413-6203-4dd7-b537-8bbcd0eeed7f",
       "sku": "B07Q7L6SRG",
       "title": "Sunfeast Mom's Magic Cashew and Almond, 600g",
       "description": "Mom's…..",
       "offerPrice": 72.0,
       "createdAt": "2021-05-09T22:22:33.803+00:00",
       "ratings": {
           "3star": "9%",
           "2star": "2%",
           "4star": "25%",
           "overallCount": "12,945",
           "5star": "60%",
           "1star": "3%"
       }
   }
}
```

Example - http://localhost:7007/products?sku=B07Q7L6SRG 

---

#### GetProductDetailsHistory
/products - GET

Query Params -
- sku = string
- url = string
- date=dd/MM/yyyy
	**Note** - you need to pass date 

```
	Response -
	{
   "id": "a5d62e3b-9c77-41bb-9cef-149ed816043f",
   "sku": "B07XMLWH7J",
   "title": "Kellogg's Corn Flakes Original, High in Iron, High in B Group Vitamins, Breakfast Cereals, 1.2 kg Pack",
   "description": "Don't compromise on morning nourishment when you have a balanced, great-tasting breakfast that is high in iron, vitamin b1, b2, b3, b6 and vitamin c, it is quick to eat and gives you energy even after 3 hours.",
   "offerPrice": 298.0,
   "createdAt": "2021-05-09T15:56:26.758+00:00",
   "ratings": {
           "3star": "9%",
           "2star": "2%",
           "4star": "25%",
           "overallCount": "12,945",
           "5star": "60%",
           "1star": "3%"
       }
}
```

Example - http://localhost:7007/products?sku=B07XMLWH7J&date=10/05/2021

---

#### List All crawled Products and details till now.
/products - GET

```
Response
{
   "timeTaken": "275 ms",
   "data": [
       {
           "id": "cd8ef413-6203-4dd7-b537-8bbcd0eeed7f",
           "sku": "B07Q7L6SRG",
           "title": "Sunfeast Mom's Magic Cashew and Almond, 600g",
           "description": "Mom's....",
           "offerPrice": 72.0,
           "createdAt": "2021-05-09T22:22:33.803+00:00",
           "ratings": {
               "3star": "9%",
               "2star": "2%",
               "4star": "25%",
               "overallCount": "12,945",
               "5star": "60%",
               "1star": "3%"
           }
       },
       {
           "id": "0b7d8f2f-d0b9-4573-a84d-1322b8a281de",
           "sku": "B07Q7L6SRG",
           "title": "Sunfeast Mom's Magic Cashew and Almond, 600g",
           "description": "Mom's....",
           "offerPrice": 72.0,
           "createdAt": "2021-05-09T22:57:30.968+00:00",
           "ratings": {
               "3star": "9%",
               "2star": "2%",
               "4star": "25%",
               "overallCount": "12,945",
               "5star": "60%",
               "1star": "3%"
           }
       }
   ]
}
```

Example - http://localhost:7007/products

---

#### PriceTrend
/products/trends

```
Response 
{
   "timeTaken": "85 ms",
   "data": [
       {
           "sku": "B07Q7L6SRG",
           "timestampPrices": [
               {
                   "timestamp": "2021-05-09T22:57:30.968+00:00",
                   "price": 72.0
               },
               {
                   "timestamp": "2021-05-09T22:22:33.803+00:00",
                   "price": 72.0
               }
           ]
       }
   ]
}
```

Example - http://localhost:7007/products/trends

