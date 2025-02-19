package com.walletapp.dto.user;

public class UserRequest {
//    private Long userId;
    private String username;
    private String password;
//
//    public UserDTO(Long userId, String username, String password) {
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//    }

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserRequest(){}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
//
//    public Long getUserId() {

//        return userId;
//    }
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
