Categorise-Transactions

This is a spring boot app that requires maven and java 11. It also requires the lombok plugin to be installed with your chosen IDE and MongoDB to be running on the default port: 27017 and host: localhost

This app is also set up to use SonarQube for code quality reports. The coverage exclusions are listed in the pom.

Running the CategoriseTransactionApplication java file will deploy the app to the local server on port 8080.

The swagger documentation for this service can be viewed on the following link after the application is deployed locally:
http://localhost:8080/banking/swagger-ui.html#/

The architecture consists of one controller per api that all talk to their own individual service file where the business logic is carried out.

The Categorise Transactions Controller api will retrieve a hard coded list of transactions and store the data in MongoDB.
In future this data will be retrieved from another microservice.
At the start of each journey the useCache path param must be set to false so the data can initially be stored in the database. This can also be used if you want to reset the data to its original state.
This api wil automatically categorise transactions under 2 categories (Coffee Purchases and Amazon Purchases).

After this all subsequent apis can be used to manipulate the categories of all transactions. This includes:

Updating category of a transaction,
Adding a new category,
Getting the most up to date transaction list from MongoDB,
Getting a list of transactions for a specific category

At the start of each call the latest updated data is retrieved from MongoDB and at the end of each call the alterations are updated in MongoDB.

More details on the functionality of these apis can be found in the swagger documentation

This git repositiory is linked with the DevOps software Buddy and has a Dockerfile. Buddy has a CI/CD pipeline setup which automatically builds a docker image and pushed this to ECR in AWS so that its ready for deployment.

