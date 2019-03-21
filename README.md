# Kafka demo
A simple project to demonstrate how a Kafka stream can replace an ETL.  

## The flow
1. Insert a couple of raw users with just a full name into a table.
2. Emit these users through a Kafka topic.
3. Listen to the topic and normalize the users splitting the full name into first and last names.
4. Insert the normalized users into a different table.

## Prerequisites
1. Java 8
2. Maven
3. Docker
4. (Optional) Python if you want to prettify *curl* output

## How to

To spin up this environment you will need to do the following:
1. Make sure the scrips are executable:  
```
chmod +x run-infra-env.sh  
chmod +x clean-infra-env.sh
```  

2. Install Maven dependencies for `source`, `processor` and `sink` applications  

3. Run the Kafka and MySql servers:  
```
./run-infra-env.sh  
```



4. After the containers are fully loaded first run the `source` application, then run the `sink` application  

5. To test them you need to make the following `HTTP` calls:  
```
curl http://localhost:38080/send  
curl http://localhost:38080/raw-users | python -m json.tool  
curl http://localhost:38082/users | python -m json.tool  
```

6. In order to clean up the environment use the following script:  
```
./clean-infra-env.sh  
```


The first *HTTP* call will the *ETL* which does 2 things:
 - it will insert raw users into a *staging* table
 - then it will emit the inserted raw users through a Kafka topic
 - the `sink` will listen to the message in the topic and will normalize the users and then insert them into a *foundation* table  

The second call will list the raw users from the *staging* table  

The third call will list the normalized users from the *foundation* table  


## TODO
1. Move the *normalization* functionality from the `sink` to the `processor`
2. Repurpose the `sink` to listen to a normalized users topic and emit through a reactive stream to a UI
