package vgg.vggapp;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class BeitragsberechnungDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
	 
		 
		Map<String, Object> processVariables = new HashMap();  //create a Hashmap to store values from pool
		processVariables = execution.getVariables();	//get all data
		long riskobeitrag = 0 ; // create variable default 0
		execution.setVariable("Entscheidung", "versicherungsfähig"); // set Entscheidung Versicherungsfähig else this class wouldnt get executed
		long alter =  (long)processVariables.get("malter") ; //get how old user is
		if(processVariables.get("riskobeitrag") != null) //if risikobeitrag isnt empty then
			riskobeitrag = (long)processVariables.get("riskobeitrag"); //get risikobeitrag from pool and set it to risikobeitrag variable
		long beitrag =  alter > 11   ? (alter *10)+riskobeitrag  :110 + riskobeitrag; //if person is older than 11 calculate alter * 10 and add the risk value else just the default kid cost and add risk value   
		
		
		execution.setVariable("Beitrag", beitrag); // add calculated beitrag into pool
	 
		
	 
	}

}
