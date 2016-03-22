var http = require('http');
var neo4j = require('neo4j');

var db = new neo4j.GraphDatabase('http://neo4j:neo@localhost:7474');

db.cypher({
    query: 'MATCH (user:User {login: {login}}) RETURN user',
    params: {
        login: 'micha',
    },
}, callback);

function callback(err, results) {
    if (err) {
        throw err;
    }
    var result = results[0];
    if (!result) {
        console.log('Database not found,');
        console.log('creating database...');

        http.get('http://localhost:8080/database', (res) => {
            console.log(`Got response: ${res.statusCode}`);
            console.log('run http://localhost:8080');
            // consume response body
            res.resume();
        }).on('error', (e) => {
            console.log(`Got error: ${e.message}`);
        });


    } else {
        //var user = result['user'];
        //console.log(user);
        console.log('Database found...');
        console.log('run http://localhost:8080');
    }
};