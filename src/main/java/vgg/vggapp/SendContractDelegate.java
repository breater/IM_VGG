package vgg.vggapp;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class SendContractDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
	 
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		Map<String, Object> processVariables = new HashMap(); // store all data here
		processVariables = execution.getVariables(); // get all variables in pool
		Map<String, Object> ContractData = new HashMap();	//just send data it needs to cause security with only necessary data
		String correlationId = (String) processVariables.get("correlationId"); //get correlation id 
		String name = (String) processVariables.get("name");	//get name
		String vorname = (String) processVariables.get("vorname"); //get vorname
		Date geburtstag = (Date) processVariables.get("gb");	//get gb
		String anschrift = (String) processVariables.get("anschrift");	//get anschrift
		long Beitrag = (long) processVariables.get("Beitrag");//get Beitrag
		Long risikobeitrag = (Long) processVariables.get("riskobeitrag"); //get riskiobeitrag
		String riskogrund = (String) processVariables.get("reason"); // get reason
		Boolean approve = true;	 // can only be true else this class doesnt get executed
			
		
		//fill with data needed 
		
		ContractData.put("name", name);  
		ContractData.put("vorname", vorname);
		ContractData.put("gb", geburtstag);
		ContractData.put("anschrift", anschrift);
		ContractData.put("createdate", new  Date());
		ContractData.put("approve",approve );	
		ContractData.put("Beitrag", Beitrag);
		ContractData.put("riskobeitrag", risikobeitrag);
		ContractData.put("Entscheidung", "versicherungsfähig");
		ContractData.put("reason", riskogrund);
		ContractData.put("correlationId",correlationId);

		
		//execution.setProcessBusinessKey(correlationId);
		// correlate process with messageid message and instanceID  
		runtimeService.createMessageCorrelation("replymsg").setVariables(ContractData).processInstanceBusinessKey(correlationId).correlate();
	
	}
}
