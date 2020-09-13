# Hazelcast Client Server Topology
Sample application using Hazelcast client server topology. There are multiple instances of Hazelcast server which forms cluster. Spring boot application acts as a Hazelcast client and communicate with servers to store/retrieve cache.

### Implementation
Hazelcast client interacts with servers using following ways:
##### Spring Data Hazelcast
Interact with Hazelcast using Spring data access technologies. Refer [StockServiceImpl](client/src/main/java/com/example/client/service/StockServiceImpl.java) from more details.

##### Spring Cache Manager
Interact with Hazelcast using Spring cache manager. Refer [StockExchangeServiceImpl](client/src/main/java/com/example/client/service/StockExchangeServiceImpl.java) from more details.

### Run
##### Hazelcast Server
Run multiple instances of [Application](server/src/main/java/com/example/server/Application.java) to start Hazelcast Server

##### Hazelcast Client
Run [Application](client/src/main/java/com/example/client/Application.java) to start Hazelcast Client

### Test
Refer endpoints in [Postman Collections](postman-collection/Hazelcast.postman_collection.json)
