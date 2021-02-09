package vgg.vggapp;
 
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.javax.el.ELException;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class SendAntragDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> processVariables = new HashMap(); //create a hash map to store data
		// fill the message; use new names
		processVariables = execution.getVariables();  //get all data from Process
 
		// set the correlation id to identify this in receiving process
		String correlationId = execution.getBusinessKey();  //get business key
		if (correlationId == null) {			// if no bussinesskey
			correlationId = execution.getProcessInstanceId(); // get process id
			execution.setProcessBusinessKey(correlationId);	//set  process id as businesskey 
		}
		processVariables.put("correlationId", correlationId);  // send correlation id ->  correlation id needed to communicate between 2 Process and make them one instance

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService(); //get runtime service
		// correlate process with messageid message and instanceID  
		runtimeService.startProcessInstanceByMessage("formularmsg", processVariables); //start systemprocess with my data
		 
	}

 
}
