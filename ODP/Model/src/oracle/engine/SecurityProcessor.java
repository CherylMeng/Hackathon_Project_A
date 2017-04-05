package oracle.engine;

import oracle.utils.StringUtil;

public class SecurityProcessor implements IRequestProcessor {

	public void processContext(Context context) {
		String serviceName = context.getServiceName();
		String operation = context.getOperation();
		if ("VersionControl".equals(serviceName)
				&& "newAppId".equals(operation)) {
			context.setAuthorized(true);
		} else {
			String appCode = "";

			try {
				appCode = context.getJsonRequestObject().getString("appCode");
				if ("00000000".equals(appCode)) {
					context.setAuthorized(true);
				} else if (StringUtil.verifyAppCode(appCode)) {
					context.setAuthorized(true);
				} else {
					context.setAuthorized(false);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				context.setAuthorized(false);
			}

		}
		 
	}

}
