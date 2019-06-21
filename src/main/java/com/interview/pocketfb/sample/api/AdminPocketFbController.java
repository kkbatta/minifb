package com.interview.pocketfb.sample.api;

import java.util.Collection;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.pocketfb.UsersManager;
import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.error.ErrorCode;
import com.interview.pocketfb.sample.model.BaseResponse;
import com.interview.pocketfb.sample.model.User;
import com.interview.pocketfb.utils.BaseResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * Admin api will be used to manage and house the data.
 * THis will be developed as the time permits 
 * @author kk
 *
 */
@RestController
@RequestMapping("/admin")
@Api(value="Admin apis to manage overall customer requests w.r.t to friends and their relations. (W.I.P)", description="Operations pertaining to PockerFB Users management")

public class AdminPocketFbController {
	
	@ApiOperation(value = "Retrieves specific User's details. User details will be part of result tag in response.", response = BaseResponse.class)
	@RequestMapping("/user/{userid}")
	@ResponseBody
	public BaseResponse getUserDetails(@PathVariable("userid") String userId) {
		User user = UsersManager.INSTANCE.getUsers().get(userId);
		if(user != null) {
			return BaseResponseUtil.createSuccessResponse(user, "GetUserDetails by Admin");
		}else {
			return BaseResponseUtil.createErrorResponse(new User(),
					new CustomException("user not found", ErrorCode.EC_2001), "GetUserDetails by Admin");
		}
		
	}

	@ApiOperation(value = "Retrieves all Users details. List of User details will be part of result tag in response.", response = BaseResponse.class)
	@RequestMapping("/allusers")
	@ResponseBody
    public BaseResponse getAllUser() 
    {
		Collection<User> users = UsersManager.INSTANCE.getUsers().values();
		return BaseResponseUtil.createSuccessResponse(users, "getAllUsers");
    }
	
	@PostMapping("/details")
	@ResponseBody
    public User deleteUser(@ModelAttribute User user) 
    {
		User userResp = new User("123", "rwer", "qwerqwe", "qwer", "1234");
		return userResp;
    }

	
}
