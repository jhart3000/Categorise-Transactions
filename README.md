Categorise-Transactions

This is a spring boot app that requires maven and java 11. It also requires the lombok pluging to be installed with your chosen IDE.

Running the FractalAssignmentApplication java file will deploy the app to the local server on port 8081.

The swagger documentation for this ap can be viewed on this link after the application is deployed locally:
http://localhost:8081/fractal/swagger-ui.html#/

Before hitting any apis in this service you must have created an app in the Fractal Developer Portal (https://developer.askfractal.com/).
You must then generate an auth token, create a company and then access a users bank account using the apis in the Developer Portal.

The architecture consits of one controller per api that all talk to the same service file where the buisness logic is carried out.
A common list object is created when the Categorise Transactions Controller and then all subsequent api calls modify this inital object. 
This means that the Categorise Transactions Controller api must be called before any other api.

The Categorise Transactions Controller api will retrieve data from the bank using the retrieve bank transactin api provided in the Developer Portal.
This means it will take in 3 headers (X-Api-Key, Authorization, X-Partner-Id) and 2 path params (bankId and accountId).
This api wil automatically categorise transactions under 2 categories (Coffee Purchases and Amazon Purchases).

After this all subsequent apis can be used to manipulate the categories of all transactions.

