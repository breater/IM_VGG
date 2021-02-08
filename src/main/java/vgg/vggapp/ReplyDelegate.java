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
		// TODO Auto-generated method stub
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		Map<String, Object> processVariables = new HashMap();
		processVariables = execution.getVariables();
		if(!processVariables.containsKey("Entscheidung"))
				processVariables.put("Entscheindung", "");
		else
			if(processVariables.get("vable") != null)
				if(processVariables.get("vable").toString() == "no")
					processVariables.replace("Entscheidung", "nicht versicherungsfähig");
		String correlationId = (String) processVariables.get("correlationId");
		processVariables.put("createdate", new  Date());
		//execution.setProcessBusinessKey(correlationId);
		// correlate process with message name
		runtimeService.createMessageCorrelation("replymsg").setVariables(processVariables).processInstanceBusinessKey(correlationId).correlate();
	
	}

}
