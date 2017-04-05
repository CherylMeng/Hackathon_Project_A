package oracle.engine;

import java.util.LinkedList;

import org.apache.log4j.Logger;


public class ServicePipeline {
	private static Logger logger = Logger.getLogger(ServicePipeline.class);
	Context context = null;
	
	LinkedList<IRequestProcessor> requestProcessors = new LinkedList<IRequestProcessor>();

	LinkedList<IResponseProcessor> responseProcessors = new LinkedList<IResponseProcessor>();

	public ServicePipeline(Context context) {
		this.context = context;
	}


	public void preparePipeline(){
		requestProcessors.add(new JsonRequestProcessor());
		requestProcessors.add(new ServiceNameRequestProcessor());
		requestProcessors.add(new SecurityProcessor());
		responseProcessors.add(new JsonResponseProcessor());
	}

	public void startProcessPipeline(){
		for(int i=0; i < requestProcessors.size(); ++i){
			requestProcessors.get(i).processContext(context);
		}
		
		IService service = null;
		service = ServiceManager.getService(context);
		try {
			if(context.getIsAuthorized())
			{
				service.execute(context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			context.setErrorMessage(context.getErrorMessage() + " " + (e.getMessage() != null?e.getMessage():e.getStackTrace()[0]));
		}
		
		for(int i=0; i < responseProcessors.size(); ++i){
			responseProcessors.get(i).processContext(context);
		}
	}
	
	public LinkedList<IRequestProcessor> getRequestProcessors() {
		return requestProcessors;
	}

	public LinkedList<IResponseProcessor> getResponseProcessors() {
		return responseProcessors;
	}
}
