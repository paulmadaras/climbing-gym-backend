package com.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean student;

    @Column(nullable = false)
    private boolean studentVerified;

    @Column(nullable = true)
    private String studentProofImageUrl; // path to uploaded proof

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Membership membership;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;


    public User() {
    }

    public User(String first_name, String last_name, String username, String email, String password, Role role, boolean student, boolean studentVerified, String studentProofImageUrl) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.student = student;
        this.studentVerified = studentVerified;
        this.studentProofImageUrl = studentProofImageUrl;
    }


    public User(String first_name, String last_name, String username, String email, String password, Role role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.student = false;
        this.studentVerified = false;
        this.studentProofImageUrl = null;
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isStudent() {
        return student;
    }

    public boolean isStudentVerified() {
        return studentVerified;
    }

    public String getStudentProofImageUrl() {
        return studentProofImageUrl;
    }

    public Membership getMembership() {
        return membership;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public void setStudentVerified(boolean studentVerified) {
        this.studentVerified = studentVerified;
    }

    public void setStudentProofImageUrl(String studentProofImageUrl) {
        this.studentProofImageUrl = studentProofImageUrl;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
