package com.ovalhr.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ovalhr.taskmanager.listeners.AuditEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * Created by rana on 8/27/21.
 */
@MappedSuperclass
@EntityListeners({AuditEntityListener.class })
public abstract class Audit implements Serializable {

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@JsonIgnore
	@Column(name = "CREATED_BY")
	private String createdBy;

	@JsonIgnore
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@JsonIgnore
	@Column(name = "CREATOR_FULL_NAME")
	private String creatorFullName;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "CREATED_USER_ID")
	private User createdUser;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatorFullName() {
		return creatorFullName;
	}

	public void setCreatorFullName(String creatorFullName) {
		this.creatorFullName = creatorFullName;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}
}
