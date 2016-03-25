package org.neo4j.examples.server;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class CreateSimpleGraph {

    /**
     * @param args the command line arguments
     */
    
    private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
    
    private static final String user = "myUsername";
    private static final String pass = "myPassword";
    
    public static void main( String[] args )
    {
        WebTarget target = testDatabaseAuthentication();

        // START SNIPPET: nodesAndProps
        URI firstNode = createNode( target );
        addProperty( target, firstNode, "name", "Joe Strummer" );
        URI secondNode = createNode( target );
        addProperty( target, secondNode, "band", "The Clash" );
        // END SNIPPET: nodesAndProps

        // START SNIPPET: addRel
        URI relationshipUri = addRelationship( target, firstNode, secondNode, "singer",
        "{ \"from\" : \"1976\", \"until\" : \"1986\" }" );
        // END SNIPPET: addRel

        // START SNIPPET: addMetaToRel
        addMetadataToProperty( target, relationshipUri, "stars", "5" );
        // END SNIPPET: addMetaToRel

        // START SNIPPET: queryForSingers
        findSingersInBands( target, firstNode );
        // END SNIPPET: queryForSingers

        sendTransactionalCypherQuery( target, "MATCH (n) WHERE has(n.name) RETURN n.name AS name" );
    }

    private static void sendTransactionalCypherQuery( WebTarget target, String query ) {
        // START SNIPPET: queryAllNodes
        final String txUri = "transaction/commit";
        
        String payload = "{\"statements\" : [ {\"statement\" : \"" + query + "\"} ]}";
        
        Response response = target
                .path(txUri)
                .request(MediaType.APPLICATION_JSON)
                .header("application/xml", "true")
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON_TYPE));
                
        String entity = response.readEntity(String.class);

        System.out.println( String.format(
                "GET, status code [%d], returned data: "
                        + System.getProperty( "line.separator" ) + "%s",
                response.getStatus(), entity ) );

        response.close();
        // END SNIPPET: queryAllNodes
    }
    
    private static void findSingersInBands( WebTarget target, URI startNode )
            //throws URISyntaxException
    {
        // START SNIPPET: traversalDesc
        // TraversalDefinition turns into JSON to send to the Server
        TraversalDefinition t = new TraversalDefinition();
        t.setOrder( TraversalDefinition.DEPTH_FIRST );
        t.setUniqueness( TraversalDefinition.NODE );
        t.setMaxDepth( 10 );
        t.setReturnFilter( TraversalDefinition.ALL );
        t.setRelationships( new Relation( "singer", Relation.OUT ) );
        // END SNIPPET: traversalDesc

        // START SNIPPET: traverse
        //URI traverserUri = new URI( startNode.toString() + "/traverse/node" );
        String auxPath = extractPathFromNode( startNode );
        String traverserUri = auxPath + "/traverse/node";
        String jsonTraverserPayload = t.toJson();
        
        // POST {} to the node entry point URI
        Response response = target
                .path(traverserUri)
                .request(MediaType.APPLICATION_JSON)
                .header("application/xml", "true")
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonTraverserPayload, MediaType.APPLICATION_JSON_TYPE));
        
        System.out.println( String.format(
                "POST [%s] to [%s], status code [%d], returned data: "
                        + System.getProperty( "line.separator" ) + "%s",
                jsonTraverserPayload, traverserUri, response.getStatus(),
                response.getEntity().toString() ) );
        
        response.close();
        // END SNIPPET: traverse
    }
    
    // START SNIPPET: insideAddMetaToProp
    private static void addMetadataToProperty( WebTarget target, URI relationshipUri,
            String name, String value ) //throws URISyntaxException
    {
        //URI propertyUri = new URI( relationshipUri.toString() + "/properties" );
        String auxPath = extractPathFromNode( relationshipUri );
        String propertyUri = auxPath + "/properties";
        String entity = toJsonNameValuePairCollection( name, value );
        
        Response response = target
                .path(propertyUri)
                .request(MediaType.APPLICATION_JSON)
                .header("application/xml", "true")
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));
        
        System.out.println( String.format(
                "PUT [%s] to [%s], status code [%d]", entity, propertyUri,
                response.getStatus() ) );
        
        response.close();
    }
    // END SNIPPET: insideAddMetaToProp
    
    private static String toJsonNameValuePairCollection( String name,
            String value )
    {
        return String.format( "{ \"%s\" : \"%s\" }", name, value );
    }
    
    private static String extractPathFromNode( URI node ) {
        String auxUri = node.toString();
        return auxUri.replace(SERVER_ROOT_URI, "");
    }

    private static URI createNode( WebTarget target )
    {
        // START SNIPPET: createNode
        final String nodeEntryPointUri = "node";
        
        // POST {} to the node entry point URI
        Response response = target
                .path(nodeEntryPointUri)
                .request(MediaType.APPLICATION_JSON)
                .header("application/xml", "true")
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity("{}", MediaType.APPLICATION_JSON_TYPE));

        final URI location = response.getLocation();
        System.out.println( String.format(
            "POST to [%s], status code [%d], location header [%s]",
            nodeEntryPointUri, response.getStatus(), location.toString() ) );
        
        response.close();

        return location;
        // END SNIPPET: createNode
    }

    // START SNIPPET: insideAddRel
    private static URI addRelationship( WebTarget target, URI startNode, URI endNode,
            String relationshipType, String jsonAttributes )
            //throws URISyntaxException
    {
        //URI fromUri = new URI( startNode.toString() + "/relationships" );
        String auxPath = extractPathFromNode( startNode );
        String fromUri = auxPath + "/relationships";
        String relationshipJson = generateJsonRelationship( endNode,
                relationshipType, jsonAttributes );

        // POST {} to the node entry point URI
        Response response = target
                .path(fromUri)
                .request(MediaType.APPLICATION_JSON)
                .header("application/xml", "true")
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(relationshipJson, MediaType.APPLICATION_JSON_TYPE));
        
        final URI location = response.getLocation();
        System.out.println( String.format(
                "POST to [%s], status code [%d], location header [%s]",
                fromUri, response.getStatus(), location.toString() ) );

        response.close();
        return location;
    }
    // END SNIPPET: insideAddRel

    private static String generateJsonRelationship( URI endNode,
            String relationshipType, String... jsonAttributes )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "{ \"to\" : \"" );
        sb.append( endNode.toString() );
        sb.append( "\", " );

        sb.append( "\"type\" : \"" );
        sb.append( relationshipType );
        if ( jsonAttributes == null || jsonAttributes.length < 1 )
        {
            sb.append( "\"" );
        }
        else
        {
            sb.append( "\", \"data\" : " );
            for ( int i = 0; i < jsonAttributes.length; i++ )
            {
                sb.append( jsonAttributes[i] );
                if ( i < jsonAttributes.length - 1 )
                { // Miss off the final comma
                    sb.append( ", " );
                }
            }
        }

        sb.append( " }" );
        return sb.toString();
    }

    private static void addProperty( WebTarget target, URI nodeUri, String propertyName,
            String propertyValue )
    {
        // START SNIPPET: addProp
        String auxPath = extractPathFromNode( nodeUri );
        String propertyUri = auxPath + "/properties/" + propertyName;
        // http://localhost:7474/db/data/node/{node_id}/properties/{property_name}

        Response response = target
                .path(propertyUri)
                .request(MediaType.APPLICATION_JSON)
                .header("application/xml", "true")
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity("\"" + propertyValue + "\"", MediaType.APPLICATION_JSON_TYPE));
        
        System.out.println( String.format( "PUT to [%s], status code [%d]",
                propertyUri, response.getStatus() ) );
        
        response.close();
        // END SNIPPET: addProp
    }
    
    public static WebTarget testDatabaseAuthentication()
    {
        // START SNIPPET: testAuthentication
        Client client = ClientBuilder.newClient();

        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(user, pass);
        client.register(authFeature);

        WebTarget target = client.target(SERVER_ROOT_URI);
        
        Response response = target
                .request()
                .header("application/xml", "true")
                .get();
        
        String entity = response.readEntity(String.class);

        System.out.println( String.format(
                "GET, status code [%d], returned data: "
                        + System.getProperty( "line.separator" ) + "%s",
                response.getStatus(), entity ) );

        response.close();
        return target;
        // END SNIPPET: testAuthentication
    }
}
