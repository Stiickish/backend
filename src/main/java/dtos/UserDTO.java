package dtos;

import entities.User;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private String userName;
    private String userPass;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getUserName(), userDTO.getUserName()) && Objects.equals(getUserPass(), userDTO.getUserPass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getUserPass());
    }
}
