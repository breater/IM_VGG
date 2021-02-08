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
		Map<String, Object> processVariables = new HashMap();
		// fill the message; use new names
		processVariables = execution.getVariables();
		try {
		long alter = calculateAge( convertToLocalDate((Date)execution.getVariable("gb")),LocalDate.now());
		long bmi =   Math.round(  (long)execution.getVariable("gewicht")   / (Math.pow((long)execution.getVariable("gr")/100.0,2)  ) );
		long risk = 0 ;
		if(execution.getVariable("k1").toString().trim() != "" ) {
			risk = 1 ;
		}else if (execution.getVariable("k2").toString().trim() != "" ) {
			risk = 2;
		}else if (execution.getVariable("k3").toString().trim() != "" ) {
			risk = 3;
		}
		
		processVariables.put("malter", alter);
		processVariables.put("mbmi", bmi);
		processVariables.put("mrisk",risk);
		}
		catch(ELException ex) {
			
		}
		catch(Exception ex) {
			
		}
		// set the correlation id to identify this in receiving process
		String correlationId = execution.getBusinessKey();
		if (correlationId == null) {			// if not set at process start
			correlationId = execution.getProcessInstanceId();
			execution.setProcessBusinessKey(correlationId);
		}
		processVariables.put("correlationId", correlationId);

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		// correlate process with message name
		runtimeService.startProcessInstanceByMessage("formularmsg", processVariables);
		 
	}

	public LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	 public static long calculateAge(LocalDate birthDate, LocalDate currentDate) {
	        if ((birthDate != null) && (currentDate != null)) {
	            return  Period.between(birthDate, currentDate).getYears();
	        } else {
	            return 0;
	        }
	    }
}
