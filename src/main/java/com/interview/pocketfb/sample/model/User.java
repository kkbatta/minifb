package com.interview.pocketfb.sample.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.gson.Gson;
import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.error.ErrorCode;


public class User {
	public User() {
		super();
	}
	public User (String name,String userid,String email,String pswd,String dob){
		super();
		this.name = name;
		this.userid = userid;
		this.email = email;
		this.dob =dob;
		this.pswd = pswd;
	}
	
	/**
	 * I am taking a shorcut here to avoid toomany lines of code and 
	 * @param e
	 */
	public User (Exception e) {
		
	}
	private String name;
	private String userid;
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String pswd;
	// TODO for simplification its a string
	private String dob;
	private List<String> friends = new ArrayList<>();
	private List<Relation> pendingRelations = new ArrayList<>();
	private List<String> blackListedFriends = new ArrayList<>();
	private List<String> approvalAwaiting = new ArrayList<>();
	
	private ErrorCode error;
	
	private long id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getBlackListedFriends() {
		return blackListedFriends;
	}

	public void setBlackListedFriends(List<String> blackListedFriends) {
		this.blackListedFriends = blackListedFriends;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Relation> getPendingRelations() {
		return pendingRelations;
	}

	public void setPendingRelations(List<Relation> pendingRelations) {
		this.pendingRelations = pendingRelations;
	}
	
	
	public String findFriend(String friendId) throws CustomException {
		if(StringUtils.isEmpty(friendId)) {
			throw new CustomException("userId cannot be null to lookup friend", ErrorCode.EC_2000);
		}
		for(String user : ListUtils.emptyIfNull(friends)) {
			if(user != null && StringUtils.isNotEmpty(user) && user.equals(friendId)) {
				return user;
			}
		}
		return null;
	}
	
	public List<String> getApprovalAwaiting() {
		return approvalAwaiting;
	}
	public void setApprovalAwaiting(List<String> approvalAwaiting) {
		this.approvalAwaiting = approvalAwaiting;
	}
	
	
	/**
	 * Removes and returns the friend for logging and audtis
	 * @param friendId
	 * @param blackList
	 * @return
	 * @throws CustomException
	 */
	public String unfriend(String friendId, boolean blackList) throws CustomException {
		if(StringUtils.isEmpty(friendId)) {
			return null;
		}
		String friend = this.findFriend(friendId) ;
		this.getFriends().remove(friend);
		if(blackList) {
			this.getBlackListedFriends().add(friend);
		}
		// TODO find the relation and udpate too but thats an overkill for this i beleive
		return friend;
	}
	public ErrorCode getError() {
		return error;
	}
	public void setError(ErrorCode error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);

	}
	
	public void addRelationForApproval(Relation friend) {
		if(friend == null || friend.getFriend() == null) {
			//TODO needs to handle efrror and respond appropriatly
			return ;
		}
		pendingRelations.add(friend);
	}
	

	
	/**
	 * approves friendship
	 * idempotentn intentionally
	 * @param friendId
	 * @return
	 */
	public boolean approveFriend(String friendId) {
		for(Relation rel : pendingRelations) {
			if(rel!= null && rel.getFriend().equals(friendId)) {
				
				this.friends.add(rel.getFriend());
				rel.setStatus(Status.ACCEPTED);
				rel.setApprovedAt(System.currentTimeMillis());
				
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * Denial is slient and friend would not know baout it
	 * idempotentn intentionally
	 * @param friendId
	 * @return
	 */
	public boolean denyFriend(String friendId) {
		for(Relation rel : pendingRelations) {
			if(rel!= null && rel.getFriend().equals(friendId)) {
				
				//this.friends.add(rel.getFriend());
				rel.setStatus(Status.REJECTED);
				rel.setApprovedAt(System.currentTimeMillis());
				//udpate friends awaiting list
				return true;
			}
			
		}
		return false;
	}
	
	public boolean recallFriendShipRequest(String friendId) {
		for(String otherUser : approvalAwaiting) {
			if(otherUser!= null && otherUser.equals(friendId)) {
				
				this.friends.remove(otherUser);
				
				return true;
			}
			
		}
		return false;
	}
	
	/** 
	 * Updates friend list that you have approved and he need not await
	 * @param user
	 */
	public void removeFromAwaiting(String user) {
		this.getApprovalAwaiting().remove(user);
	}
	
	public boolean updateFriendShipRequest(Status status, String friendId) {
		
		for(Relation rel : pendingRelations) {
			if(rel!= null && rel.getFriend().equals(friendId)) {
				
				//this.friends.add(rel.getFriend());
				rel.setStatus(status);
				rel.setApprovedAt(System.currentTimeMillis());
				//udpate friends awaiting list
				return true;
			}
			
		}
		return false;
	}
	
}
