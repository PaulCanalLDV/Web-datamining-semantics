package applications;

import com.hp.hpl.jena.rdf.model.Model;
import tools.JenaEngine;


public class Person {
	public static void main(String[] args) {
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/family.owl");
		if (model != null) {
			//lire le Namespace de l'ontologie
			NS = model.getNsPrefixURI("");		
			//apply owl rules on the model
			Model owlInferencedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/owlrules.txt");
			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(owlInferencedModel, "data/rules.txt");
				        
			// code here 
			System.out.println("ok");
			String personne = "Paul";
			
			// query on the model after inference
			System.out.println(JenaEngine.executeQuery(inferedModel,
					" PREFIX ns: <http://www.owl-ontologies.com/unnamed.owl#>\r\n"
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
					+ "SELECT ?name \r\n"
					+ "WHERE {\r\n"
					+ "        ?per ns:isParentOf ns:Paul .\r\n"
					+ "        ?per ns:name ?name.\r\n"
					+ "}"));

		} else {
			System.out.println("Error when reading model from ontology");
		}
	}
}
