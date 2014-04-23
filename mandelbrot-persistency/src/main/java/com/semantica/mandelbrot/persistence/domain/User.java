package com.semantica.mandelbrot.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

	@Id
	private String id;

	private String name;

	private String surname;

	@Indexed(unique = true)
	private String userName;

	private String password;

	@DBRef(lazy = true)
	private List<User> followers;

	@DBRef(lazy = true)
	private List<User> followees;

	@DBRef(lazy = true)
	private List<Badge> bagdes;

	public User() {
	}

	public User(String name, String surname, String userName, String password) {
		this.name = name;
		this.surname = surname;
		this.userName = userName;
		this.password = password;
	}

	public void addFollower(User follower) {
		getFollowers().add(follower);
	}

	public void addFollowee(User followee) {
		getFollowees().add(followee);
	}

	public void addBadge(Badge badge) {
		getBagdes().add(badge);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getFollowers() {
		if (followers == null) {
			followers = new ArrayList<User>();
		}
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<User> getFollowees() {
		if (followees == null) {
			followees = new ArrayList<User>();
		}
		return followees;
	}

	public void setFollowees(List<User> followees) {
		this.followees = followees;
	}

	public List<Badge> getBagdes() {
		if (bagdes == null) {
			bagdes = new ArrayList<Badge>();
		}
		return bagdes;
	}

	public void setBagdes(List<Badge> bagdes) {
		this.bagdes = bagdes;
	}

}