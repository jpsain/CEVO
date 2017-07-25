package org.knoesis.cevo.process;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;
import org.knoesis.cevo.model.CEVOEvent;

public class EventTagger {
	
	
	public List<CEVOEvent> getCEVOEvents(String lstrVerb){
		
		List<CEVOEvent> lstCEVOEvents = new ArrayList<>();				
		
		CEVOEvent lobjCevoEvent;

		Model lobjModel = ModelFactory.createDefaultModel();
		// Prepare input stream for CEVO ontolot6gy
		InputStream lisCevoOnto = FileManager.get().open("org/knoesis/cevo/process/cevo-rdf.owl");
		
		if (lisCevoOnto == null) {
            throw new IllegalArgumentException( "Cevo ontology file not found");
        }
        // Read the RDF/XML file
		lobjModel.read(lisCevoOnto,null);
        
        String lstrInputToken = lstrVerb;
       
        // SPARQL query string
        String queryString = 
      		   "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
      		   "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
      		   "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
      		   "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
      		   "PREFIX cevo:<http://eventontology.org/#>"+
      		   "SELECT distinct ?event "+
      		   "WHERE { ?v rdf:type cevo:MainVerb. ?v rdf:type ?event. ?v rdfs:label ?vlabel. Filter (regex(?vlabel,\"" + "^" + lstrInputToken + "$" + "\", 'i' )). Filter (?event != cevo:MainVerb ). Filter (?event != owl:NamedIndividual ).}";
        
        Query lobjQuery = QueryFactory.create(queryString);
        // Execute the query and obtain results
		QueryExecution lobjQE = QueryExecutionFactory.create(lobjQuery, lobjModel);
		
		ResultSet lobjRS = lobjQE.execSelect();
		
		// Loop through the result set to extract Verb Class and URI
		for ( ; lobjRS.hasNext() ; )
	    {
			lobjCevoEvent = new CEVOEvent();
			QuerySolution soln = lobjRS.nextSolution() ;
	    
			RDFNode lobjRDFNode = soln.get("event") ;
			
			lobjCevoEvent.setEventLabel((String.valueOf(lobjRDFNode).split("#")[1]).replaceAll("_"," "));
			
			lobjCevoEvent.setEventURI(String.valueOf(lobjRDFNode));
	
			lstCEVOEvents.add(lobjCevoEvent);
			
			lobjCevoEvent = null;
	    }
		lobjQuery =null;
		lobjRS=null;
		lobjQE.close();
		lobjModel.close();
		// Return the result
		return lstCEVOEvents;
    		
		
	}

}
