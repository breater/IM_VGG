package vgg.vggapp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RejectPoliceDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService(); //  get runtime service of pool
		Map<String, Object> processVariables = new HashMap(); //store process
		processVariables = execution.getVariables();	 // get all process variables 
														 
		String correlationId = (String) processVariables.get("correlationId");	 //correlation id
		//execution.setProcessBusinessKey(correlationId);
		// correlate process with messageid and message data and bussineskey
		runtimeService.createMessageCorrelation("rejectmsg").setVariables(processVariables).processInstanceBusinessKey(correlationId).correlate();
	}

}
