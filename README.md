# Hazelcast Client Server Topology
Sample application using Hazelcast client server topology. There are multiple instances of Hazelcast server which forms cluster. Spring boot application acts as a Hazelcast client and communicate with servers to store/retrieve cache.

### Implementation
Hazelcast client interacts with servers using following ways:
##### Spring Data Hazelcast
Interact with Hazelcast using Spring data access technologies. Refer [StockServiceImpl](client/src/main/java/com/example/client/service/StockServiceImpl.java) for more details.

##### Spring Cache Manager
Interact with Hazelcast using Spring cache manager. Refer [StockExchangeServiceImpl](client/src/main/java/com/example/client/service/StockExchangeServiceImpl.java) for more details.
Refer [WeightFactorServiceImpl](client/src/main/java/com/example/client/service/WeightFactorServiceImpl.java) for asynchronous calculation using Completable Future. 

### Run
##### Hazelcast Management Center
Follow below steps to run Hazelcast Management Center:
1. Download Hazelcast Management Center zip file using this [link](https://download.hazelcast.com/management-center/hazelcast-management-center-3.12.8.zip)
1. Extract zip file and copy `hazelcast-mancenter-3.12.8.war` file to a folder called `hazelcast-mancenter`
1. Change directory to `hazelcast-mancenter`
1. Run `java -jar hazelcast-mancenter-3.12.8.war 8090 hazelcast-mancenter` command to start Hazelcast Management Center
1. Access Hazelcast Management Center using this [link](http://localhost:8090/hazelcast-mancenter)

##### Hazelcast Server
Run multiple instances of [Application](server/src/main/java/com/example/server/Application.java) to start Hazelcast Server

##### Hazelcast Client
Run [Application](client/src/main/java/com/example/client/Application.java) to start Hazelcast Client

### Test
Refer endpoints in [Postman Collections](postman-collection/Hazelcast.postman_collection.json)
