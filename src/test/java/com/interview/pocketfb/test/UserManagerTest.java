package com.interview.pocketfb.test;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.interview.pocketfb.UsersManager;
import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.sample.model.Relation;
import com.interview.pocketfb.sample.model.Status;
import com.interview.pocketfb.sample.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserManagerTest.class)
public class UserManagerTest{
	
	@Before
	public void init() throws CustomException, Exception {
		UsersManager.INSTANCE.getUsers().clear();
		UsersManager.INSTANCE.init();
		User user = new User("soma", "somaid", "soma@gmail.com", "test", "1980/06/07");
		User user2 = new User("ravi", "raviid", "ravi@gmail.com", "test", "1980/06/07");
		User user3 = new User("kishore", "kishore", "kishore@gmail.com", "test", "1980/06/07");
		User user4 = new User("suresh", "sureshid", "suresh@gmail.com", "test", "1980/06/07");
		
		UsersManager.INSTANCE.validateAndAdd(user);
		UsersManager.INSTANCE.validateAndAdd(user2);
		UsersManager.INSTANCE.validateAndAdd(user3);
		UsersManager.INSTANCE.validateAndAdd(user4);
	}

	@Test
	public void addUserTest() throws CustomException, Exception {
		User user = new User("huy", "huyid", "huyid@gmail.com", "test", "1980/06/07");
		UsersManager.INSTANCE.addUser(user);
		User addedUser = UsersManager.INSTANCE.getUser(user.getUserid());
		Assert.assertSame(addedUser.getUserid(),user.getUserid() );
		Assert.assertSame(addedUser.getName(),user.getName() );
		Assert.assertSame(addedUser.getDob(),user.getDob() );
	}
	
	@Test
	public void removeUserTest() throws CustomException, Exception {
		User user = new User("huy2", "huyid2", "huyid@gmail.com", "test", "1980/06/07");
		UsersManager.INSTANCE.addUser(user);
		User addedUser = UsersManager.INSTANCE.getUser(user.getUserid());
		Assert.assertSame(addedUser.getUserid(),user.getUserid() );
		Assert.assertSame(addedUser.getName(),user.getName() );
		Assert.assertSame(addedUser.getDob(),user.getDob() );
		
		User removedUser = UsersManager.INSTANCE.removeUser(addedUser);
		Assert.assertSame(removedUser.getUserid(),user.getUserid() );
		Assert.assertSame(removedUser.getName(),user.getName() );
		Assert.assertSame(removedUser.getDob(),user.getDob() );
		
		User nullUser = UsersManager.INSTANCE.getUser(addedUser.getUserid());
		Assert.assertNull(nullUser);
	}
	
	
	@Test
	public void addRelationTest() throws CustomException, Exception {
		UsersManager.INSTANCE.requestFriendShip("somaid", "raviid");
		User requester = UsersManager.INSTANCE.getUser("somaid");
		User approver = UsersManager.INSTANCE.getUser("raviid");
		List<String> awaitingApprovals = requester.getApprovalAwaiting();
		List<Relation> pendingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(pendingApprovals) && pendingApprovals.size()==1);
		Relation rel = pendingApprovals.get(0);
		Assert.assertTrue(rel.getFriendId().equals("somaid"));
		Assert.assertTrue(rel.getStatus().equals(Status.PENDING));
		
		Assert.assertTrue(CollectionUtils.isNotEmpty(awaitingApprovals) && awaitingApprovals.size()==1);
		String approverId = awaitingApprovals.get(0);
		Assert.assertTrue(approverId.equals("raviid"));
		
	}
	
	@Test
	public void approveRelationTest() throws CustomException, Exception {
		UsersManager.INSTANCE.requestFriendShip("somaid", "raviid");
		User requester = UsersManager.INSTANCE.getUser("somaid");
		User approver = UsersManager.INSTANCE.getUser("raviid");
		List<String> awaitingApprovals = requester.getApprovalAwaiting();
		List<Relation> pendingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(pendingApprovals) && pendingApprovals.size()==1);
		Relation rel = pendingApprovals.get(0);
		Assert.assertTrue(rel.getFriendId().equals("somaid"));
		Assert.assertTrue(rel.getStatus().equals(Status.PENDING));
		
		Assert.assertTrue(CollectionUtils.isNotEmpty(awaitingApprovals) && awaitingApprovals.size()==1);
		String approverId = awaitingApprovals.get(0);
		Assert.assertTrue(approverId.equals("raviid"));
		
		Assert.assertTrue(UsersManager.INSTANCE.approveFriendShip("raviid", "somaid"));
		
		
		List<Relation> ramainingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(ramainingApprovals) && ramainingApprovals.size()==1);
		Relation relAfter = ramainingApprovals.get(0);
		Assert.assertTrue(relAfter.getFriendId().equals("somaid"));
		Assert.assertTrue(relAfter.getStatus().equals(Status.ACCEPTED));
		
		Assert.assertTrue(CollectionUtils.isEmpty(awaitingApprovals));
		
		
		User requester2 = UsersManager.INSTANCE.getUser("somaid");
		User approver2 = UsersManager.INSTANCE.getUser("raviid");
		
		Assert.assertTrue(requester2.getFriends().contains("raviid"));
		Assert.assertTrue(approver2.getFriends().contains("somaid"));
		
	}
	
	
	@Test
	public void denyRelationTest() throws CustomException, Exception {
		UsersManager.INSTANCE.requestFriendShip("somaid", "raviid");
		User requester = UsersManager.INSTANCE.getUser("somaid");
		User approver = UsersManager.INSTANCE.getUser("raviid");
		List<String> awaitingApprovals = requester.getApprovalAwaiting();
		List<Relation> pendingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(pendingApprovals) && pendingApprovals.size()==1);
		Relation rel = pendingApprovals.get(0);
		Assert.assertTrue(rel.getFriendId().equals("somaid"));
		Assert.assertTrue(rel.getStatus().equals(Status.PENDING));
		
		Assert.assertTrue(CollectionUtils.isNotEmpty(awaitingApprovals) && awaitingApprovals.size()==1);
		String approverId = awaitingApprovals.get(0);
		Assert.assertTrue(approverId.equals("raviid"));
		
		Assert.assertTrue(UsersManager.INSTANCE.denyFriendShip("raviid", "somaid"));
		
		
		List<Relation> ramainingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(ramainingApprovals) && ramainingApprovals.size()==1);
		Relation relAfter = ramainingApprovals.get(0);
		Assert.assertTrue(relAfter.getFriendId().equals("somaid"));
		Assert.assertTrue(rel.getStatus().equals(Status.REJECTED));
		
		Assert.assertTrue(CollectionUtils.isNotEmpty(awaitingApprovals) && awaitingApprovals.size()==1);
		String approverId2 = awaitingApprovals.get(0);
		Assert.assertTrue(approverId2.equals("raviid"));
		
		
	}
	
	@Test
	public void recallRelationTest() throws CustomException, Exception {
		UsersManager.INSTANCE.requestFriendShip("somaid", "raviid");
		User requester = UsersManager.INSTANCE.getUser("somaid");
		User approver = UsersManager.INSTANCE.getUser("raviid");
		List<String> awaitingApprovals = requester.getApprovalAwaiting();
		List<Relation> pendingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(pendingApprovals) && pendingApprovals.size()==1);
		Relation rel = pendingApprovals.get(0);
		Assert.assertTrue(rel.getFriendId().equals("somaid"));
		Assert.assertTrue(rel.getStatus().equals(Status.PENDING));
		
		Assert.assertTrue(CollectionUtils.isNotEmpty(awaitingApprovals) && awaitingApprovals.size()==1);
		String approverId = awaitingApprovals.get(0);
		Assert.assertTrue(approverId.equals("raviid"));
		
		Assert.assertTrue(UsersManager.INSTANCE.recallFriendShipRequest("somaid", "raviid"));
		
		
		List<Relation> ramainingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(ramainingApprovals) && ramainingApprovals.size()==1);
		Relation relAfter = ramainingApprovals.get(0);
		Assert.assertTrue(relAfter.getFriendId().equals("somaid"));
		Assert.assertEquals("Stastus mismatch", Status.RECALLED, rel.getStatus());
		
		Assert.assertTrue(CollectionUtils.isEmpty(awaitingApprovals) );
		
		
		
	}
	
	@Test
	public void removeFriend() throws CustomException, Exception {
		UsersManager.INSTANCE.requestFriendShip("somaid", "raviid");
		User requester = UsersManager.INSTANCE.getUser("somaid");
		User approver = UsersManager.INSTANCE.getUser("raviid");
		List<String> awaitingApprovals = requester.getApprovalAwaiting();
		List<Relation> pendingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(pendingApprovals) && pendingApprovals.size()==1);
		Relation rel = pendingApprovals.get(0);
		Assert.assertTrue(rel.getFriendId().equals("somaid"));
		Assert.assertTrue(rel.getStatus().equals(Status.PENDING));
		
		Assert.assertTrue(CollectionUtils.isNotEmpty(awaitingApprovals) && awaitingApprovals.size()==1);
		String approverId = awaitingApprovals.get(0);
		Assert.assertTrue(approverId.equals("raviid"));
		
		Assert.assertTrue(UsersManager.INSTANCE.approveFriendShip("raviid", "somaid"));
		
		
		List<Relation> ramainingApprovals = approver.getPendingRelations();
		Assert.assertTrue(CollectionUtils.isNotEmpty(ramainingApprovals) && ramainingApprovals.size()==1);
		Relation relAfter = ramainingApprovals.get(0);
		Assert.assertTrue(relAfter.getFriendId().equals("somaid"));
		Assert.assertTrue(relAfter.getStatus().equals(Status.ACCEPTED));
		
		User requester2 = UsersManager.INSTANCE.getUser("somaid");
		User approver2 = UsersManager.INSTANCE.getUser("raviid");
		
		Assert.assertTrue(requester2.getFriends().contains("raviid"));
		Assert.assertTrue(approver2.getFriends().contains("somaid"));
		
		Assert.assertTrue(CollectionUtils.isEmpty(awaitingApprovals));
		
		Assert.assertNotNull(UsersManager.INSTANCE.removeFriend("somaid", "raviid", true));
		
		User requester3 = UsersManager.INSTANCE.getUser("somaid");
		User approver3 = UsersManager.INSTANCE.getUser("raviid");
		

		Assert.assertTrue(!requester3.getFriends().contains("raviid"));
		Assert.assertTrue(!approver3.getFriends().contains("somaid"));
		
	}

}
