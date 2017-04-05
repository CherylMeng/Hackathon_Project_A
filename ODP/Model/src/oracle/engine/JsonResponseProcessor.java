package oracle.engine;

import java.io.IOException;

import org.apache.log4j.Logger;

 


public class JsonResponseProcessor implements IResponseProcessor {
	private static Logger logger = Logger
			.getLogger(JsonResponseProcessor.class);

	public void processContext(Context context) {

		String returnObjectString = null;

		if (context.getIsAuthorized() == false) {
			returnObjectString = "{\"returnCode\":\"-1077\",\"returnMsg\":\""
					+ "\"}";

		} else {
			if (context.getJsonResponseObject() != null)
				returnObjectString = context.getJsonResponseObject().toString();
		}

		try {
			context.getResponse().getWriter().print(returnObjectString);

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
