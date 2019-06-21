package com.interview.pocketfb;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.attribute.standard.RequestingUserName;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.error.ErrorCode;
import com.interview.pocketfb.sample.model.Relation;
import com.interview.pocketfb.sample.model.Status;
import com.interview.pocketfb.sample.model.User;
import com.interview.pocketfb.utils.GenericUtils;

public enum UsersManager {
	INSTANCE;
	private static final Logger log = (Logger) LogManager.getLogger(UsersManager.class.getName());
	private boolean isHealthy = false;
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

	public void init(){
		try {
		populateUsers();
		isHealthy = true;
		}catch (Exception e) {
			log.error("Error initializing pocketFd data", e);
		}
		
	}
	/**
	 * TODO Ideally load it from persistent data source. Will implemennt this if
	 * time permits
	 * @throws CustomException 
	 * @throws Exception 
	 */
	public  void populateUsers() throws CustomException, Exception {
		User user = new User("kk", "kkbatta", "kkbatta@gmail.com", "test", "1980/06/07");
		User user2 = new User("kk2", "kkbatta2", "kkbatta2@gmail.com", "test", "1980/06/07");
		User user3 = new User("kk3", "kkbatta3", "kkbatta3@gmail.com", "test", "1980/06/07");
		User user4 = new User("kk4", "kkbatta4", "kkbatta4@gmail.com", "test", "1980/06/07");

		INSTANCE.validateAndAdd(user);
		INSTANCE.validateAndAdd(user2);
		INSTANCE.validateAndAdd(user3);
		INSTANCE.validateAndAdd(user4);
	}

	
	
	public void validateAndAdd(User user) throws CustomException, Exception {

		if (! GenericUtils.validateUser(user)) {
			throw new CustomException("Input invalid. please send mandatory fiels name, userid, dob, email and pswd", ErrorCode.EC_2000);
		}
		
		if (UsersManager.INSTANCE.getUsers() != null && !UsersManager.INSTANCE.getUsers().containsKey(user.getId())) {
			User tempUser = UsersManager.INSTANCE.getUsers().putIfAbsent(user.getUserid(), user);
			try {
				// Should not happen but handliung for future geneartions
				if (tempUser != null) {
					log.error("User already exists");
				}
			} catch (Exception e) {
				log.error("Data corrupt", e);
				INSTANCE.isHealthy = false;
			}
		} else {
			log.debug("User already exists so continuing");
		}
	}

	public boolean isHealthy() {
		return isHealthy;
	}

	public ConcurrentHashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(ConcurrentHashMap<String, User> users) {
		this.users = users;
	}

	
	
	
	//** All accessor methods for User management **//
	
	public User addUser(User user) throws CustomException, Exception {
		INSTANCE.validateAndAdd(user);
		
		return getUsers().get(user.getUserid());
	}
	
	public User getUser(String userId) {
		if(StringUtils.isEmpty(userId)) {
			return null;
		}
		return users.get(userId);
	}
	
	public User removeUser(User user) throws CustomException, Exception {
		if (! GenericUtils.validateCredentials(user)) {
			throw new CustomException("Input invalid. please send mandatory fields userid and pswd", ErrorCode.EC_2000);
		}
		
		return getUsers().remove(user.getUserid());
	
		
	}
	
	/**
	 * Adds tghe relation to Friend so that he can approve
	 * @param myId
	 * @param friendId
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public User requestFriendShip(String myId, String friendId) throws CustomException, Exception {
		
		if(StringUtils.isEmpty(myId)||StringUtils.isEmpty(friendId)) {
			return null;
		}
		User reuestingUser = getUsers().get(myId);
		if(reuestingUser != null) {
			User approver = getUser(friendId);
			if(approver != null) {
				reuestingUser.getFriends().add(friendId);
				Relation rel = new Relation(reuestingUser, true);
				approver.addRelationForApproval(rel);
				reuestingUser.getApprovalAwaiting().add(friendId);
			}else {
				throw new CustomException(MessageFormat.format("Friend {0} not found to submit request to",friendId), ErrorCode.EC_1001) ;
			}
		}else {
			throw new CustomException(MessageFormat.format("User {0} not found to submit request",myId), ErrorCode.EC_1002);
		}
		
		return reuestingUser;
	}
	
	/**
	 * Adds relation for approval by the user
	 * @param myId
	 * @param friendId
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public boolean addFriend(String myId, String friendId) throws CustomException, Exception {
		if(StringUtils.isEmpty(myId)||StringUtils.isEmpty(friendId)) {
			throw new CustomException("User Id and Friend Id cannot be null", ErrorCode.EC_2002);
		}
		User user = getUsers().get(myId);
		if(user != null) {
			User friend = getUser(friendId);
			
			if(friend != null) {
				return  user.approveFriend(friendId);
			}else {
				throw new CustomException("Friend not found to approve", ErrorCode.EC_1001);
			}
			
		}else {
			throw new CustomException("User not found to approve", ErrorCode.EC_1002);
		}
		
	}
	
	
	/**
	 * Adds tghe relation to Friend so that he can approve
	 * @param myId
	 * @param friendId
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public boolean recallFriendShipRequest(String myId, String friendId) throws CustomException, Exception {
		if(StringUtils.isEmpty(myId)||StringUtils.isEmpty(friendId)) {
			throw new CustomException("User Id and Friend Id cannot be null", ErrorCode.EC_2002);
		}
		User reuestingUser = getUsers().get(myId);
		if(reuestingUser != null) {
			User approver = getUser(friendId);
			if(approver != null) {
				boolean status = reuestingUser.recallFriendShipRequest(approver.getUserid());
				reuestingUser.removeFromAwaiting(friendId);
				approver.updateFriendShipRequest(Status.RECALLED, myId);
				return status;
			}
		}else {
			throw new CustomException("User not found to approve", ErrorCode.EC_1002);
		}
		
		return false;
	}
	
	/**
	 * valdiate and approves friend
	 * aldo manages the friends awaitingApproval and Users pending approvals
	 * @param myId
	 * @param friendId
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public boolean approveFriendShip(String myId, String friendId) throws CustomException, Exception {
		if(StringUtils.isEmpty(myId)||StringUtils.isEmpty(friendId)) {
			throw new CustomException("User Id and Friend Id cannot be null", ErrorCode.EC_2002);
		}
		User user = getUsers().get(myId);
		if(user != null) {
			User friend = getUser(friendId);
			if(friend != null) {
				boolean status = user.approveFriend(friendId);
				//udpate friends awaiting list
				friend.removeFromAwaiting(myId);
				return status;
			}else {
				throw new CustomException("Friend not found to approve", ErrorCode.EC_1001);
			}
			
			
			
		}else {
			throw new CustomException("User not found to approve", ErrorCode.EC_1002);
		}
	}
	
	/**
	 * valdiate and denies friend
	 * aldo manages the friends awaitingApproval and Users pending approvals
	 * @param myId
	 * @param friendId
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public boolean denyFriendShip(String myId, String friendId) throws CustomException, Exception {
		if(StringUtils.isEmpty(myId)||StringUtils.isEmpty(friendId)) {
			throw new CustomException("User Id and Friend Id cannot be null", ErrorCode.EC_2002);
		}
		User user = getUsers().get(myId);
		if(user != null) {
			User friend = getUser(friendId);
			if(friend != null) {
				return  user.denyFriend(friendId);
			}else {
				throw new CustomException("Friend not found to approve", ErrorCode.EC_1001);
			}
			
		}else {
			throw new CustomException("User not found to approve", ErrorCode.EC_1002);
		}
	}
	
	
	
	
	
	/**
	 * Remove a friendhsip
	 * udoaptes respective states
	 * @param myId
	 * @param friendId
	 * @param blackList
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public User removeFriend(String myId, String friendId, boolean blackList) throws CustomException, Exception {
		if(StringUtils.isEmpty(myId)||StringUtils.isEmpty(friendId)) {
			return null;
		}
		User user = getUsers().get(myId);
		if(user != null && CollectionUtils.isNotEmpty(user.getFriends())) {
			String unFriended = user.unfriend(friendId, blackList);
			User friend = getUser(friendId);
			
			if(friend != null) {
				friend.getFriends().remove(myId);
			}
			return UsersManager.INSTANCE.getUser(unFriended);
		}
		
		return null;
	}
	
	
}
