## A quick java block chain prototype
### Features
- concise core java implementation without j2ee or any 3rd party components
- mine: to add a new block
- chain: to list all nodes in a chain
- register other nodes
- add new transactions
- synch chain with other nodes

### run it at local
```bash
# 0. Get into source code folder and Build
mvn clean intall
# 1. start 2 block chain nodes by using 2 diff ports
java -jar target/easy-blockchain-0.0.1.jar localhost 5678 4 richard1
java -jar target/easy-blockchain-0.0.1.jar localhost 5679 4 richard2

# 2. register each other
curl -X POST -H "Content-Type: application/json" -d '{nodes:[{address:localhost,port:5679}]}' "http://localhost:5678/nodes/register"
curl -X POST -H "Content-Type: application/json" -d '{nodes:[{address:localhost,port:5678}]}' "http://localhost:5679/nodes/register"


# 3. Mine and display it on 0.0.0.0:5678
curl -X GET -H "Content-Type: application/json" "http://localhost:5678/mine"
curl -X GET -H "Content-Type: application/json" "http://localhost:5678/chain"

# 4. Display then Resolve it on 0.0.0.0:5679
curl -X GET -H "Content-Type: application/json" "http://localhost:5679/chain"
curl -X GET -H "Content-Type: application/json" "http://localhost:5679/nodes/resolve"
curl -X GET -H "Content-Type: application/json" "http://localhost:5679/chain"

# 5. Add transaction on 0.0.0.0:5679
curl -X POST -H "Content-Type: application/json" -d '{sender:richard,recipient:KPMG,amount:5}' "http://localhost:5678/transaction"

# 6. Mine it on 0.0.0.0:5679
curl -X GET -H "Content-Type: application/json" "http://localhost:5679/mine"
curl -X GET -H "Content-Type: application/json" "http://localhost:5679/chain"

# 7. Resolve it on 0.0.0.0:5678
curl -X GET -H "Content-Type: application/json" "http://localhost:5678/chain"
curl -X GET -H "Content-Type: application/json" "http://localhost:5678/nodes/resolve"
curl -X GET -H "Content-Type: application/json" "http://localhost:5678/chain"

# 8. display blocks on both. They should display same chain
curl -X GET -H "Content-Type: application/json" "http://localhost:5678/chain"
curl -X GET -H "Content-Type: application/json" "http://localhost:5679/chain"

```

