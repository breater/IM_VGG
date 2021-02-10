package vgg.vggapp;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class SendRejectionDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService(); //get runtime service of pool
		Map<String, Object> processVariables = new HashMap(); // store all data here
		Map<String, Object> RejectData = new HashMap(); //just send data it needs to cause security with only necessary data
		processVariables = execution.getVariables(); //get all data of process 
		String correlationId = (String) processVariables.get("correlationId"); //get correlationID
		String name = (String) processVariables.get("name");	//get name
		String vorname = (String) processVariables.get("vorname"); //get vorname
		Date geburtstag = (Date) processVariables.get("gb");	//get geburtstag
		String anschrift = (String) processVariables.get("anschrift");	//get anschrift
		String ablehnungsgrund = (String) processVariables.get("ablehung");	//get ablehnungsgrund
		Boolean approve = true;	 //can only be true else this class wouldnt be executed
		
		//fill data
		RejectData.put("name", name);
		RejectData.put("vorname", vorname);
		RejectData.put("gb", geburtstag);
		RejectData.put("anschrift", anschrift);
		RejectData.put("ablehung", ablehnungsgrund);
		RejectData.put("approve",approve );
		RejectData.put("Entscheidung", "nicht versicherungsfähig");
		RejectData.put("createdate", new  Date());
		RejectData.put("correlationId",correlationId);
		
		
		
		 
		// correlate process with messageid message and instanceID  
		runtimeService.createMessageCorrelation("replymsg").setVariables(RejectData).processInstanceBusinessKey(correlationId).correlate();
	
	}

}

