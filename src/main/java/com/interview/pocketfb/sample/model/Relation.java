package com.interview.pocketfb.sample.model;

import org.apache.commons.lang3.StringUtils;

public class Relation {
	private String friend;
	private Status status;
	private long timeRequested= -1l;
	private long approvedAt = -1l;
	public Relation(User friend, boolean newRelation) {
		if(newRelation) {
			this.friend = friend.getUserid();
			timeRequested = System.currentTimeMillis();
			status = Status.PENDING;
		}
	}
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public long getTimeRequested() {
		return timeRequested;
	}
	public void setTimeRequested(long timeRequested) {
		this.timeRequested = timeRequested;
	}
	
	public String getFriendId() {
		if(StringUtils.isNotEmpty(friend) ) {
			return friend;
		}
		return "";
	}
	public long getApprovedAt() {
		return approvedAt;
	}
	public void setApprovedAt(long approvedAt) {
		this.approvedAt = approvedAt;
	}

}
