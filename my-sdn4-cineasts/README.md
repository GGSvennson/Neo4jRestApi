![Cineasts.net Logo](https://github.com/jexp/cineasts/raw/master/cineasts.png)
Cineasts.net
============

Based on the original [sdn4-cineasts](https://github.com/neo4j-examples/sdn4-cineasts) web application from [Luanne Misquitta](https://github.com/luanne) I add some new features extending them to [Neo4j driver (REST API client) for Node.js](https://www.npmjs.com/package/neo4j) to initialize the webapp.

To run it, start your local Neo4j server on localhost:7474 providing a {username: 'neo4j', password: 'neo'} for your database, run `mvn install jetty:run`, and then execute `node app.js` from inside your application local folder conf.
