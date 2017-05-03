package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Authentication {
    private String _email;
    private String _username;
    private String _password;

    @JsonGetter("email")
    public String getEmail() {
        return _email;
    }

    @JsonGetter("username")
    public String getUsername() {
        return _username;
    }

    @JsonGetter("password")
    public String getPassword() {
        return _password;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        _email = email;
    }

    @JsonSetter("username")
    public void setUsername(String username) {
        _username = username;
    }

    @JsonSetter("password")
    public void setPassword(String password) {
        _password = password;
    }
}
