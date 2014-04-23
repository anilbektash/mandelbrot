package com.semantica.mandelbrot.persistence.test;

import static com.semantica.mandelbrot.persistence.test.util.MandelbrotTestUtils.*;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.semantica.mandelbrot.persistence.domain.Badge;
import com.semantica.mandelbrot.persistence.domain.User;
import com.semantica.mandelbrot.persistence.repository.BadgeRepository;
import com.semantica.mandelbrot.persistence.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/mandelbrot-test-persistence.xml")
public class TestUserOperations {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BadgeRepository badgeRepository;

	@After
	public void teardown() {
		// remove all possible written test data.
		userRepository.removeAll();
		badgeRepository.removeAll();
	}

	@Test
	public void testPersistSingleBasicUser() throws Exception {
		// create a basic test user.
		User defaultUser = createDefaultUser();
		// save it.
		userRepository.save(defaultUser);
		// get the count of users.
		long count = userRepository.count();
		// check that count is 1L.
		Assert.assertThat(count, is(1l));

		// check that persistent user is actually our user.
		List<? extends User> users = userRepository.findAll();
		Assert.assertThat(users.size(), is(1));
		Assert.assertThat(users.get(0).getUserName(),
				is(DEFAULT_TEST_USER_NAME));
	}

	@Test
	public void testPersistAdvancedUser() throws Exception {

		// create and persist follower and followee users.
		User followerUser = createFollowerUser();
		userRepository.save(followerUser);

		User followeeUser = createFolloweeUser();
		userRepository.save(followeeUser);

		// create and persist a simple badge.
		Badge badge = createBadge();
		badgeRepository.save(badge);

		// create a detailed user.
		User detailedUser = createDefaultUser();
		detailedUser.addFollower(followerUser);
		detailedUser.addFollowee(followeeUser);
		detailedUser.addBadge(badge);

		// save it.
		userRepository.save(detailedUser);
		// get the count of users.
		long count = userRepository.count();
		// check that count is 3L.
		Assert.assertThat(count, is(3l));

		// check that detailed user is actually our user.
		User probableDetailedUser = userRepository
				.findByUserName(DEFAULT_TEST_USER_NAME);

		Assert.assertThat(probableDetailedUser.getName(), is(DEFAULT_TEST_NAME));
		Assert.assertThat(probableDetailedUser.getSurname(),
				is(DEFAULT_TEST_SURNAME));
		Assert.assertThat(probableDetailedUser.getUserName(),
				is(DEFAULT_TEST_USER_NAME));

		Assert.assertThat(probableDetailedUser.getFollowers().size(), is(1));
		Assert.assertThat(probableDetailedUser.getFollowers().get(0)
				.getUserName(), is(FOLLOWER_USER_USERNAME));

		Assert.assertThat(probableDetailedUser.getFollowees().size(), is(1));
		Assert.assertThat(probableDetailedUser.getFollowees().get(0)
				.getUserName(), is(FOLLOWEE_USER_USERNAME));

		Assert.assertThat(probableDetailedUser.getBagdes().size(), is(1));
		Assert.assertThat(probableDetailedUser.getBagdes().get(0).getName(),
				is(DEFAULT_TEST_BADGE_NAME));
	}

	@Test(expected = DuplicateKeyException.class)
	public void testPersistExistingUser() throws Exception {
		// create a default user.
		User firstDefaultUser = createDefaultUser();
		// save it
		userRepository.save(firstDefaultUser);

		// create a second user.
		User secondDefaultUser = createDefaultUser();
		// try to save it - this line should throw an exception.
		userRepository.save(secondDefaultUser);

		Assert.fail("This line should never been executed.");
	}
}
