package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "hello_world",
	roleName = "hello_world-role",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig
public class HelloWorld implements RequestHandler<Object, Map<String, Object>> {

	public Map<String, Object> handleRequest(Object request, Context context) {
		Map<String,Object> mapRequst = (Map<String, Object>) request;

		String path = (String) mapRequst.get("rawPath");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if(path.equals("/hello")){
			resultMap.put("statusCode", 200);
			resultMap.put("message", "Hello from Lambda");
		} else {
			Map<String,Object> requestContext = (Map<String,Object>) mapRequst.get("requestContext");
			Map<String,Object> http = (Map<String,Object>) requestContext.get("http");
			String method = (String) http.get("method");
			resultMap.put("statusCode", 400);
			resultMap.put("message", "Bad request syntax or unsupported method. Request path: "+ path + ". HTTP method: " + method);
		}

	   return resultMap;
	}
}
