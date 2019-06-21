package com.interview.pocketfb.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.interview.pocketfb.UsersManager;
import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.sample.model.User;
import com.interview.pocketfb.utils.GenericUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserManagerTest.class)
public class GenericUtilsTests {
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
	public void validateCredentialsTest() throws Exception {
		User user = new User("soma", "somaid", "soma@gmail.com", "test", "1980/06/07");
		Assert.assertTrue(GenericUtils.validateCredentials(user));
		User user2 = new User("soma", "somaid", "soma@gmail.com", "", "");
		try {
			GenericUtils.validateUser(user2);
		}catch(Exception e) {
			Assert.assertNotNull(e);
		}
	}
	
	@Test
	public void validateUserTest() throws Exception {
		User user = new User("soma", "somaid", "soma@gmail.com", "test", "1980/06/07");
		Assert.assertTrue(GenericUtils.validateUser(user));
		User user2 = new User("soma", "somaid", "soma@gmail.com", null, "");
		Assert.assertFalse(GenericUtils.validateUser(user2));
	}
	
	
}
