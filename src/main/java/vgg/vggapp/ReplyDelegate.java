package vgg.vggapp;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class ReplyDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
	 
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService(); // get runtime service
		Map<String, Object> processVariables = new HashMap(); // store all data of pool in 
		processVariables = execution.getVariables(); //get all data of process 
			
		String correlationId = (String) processVariables.get("correlationId"); // get instanceid
		
		
		//no overhead of Data ... so there is only necessary data in processVariables
		// correlate process with messageid message and instanceID  
		runtimeService.createMessageCorrelation("replymsg").setVariables(processVariables).processInstanceBusinessKey(correlationId).correlate();
	
	}
}
