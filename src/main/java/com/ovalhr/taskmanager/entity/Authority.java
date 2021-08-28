package com.ovalhr.taskmanager.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * Created by rana on 8/27/21.
 */

@Entity
@Table(name = "AUTHORITIES")
@XmlRootElement
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@SequenceGenerator(name = "AUTHORITY_SEQUENCE_GENERATOR", sequenceName = "AUTHORITY_ID_SEQUENCE")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AUTHORITY_SEQUENCE_GENERATOR")
	@Basic(optional = false)
	@Column(name = "ID", nullable = false, precision = 38, scale = 0)
	private Long id;

	@Column(name = "USERNAME", length = 50)
	private String username;

	@Column(name = "AUTHORITY", length = 50)
	private String authority;

	public Authority() {
	}

	public Authority(Long id, String username, String authority) {
		this.id = id;
		this.username = username;
		this.authority = authority;
	}
	public Authority(String username, String authority) {
		this.username = username;
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}