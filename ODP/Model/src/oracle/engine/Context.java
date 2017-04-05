package oracle.engine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Context {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private JSONObject jsonRequestObject;
	private JSONObject jsonResponseObject;
	private String serviceName = null;
	private String operation = null;
	private String errorMessage ="";
	private String principle;
	
	private boolean isAuthorized = false;
	
	public boolean getIsAuthorized(){
		return isAuthorized;
	}
	
	public Context(HttpServletRequest request,HttpServletResponse response){
		
		this.request = request;	
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public JSONObject getJsonRequestObject() {
		return jsonRequestObject;
	}

	public void setJsonRequestObject(JSONObject jsonRequestObject) {
		this.jsonRequestObject = jsonRequestObject;
	}

	public JSONObject getJsonResponseObject() {
		return jsonResponseObject;
	}

	public void setJsonResponseObject(JSONObject jsonResponseObject) {
		this.jsonResponseObject = jsonResponseObject;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}	
}
