package oracle.engine;

import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class JsonRequestProcessor implements IRequestProcessor {
	private static Logger logger = Logger.getLogger(JsonRequestProcessor.class);

	public void processContext(Context context) {

		JSONObject jsonRequest = new JSONObject();

		Enumeration<String> enumeration = context.getRequest()
				.getParameterNames();

		while (enumeration.hasMoreElements()) {
			try {
				// String parameterName = new
				// String(enumeration.nextElement().getBytes("iso-8859-1"),
				// "UTF-8");

				// String parameterValue = new
				// String(context.getRequest().getParameter(parameterName).getBytes("iso-8859-1"),
				// "UTF-8");

				String parameterName = new String(enumeration.nextElement());

				String parameterValue = new String(context.getRequest()
						.getParameter(parameterName));

				parameterValue = parameterValue.replaceAll("'", "&acute;");

				jsonRequest.put(parameterName, parameterValue);
			} catch (Exception ex) {
				// ex.printStackTrace();
				logger.error(ex);
			}
		}
		System.out.println("input:" + jsonRequest);
		context.setJsonRequestObject(jsonRequest);
	}
}
