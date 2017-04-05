package oracle.engine;

public interface IPipelineProcessorFactory {
	
	public IRequestProcessor createRequestProcessor(String processorName);
	
	public IResponseProcessor createResponseProcessor(String processorName);
}
