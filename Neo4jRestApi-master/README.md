Neo4j and the REST API from Java
================================

Based on "The Neo4j v2.3.0-M01 Manual" manual under <a href="http://neo4j.com/docs/2.3.0-M01/server-java-rest-client-example.html" target="_blank">7.1 How to use the REST API from Java</a>, I've developed the same example program using Jersey library (2.19). The goal of this example is to use the REST API from Java when auth is required.

<a href="https://jersey.java.net/documentation/latest/client.html" target="_blank">Jersey Client API</a>

To utilize the client API it is first necessary to build an instance of a Client using one of the static ClientBuilder factory methods. Once you have a Client instance you can create a WebTarget from it. 
 
Client client = ClientBuilder.newClient();

WebTarget webTarget = client.target(http://example.com/rest/);

In this case we're using target(String uri) version. The uri passed to the method as a String is the URI of the targeted web resource.

Securing a Client (Http Authentication Support)
===============================================

Jersey supports Basic and Digest HTTP Authentication.

In order to enable http authentication support in Jersey client register the HttpAuthenticationFeature. This feature can provide both authentication methods, digest and basic. Feature can work in the following modes: 

• BASIC: Basic preemptive authentication. In preemptive mode the authentication information is send always with each HTTP request. This mode is more usual than the following non-preemptive mode (if you require BASIC authentication you will probably use this preemptive mode). This mode must be combined with usage of SSL/TLS as the password is send only BASE64 encoded. 

• BASIC NON-PREEMPTIVE:Basic non-preemptive authentication. In non-preemptive mode the authentication information is added only when server refuses the request with 401 status code and then the request is repeated with authentication information. This mode has negative impact on the performance. The advantage is that it does not send credentials when they are not needed. This mode must be combined with usage of SSL/TLS as the password is send only BASE64 encoded. 

• DIGEST: Http digest authentication. Does not require usage of SSL/TLS. 

• UNIVERSAL: Combination of basic and digest authentication. The feature works in non-preemptive mode which means that it sends requests without authentication information. If 401 status code is returned, the request is repeated and an appropriate authentication is used based on the authentication requested in the response (defined in WWW-Authenticate HTTP header. The feature remembers which authentication requests were successful for given URI and next time tries to preemptively authenticate against this URI with latest successful authentication method. 

To initialize the feature use static methods and builder of this feature. Example of building the feature in Basic authentication mode: 

HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("user", "superSecretPassword");

In this example program the "user" and "superSecretPassword" are those that you use to log onto the Neo4j server. So the code snippet would be placed as follows:

Client client = ClientBuilder.newClient();

HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic("user", "password");

client.register(authFeature);

WebTarget webTarget = client.target(http://example.com/rest/);

That's all!

You can see my blog on
<a href="http://thoughts-on-programming.blogspot.com.es/2015/06/how-to-use-rest-api-from-java.html" target="_blank">Google Blogger</a>
