package com.keshore.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Size(min = 8, message = "Minimum password length: 8 characters")
	private String password;

	@Column(nullable = false)
	private String firstname;

	private String middlename;

	private String lastname;

	@Column(nullable = false)
	private Long mobile_1;
	
	private Long mobile_2;

	private String address;

	private Date date_of_birth;

	@Lob
	@Column(length = 100000)
	private byte[] photo;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Role> roles;

}
