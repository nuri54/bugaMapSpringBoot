package de.hhn.se.labswp.bugamap.jpa;

import de.hhn.se.labswp.bugamap.security.auth.roles.Role;
import jakarta.persistence.*;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents an entity for an administrator of the application. This entity implements UserDetails
 * of Spring Security to handle the authentication and authorization of administrators.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class Admin implements UserDetails {

    /**
     * The unique identifier of the admin.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * The first name of the admin.
     */
    @Column(name = "Firstname")
    private String firstname;

    /**
     * The last name of the admin.
     */
    @Column(name = "Lastname")
    private String lastname;

    /**
     * The email address of the admin.
     */
    @Column(name = "Emailadress")
    private String emailadress;

    /**
     * The password of the admin.
     */
    @Column(name = "Password")
    private String password;

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Returns the unique identifier of the admin.
     *
     * @return the unique identifier of the admin.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the admin.
     *
     * @param id the unique identifier of the admin.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the first name of the admin.
     *
     * @return the first name of the admin.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the admin.
     *
     * @param firstname the first name of the admin.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the last name of the admin.
     *
     * @return the last name of the admin.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the admin.
     *
     * @param lastname the last name of the admin.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the email address of the admin.
     *
     * @return the email address of the admin.
     */
    public String getEmailadress() {
        return emailadress;
    }

    /**
     * Sets the email address of the admin.
     *
     * @param emailadress the email address of the admin.
     */
    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleAsString() {
        return this.role.toString();
    }

    /**
     * Returns the granted authorities of the admin. In this case, the admin role is returned.
     *
     * @return the granted authorities of the admin.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    /**
     * Returns the password of the admin.
     *
     * @return the password of the admin.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the email address of the admin. This is the username for authentication.
     *
     * @return the email address of the admin.
     */
    @Override
    public String getUsername() {
        return emailadress;
    }

    /**
     * Returns a boolean indicating whether the account of the admin has expired or not.
     *
     * @return true if the account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns true since admin account is never locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Returns true since admin credentials never expire.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Returns true since admin account is always enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Sets the password of the admin.
     *
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the admin object, including its id, firstname, lastname,
     * email address, and password.
     *
     * @return a string representation of the admin object
     */
    @Override
    public String toString() {
        return "Admin{"
                + "id=" + id
                + ", firstname='" + firstname + '\''
                + ", lastname='" + lastname + '\''
                + ", emailadress='" + emailadress + '\''
                + ", password='" + password + '\''
                +
                '}';
    }
}