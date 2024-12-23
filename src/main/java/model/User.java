package model;

public class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phoneNumber;
    protected String role;

    public User() {
    }

//    public User(int userId, String username, String password, String email, String firstName, String lastName, String address, String phoneNumber, String role) {
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//        this.role = role;
//    }


    public User(int userId, String username, String hashedPassword, String email, String firstName, String lastName, String address, String phoneNumber, String role) {
          this.userId = userId;
          this.username = username;
          this.password = hashedPassword;
          this.email = email;
          this.firstName = firstName;
          this.lastName = lastName;
          this.address = address;
          this.phoneNumber = phoneNumber;
          this.role = role;

    }

    public User(int userId, String username, String hashedPassword, String email, String firstName, String lastName, String role) {
          this.userId = userId;
          this.username = username;
          this.password = hashedPassword;
          this.email = email;
          this.firstName = firstName;
          this.lastName = lastName;
          this.address = address;
          this.phoneNumber = phoneNumber;
          this.role = role;

    }

    public User(int userId, String username, String hashedPassword, String email, String firstName, String lastName, String address, String phoneNumber, String role, Object o) {
        this.username = username;
        this.password = hashedPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirsrtName(String firsrName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


