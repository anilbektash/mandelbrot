package com.semantica.mandelbrot.persistence.test.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.semantica.mandelbrot.persistence.domain.Badge;
import com.semantica.mandelbrot.persistence.domain.Photo;
import com.semantica.mandelbrot.persistence.domain.User;

public class MandelbrotTestUtils {

	public static final String DEFAULT_TEST_NAME = "test user name";
	public static final String DEFAULT_TEST_SURNAME = "test user surname";
	public static final String DEFAULT_TEST_USER_NAME = "test.user.userName";

	public static final String FOLLOWER_USER_USERNAME = "follower.user.username";
	public static final String FOLLOWEE_USER_USERNAME = "followee.user.username";

	public static final String DEFAULT_TEST_BADGE_NAME = "testBadge";

	public static final String DEFAULT_PHOTO_PATH = "/test.jpg";
	public static final String DEFAULT_PHOTO_EXPLANATION = "test.photo.explanation";
	public static final String DEFAULT_PHOTO_NAME = "test.photo.name";

	public static User createDefaultUser() {
		return createUser(DEFAULT_TEST_NAME, DEFAULT_TEST_SURNAME,
				DEFAULT_TEST_USER_NAME, null);
	}

	public static User createFollowerUser() {
		return createUser("follower user name", "follower user surname",
				FOLLOWER_USER_USERNAME, "follower user password");
	}

	public static User createFolloweeUser() {
		return createUser("followee user name", "followee user surname",
				FOLLOWEE_USER_USERNAME, "followee user password");
	}

	private static User createUser(String name, String surname,
			String userName, String password) {
		return new User(name, surname, userName, password);
	}

	public static Badge createBadge() {
		return new Badge(DEFAULT_TEST_BADGE_NAME);
	}

	public static Photo createDefaultPhoto() throws FileNotFoundException,
			IOException {
		Photo photo = new Photo();
		photo.setName(DEFAULT_PHOTO_NAME);
		photo.setExplanation(DEFAULT_PHOTO_EXPLANATION);

		byte[] photoContent = IOUtils.toByteArray(MandelbrotTestUtils.class
				.getResourceAsStream(DEFAULT_PHOTO_PATH));
		photo.setPhotoContent(photoContent);

		return photo;
	}

}
