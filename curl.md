//for windows users(cmd)

* create meal
curl -d "{\"dateTime\":\"2020-01-31T21:00:00\",\"description\":\"Второй ужин\",\"calories\":510}" -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals
  
* delete meal
curl -X DELETE http://localhost:8080/topjava/rest/meals/100011

* get all meals
curl -v http://localhost:8080/topjava/rest/meals

* get meal
curl -v http://localhost:8080/topjava/rest/meals/100006

* update meal
curl -d "{\"dateTime\":\"2020-01-31T21:00:00\",\"description\":\"Ужин\",\"calories\":510}" -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100005
  
* filter meals
curl -v "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=09:00&endDate=2020-01-30&endTime=19:00"