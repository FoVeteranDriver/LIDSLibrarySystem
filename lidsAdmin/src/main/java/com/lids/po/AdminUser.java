package com.lids.po;

import java.io.Serializable;
import java.util.List;

public class AdminUser implements Serializable{

    private static final long serialVersionUID = 111L;

    private int id;
    private String account;
    private String password;
    private int level;
    private String name;
    private String phone;
    private String email;
    private String libraryCardNumber;

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(String libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    //权限json
    private List accessList;

    public List getAccessList() {
        return accessList;
    }

    public void setAccessList(List accessList) {
        this.accessList = accessList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
