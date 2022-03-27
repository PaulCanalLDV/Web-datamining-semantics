package applications;

import java.util.Scanner;

import com.hp.hpl.jena.rdf.model.Model;
import tools.JenaEngine;

/**
 * @author DO.ITSUDPARIS
 */
public class Project {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/project.owl");
		if (model != null) {
			//lire le Namespace de l'ontologie
			NS = model.getNsPrefixURI("");
		//apply owl rules on the model
		Model owlInferencedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/owlrules.txt");
		// apply our rules on the owlInferencedModel
		Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(owlInferencedModel, "data/rules.txt");
		
		Scanner saisieUtilisateur = new Scanner(System.in);

		/*
		System.out.println("Bonjour, où souhaitez vous aller ? Donner les coordonnées du lieu");
		System.out.println("Latitude : ");
		float latitude = Float.parseFloat(saisieUtilisateur.nextLine());
		System.out.println("Longitude : ");
		float longitude = Float.parseFloat(saisieUtilisateur.nextLine());
		System.out.println("Latitude : " + latitude);
		System.out.println("Longitude : " + longitude);
		*/
		
		System.out.println("First we defines some SWRL rules to see the 'Big Stations', which are stations with a capacity greater than 40");
		System.out.println(JenaEngine.executeQueryFile(inferedModel,
				"data/query.txt"));

		System.out.println("Hello, what is your departure station ? Here is the list of our stations : ");
		System.out.println(JenaEngine.executeQuery(inferedModel,
				"PREFIX ns: <http://www.owl-ontologies.com/unnamed.owl#>\r\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "PREFIX tg:<http://www.turnguard.com/functions#>\r\n"
				+ "\r\n"
				+ "SELECT ?Station_names \r\n"
				+ "WHERE {\r\n"
				+ "	 ?stat a ns:Velib_Station .\r\n"
				+ "	?stat ns:name ?Station_names\r\n"
				+ "}"));
		
		String stationd = saisieUtilisateur.nextLine();
		System.out.println("You choose : " + stationd);
		
		System.out.println("The number of bike available at this station is : ");
		System.out.println(JenaEngine.executeQuery(inferedModel,"PREFIX ns: <http://www.owl-ontologies.com/unnamed.owl#>\r\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "PREFIX tg:<http://www.turnguard.com/functions#>\r\n"
				+ "\r\n"
				+ "SELECT ?nb_bike ?nb_elec_bike\r\n"
				+ "WHERE {\r\n"
				+ "	 ?stat a ns:Velib_Station .\r\n"
				+ "	?stat ns:name \""+stationd+"\" .\r\n"
				+ "	?stat ns:nb_bike_free ?nb_bike .\r\n"
				+ "	?stat ns:nb_elecbike_free ?nb_elec_bike .\r\n"
				+ "}"));


		System.out.println("Where do you want to go ? Again this is the list of the stations : ");
		System.out.println(JenaEngine.executeQuery(inferedModel,
				"PREFIX ns: <http://www.owl-ontologies.com/unnamed.owl#>\r\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "PREFIX tg:<http://www.turnguard.com/functions#>\r\n"
				+ "\r\n"
				+ "SELECT ?Station_names \r\n"
				+ "WHERE {\r\n"
				+ "	 ?stat a ns:Velib_Station .\r\n"
				+ "	?stat ns:name ?Station_names\r\n"
				+ "}"));
		
		String station = saisieUtilisateur.nextLine();
		System.out.println("You want to go to : " + station);

		System.out.println(String.format("?stat ns:name \"%s\" .\r\n", station));
		System.out.println("In this station, the number of borns available is : ");
		System.out.println(JenaEngine.executeQuery(inferedModel,"PREFIX ns: <http://www.owl-ontologies.com/unnamed.owl#>\r\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "PREFIX tg:<http://www.turnguard.com/functions#>\r\n"
				+ "\r\n"
				+ "SELECT ?nb_free_born\r\n"
				+ "WHERE {\r\n"
				+ "	 ?stat a ns:Velib_Station .\r\n"
				+ "?stat ns:name \""+station+"\" .\r\n"
				+ "	?stat ns:nb_born_free ?nb_free_born .\r\n"
				+ "}"));
		
		
		
		} else {
			System.out.println("Error when reading model from ontology");
		}
	}
}
