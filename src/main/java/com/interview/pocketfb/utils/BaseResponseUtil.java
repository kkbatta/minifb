package com.interview.pocketfb.utils;


import org.apache.commons.lang3.StringUtils;

import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.error.ErrorCode;
import com.interview.pocketfb.sample.model.BaseResponse;
import com.interview.pocketfb.sample.model.User;

public class BaseResponseUtil {

	
	public static BaseResponse createErrorResponse(User user, Exception e, String action) {
		BaseResponse resp = new BaseResponse();
		if(user != null) {
			resp.setResult(user);
		}
		if(e != null) {
			if(e instanceof CustomException) {
				CustomException ce = (CustomException) e;
				ErrorCode ec = ((CustomException) e).getErrorCode();
				resp.setErrorCode(ec);
				
				resp.setErrorMessage(e.getMessage()+". AdditionalInfo: Error occured during "+ (StringUtils.isNotEmpty(action)?action:""));
				
			}
		}
		return resp;
	}
	
	public static BaseResponse createSuccessResponse(Object obj,  String action) {
		BaseResponse resp = new BaseResponse();
		resp.setMsg(action);
		if(obj != null) {
			resp.setResult(obj);
		}
		return resp;
	}
	
}
