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
public class BmiCalculateDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 
		Map<String, Object> processVariables = new HashMap(); // store all data here
		// fill the message; use new names
		processVariables = execution.getVariables(); //get all data 
		 
		long alter = calculateAge( convertToLocalDate((Date)execution.getVariable("gb")),LocalDate.now()); //calculate the age 
		long bmi =   Math.round(  (long)execution.getVariable("gewicht")   / (Math.pow((long)execution.getVariable("gr")/100.0,2)  ) ); //calculate bmi
		long risk = 0 ;	//default riskfaktor is 0
		
		if (execution.getVariable("k3").toString().trim() != "" ) {		//if something is written in k3 field set risk 3
			risk = 3;
		}else if (execution.getVariable("k2").toString().trim() != "" ) {	// else look if something is written in k2 field and set risk 2
			risk = 2;
		}else if(execution.getVariable("k1").toString().trim() != "" ) { //else look if something is written in k1 field and set risk 1
			risk = 1 ;
		}
		
		
		execution.setVariable("malter", alter); //add alter to pool
		execution.setVariable("mbmi", bmi);	//add bmi to pool
		execution.setVariable("mrisk", risk);//add risk to pool
		
 
	 
	}

	//converts a date to a localdate
	public LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	//get alter 
	 public static long calculateAge(LocalDate birthDate, LocalDate currentDate) {
	        if ((birthDate != null) && (currentDate != null)) {
	            return  Period.between(birthDate, currentDate).getYears();
	        } else {
	            return 0;
	        }
	    }
}

