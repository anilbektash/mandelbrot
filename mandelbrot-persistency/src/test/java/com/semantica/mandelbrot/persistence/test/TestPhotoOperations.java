package com.semantica.mandelbrot.persistence.test;

import static com.semantica.mandelbrot.persistence.test.util.MandelbrotTestUtils.*;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.semantica.mandelbrot.persistence.domain.Photo;
import com.semantica.mandelbrot.persistence.domain.User;
import com.semantica.mandelbrot.persistence.repository.PhotoRepository;
import com.semantica.mandelbrot.persistence.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/mandelbrot-test-persistence.xml")
public class TestPhotoOperations {

	@Autowired
	private PhotoRepository photoRepository;

	@Autowired
	private UserRepository userRepository;

	@After
	public void teardown() {
		// remove all possible written test data.
		photoRepository.removeAll();
		userRepository.removeAll();
	}

	@Test
	public void testPersistSingleBasicPhoto() throws Exception {
		// a basic photo should have name, explanation, file itself and an
		// owner.
		User owner = createDefaultUser();
		userRepository.save(owner);

		// create and save default photo.
		Photo photo = createDefaultPhoto();
		photo.setOwner(owner);
		photoRepository.save(photo);

		// get the count of photos.
		long count = photoRepository.count();
		// check that count is 1L.
		Assert.assertThat(count, is(1l));

		// check that persistent photo is actually our photo.
		List<? extends Photo> photos = photoRepository.findAll();
		Assert.assertThat(photos.size(), is(1));
		Assert.assertThat(photos.get(0).getName(), is(DEFAULT_PHOTO_NAME));
		Assert.assertThat(photos.get(0).getExplanation(),
				is(DEFAULT_PHOTO_EXPLANATION));
		Assert.assertThat(
				photos.get(0).getPhotoContent(),
				is(IOUtils.toByteArray(getClass().getResourceAsStream(
						DEFAULT_PHOTO_PATH))));
		//check only owner's user name.
		Assert.assertThat(photos.get(0).getOwner().getUserName(), is(owner.getUserName()));
	}

}
