package com.team9.motors.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty(message="Please provide your name")
    private String firstName;
    @NotEmpty(message="Please provide your name")
    private String lastName;
    @Column (unique=true, nullable = false)
    @NotEmpty(message="Please provide your address")
    private String username;
    @Column(unique=true, nullable=false)
    private String email;
    @NotEmpty
    @Length(min = 8, message="Please provide a password with at least 8 characters")
    private String password;
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id)
    {

        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public com.team9.motors.model.Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Role=" + role +
                '}';
    }
}
