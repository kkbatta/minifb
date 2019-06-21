package com.interview.pocketfb.sample.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.pocketfb.UsersManager;
import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.error.ErrorCode;
import com.interview.pocketfb.sample.model.BaseResponse;
import com.interview.pocketfb.sample.model.User;
import com.interview.pocketfb.utils.BaseResponseUtil;
import com.interview.pocketfb.utils.GenericUtils;

@RestController
@RequestMapping("/user")
public class UserPocketFbController {
	private static final Logger log = (Logger) LogManager.getLogger(UserPocketFbController.class.getName());

	
	@RequestMapping("/{userid}")
	@ResponseBody
	public BaseResponse getUserDetails(@PathVariable("userid") String userId) {
		User user = UsersManager.INSTANCE.getUsers().get(userId);
		if(user != null) {
			User pUser = GenericUtils.getPublicData(user);
			return BaseResponseUtil.createSuccessResponse(pUser, "GetUserDetails by User");
		}else {
			return BaseResponseUtil.createErrorResponse(new User(),
					new CustomException("user not found", ErrorCode.EC_2001), "GetUserDetails by User");
		}
		
	}

	@PostMapping(path = "/register", produces = "application/json", consumes = "application/json")
	@ResponseBody
	public BaseResponse registerUserDetails(@RequestBody User user) {
		try {
			if (!GenericUtils.validateUser(user)) {

				throw new CustomException("Mandatory fields missing: name, useris, email, dob, pswd",
						ErrorCode.EC_2000);
			}
			User addedUser = UsersManager.INSTANCE.addUser(user);
			return BaseResponseUtil.createSuccessResponse(addedUser, "RegisterUser");
		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "RegisterUser");
		}

	}

	/**
	 * For now handling unregister with in user. Can talk about ideal way in
	 * interview
	 * 
	 * @param user
	 *            {userId and pswd are reguired in payload}
	 * @return
	 */
	@PostMapping(path = "/unregister", produces = "application/json", consumes = "application/json")
	@ResponseBody

	public BaseResponse unRegisterUserDetails(@RequestBody User user) {
		try {
			if (!GenericUtils.validateCredentials(user)) {

				throw new CustomException("Mandatory fields missing: name, useris, email, dob, pswd",
						ErrorCode.EC_2000);
			}
			User removedUser = UsersManager.INSTANCE.removeUser(user);
			return BaseResponseUtil.createSuccessResponse(removedUser, "UnRegisterUser");
		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "UnRegisterUser");
		}

	}

	/**
	 * For now handling unregister with in user. Can talk about ideal way in
	 * interview
	 * 
	 * @param user
	 *            {userId and pswd are reguired in payload}
	 * @return
	 */
	@PostMapping(path = "/addfriend/{friendId}", produces = "application/json", consumes = "application/json")
	@ResponseBody

	public BaseResponse addRelation(@RequestBody User user, @PathVariable("friendId") String friendId) {
		try {
			if (!GenericUtils.validateCredentials(user) || StringUtils.isEmpty(friendId)) {
				throw new CustomException(
						"Mandatory fields missing:  userid, pswd are missing in payload or friendId is empty in url param",
						ErrorCode.EC_2000);
			}
			User addedUser = UsersManager.INSTANCE.requestFriendShip(user.getUserid(), friendId);
			return BaseResponseUtil.createSuccessResponse(addedUser, "addRelation");
		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "addRelation");
		}

	}

	/**
	 * For now handling unregister with in user. Can talk about ideal way in
	 * interview
	 * 
	 * @param user
	 *            {userId and pswd are reguired in payload}
	 * @return
	 */
	@PostMapping(path = "/approvefriend/{friendId}", produces = "application/json", consumes = "application/json")
	@ResponseBody

	public BaseResponse approveRelation(@RequestBody User user, @PathVariable("friendId") String friendId) {
		try {
			if (!GenericUtils.validateCredentials(user) || StringUtils.isEmpty(friendId)) {
				throw new CustomException(
						"Mandatory fields missing:  userid, pswd are missing in payload or friendId is empty in url param",
						ErrorCode.EC_2000);
			}
			if (UsersManager.INSTANCE.approveFriendShip(user.getUserid(), friendId)) {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
				return BaseResponseUtil.createSuccessResponse(reuestingUser, "addRelation");
			} else {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
			
				return BaseResponseUtil.createErrorResponse(reuestingUser,
						new CustomException("Please check if the relation exitsts", ErrorCode.EC_UNKNOWN), "approveRelation");
			}

		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "approveRelation");
		}

	}
	/**
	 * For now handling unregister with in user. Can talk about ideal way in
	 * interview
	 * 
	 * @param user
	 *            {userId and pswd are reguired in payload}
	 * @return
	 */
	@PostMapping(path = "/denyfriend/{friendId}", produces = "application/json", consumes = "application/json")
	@ResponseBody

	public BaseResponse denyRelation(@RequestBody User user, @PathVariable("friendId") String friendId) {
		try {
			if (!GenericUtils.validateCredentials(user) || StringUtils.isEmpty(friendId)) {
				throw new CustomException(
						"Mandatory fields missing:  userid, pswd are missing in payload or friendId is empty in url param",
						ErrorCode.EC_2000);
			}
			if (UsersManager.INSTANCE.denyFriendShip(user.getUserid(), friendId)) {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
				return BaseResponseUtil.createSuccessResponse(reuestingUser, "denyRelation");
			} else {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
				return BaseResponseUtil.createErrorResponse(reuestingUser,
						new CustomException("Unknow reason. please debug", ErrorCode.EC_UNKNOWN), "denyRelation");
			}

		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "denyRelation");
		}

	}

	/**
	 * For now handling unregister with in user. Can talk about ideal way in
	 * interview
	 * 
	 * @param user
	 *            {userId and pswd are reguired in payload}
	 * @return
	 */
	@PostMapping(path = "/recall/{friendId}", produces = "application/json", consumes = "application/json")
	@ResponseBody

	public BaseResponse recallRelation(@RequestBody User user, @PathVariable("friendId") String friendId) {
		try {
			if (!GenericUtils.validateCredentials(user) || StringUtils.isEmpty(friendId)) {
				throw new CustomException(
						"Mandatory fields missing:  userid, pswd are missing in payload or friendId is empty in url param",
						ErrorCode.EC_2000);
			}
			if (UsersManager.INSTANCE.recallFriendShipRequest(user.getUserid(), friendId)) {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
				return BaseResponseUtil.createSuccessResponse(reuestingUser, "recallFriendshipRequest");
			} else {
				return BaseResponseUtil.createErrorResponse(UsersManager.INSTANCE.getUsers().get(user.getUserid()),
						new CustomException("Unknow reason. please debug", ErrorCode.EC_UNKNOWN), "recallRelation");
			}

		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "recallRelation");
		}

	}

	/**
	 * Unfriend api.
	 * Both sides users darta is changed accordingly
	 * interview
	 * 
	 * @param user
	 *            {userId and pswd are reguired in payload}
	 * @return
	 */
	@PostMapping(path = "/unfriend/{friendId}", produces = "application/json", consumes = "application/json")
	@ResponseBody

	public BaseResponse unfriend(@RequestBody User user, @PathVariable("friendId") String friendId) {
		try {
			if (!GenericUtils.validateCredentials(user) || StringUtils.isEmpty(friendId)) {
				throw new CustomException(
						"Mandatory fields missing:  userid, pswd are missing in payload or friendId is empty in url param",
						ErrorCode.EC_2000);
			}
			User unfriended = UsersManager.INSTANCE.removeFriend(user.getUserid(), friendId, false);
			if (unfriended != null) {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
				return BaseResponseUtil.createSuccessResponse(reuestingUser, "unfriended "+unfriended.getUserid());
			} else {
				User reuestingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
				return BaseResponseUtil.createErrorResponse(reuestingUser,
						new CustomException("Unknow reason. please debug", ErrorCode.EC_UNKNOWN), "unfriended");
			}

		} catch (Exception e) {
			log.error(e);
			return BaseResponseUtil.createErrorResponse(user, e, "UnRegisterUser");
		}

	}
}
