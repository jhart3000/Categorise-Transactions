Categorise-Transactions

This is a spring boot app that requires maven and java 11. It also requires the lombok plugin to be installed with your chosen IDE.

Running the CategoriseTransactionApplication java file will deploy the app to the local server on port 8080.

The swagger documentation for this service can be viewed on the following link after the application is deployed locally:
http://localhost:8080/banking/swagger-ui.html#/

The architecture consists of one controller per api that all talk to the same service file where the business logic is carried out.
A common list object is created in the service layer when the Categorise Transactions Controller api is called and then all subsequent api calls modify this initial object. 
This means that the Categorise Transactions Controller api must be called before any other api.

The Categorise Transactions Controller api will retrieve a hard coded list of transactions.In future this data will be retrieved from another microservice.
This api wil automatically categorise transactions under 2 categories (Coffee Purchases and Amazon Purchases).

After this all subsequent apis can be used to manipulate the categories of all transactions. This includes:

Updating category of a transaction,
Adding a new category,
Getting the most up to date transaction list,
Getting a list of transactions for a specific category

More details on the functionality of these apis can be found in the swagger documentation

This git repositiory is linked with the DevOps software Buddy and has a Dockerfile. Buddy has a CI/CD pipeline setup which automatically builds a docker image and pushed this to ECR in AWS so that its ready for deployment.

