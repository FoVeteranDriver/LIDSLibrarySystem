package com.lids.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminPermission {

    private String admin_user_name;
    private int admin_user_id;
    private int admin_settings;
    private int admin_authority;
    private int admin_rules;
    private int admin_display;
    private int admin_space;
    private int admin_management;
    private int admin_mSpace;
    private int admin_credits;
    private int admin_activity;
    private int admin_data;
    private int admin_data1;
    private int admin_data2;

    public String getAdmin_user_name() {
        return admin_user_name;
    }

    public void setAdmin_user_name(String admin_user_name) {
        this.admin_user_name = admin_user_name;
    }

    public int getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(int admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public int getAdmin_settings() {
        return admin_settings;
    }

    public void setAdmin_settings(int admin_settings) {
        this.admin_settings = admin_settings;
    }

    public int getAdmin_authority() {
        return admin_authority;
    }

    public void setAdmin_authority(int admin_authority) {
        this.admin_authority = admin_authority;
    }

    public int getAdmin_rules() {
        return admin_rules;
    }

    public void setAdmin_rules(int admin_rules) {
        this.admin_rules = admin_rules;
    }

    public int getAdmin_display() {
        return admin_display;
    }

    public void setAdmin_display(int admin_display) {
        this.admin_display = admin_display;
    }

    public int getAdmin_space() {
        return admin_space;
    }

    public void setAdmin_space(int admin_space) {
        this.admin_space = admin_space;
    }

    public int getAdmin_management() {
        return admin_management;
    }

    public void setAdmin_management(int admin_management) {
        this.admin_management = admin_management;
    }

    public int getAdmin_mSpace() {
        return admin_mSpace;
    }

    public void setAdmin_mSpace(int admin_mSpace) {
        this.admin_mSpace = admin_mSpace;
    }

    public int getAdmin_credits() {
        return admin_credits;
    }

    public void setAdmin_credits(int admin_credits) {
        this.admin_credits = admin_credits;
    }

    public int getAdmin_activity() {
        return admin_activity;
    }

    public void setAdmin_activity(int admin_activity) {
        this.admin_activity = admin_activity;
    }

    public int getAdmin_data() {
        return admin_data;
    }

    public void setAdmin_data(int admin_data) {
        this.admin_data = admin_data;
    }

    public int getAdmin_data1() {
        return admin_data1;
    }

    public void setAdmin_data1(int admin_data1) {
        this.admin_data1 = admin_data1;
    }

    public int getAdmin_data2() {
        return admin_data2;
    }

    public void setAdmin_data2(int admin_data2) {
        this.admin_data2 = admin_data2;
    }

    public void setdata(List param){
        for (Object o:param){
            Map map = (Map) o;
            if (map.get("name").equals("admin-settings")){
                admin_settings = (Integer)map.get("access");
                List childrens = (ArrayList)map.get("children");
                for (int i=0;i<4;i++){
                    Map child = (Map)childrens.get(i);
                    if (child.get("name").equals("admin-authority")){
                        admin_authority = (Integer)child.get("access");
                    }else if (child.get("name").equals("admin-rules")){
                        admin_rules = (Integer)child.get("access");
                    }else if (child.get("name").equals("admin-display")){
                        admin_display= (Integer)child.get("access");
                    }else if (child.get("name").equals("admin-space")){
                        admin_space = (Integer)child.get("access");
                    }
                }
            }else if(map.get("name").equals("admin-management")){
                admin_management = (Integer)map.get("access");
                List childrens = (ArrayList)map.get("children");
                for (int i=0;i<3;i++){
                    Map child = (Map)childrens.get(i);
                    if (child.get("name").equals("admin-mSpace")){
                        admin_mSpace = (Integer)child.get("access");
                    }else if (child.get("name").equals("admin-credits")){
                        admin_credits = (Integer)child.get("access");
                    }else if (child.get("name").equals("admin-activity")){
                        admin_activity= (Integer)child.get("access");
                    }
                }
            }else if(map.get("name").equals("admin-data")){
                admin_data = (Integer)map.get("access");
                List childrens = (ArrayList)map.get("children");
                for (int i=0;i<2;i++){
                    Map child = (Map)childrens.get(i);
                    if (child.get("name").equals("admin-data1")){
                        admin_data1 = (Integer)child.get("access");
                    }else if (child.get("name").equals("admin-data2")){
                        admin_data2 = (Integer)child.get("access");
                    }
                }
            }
        }
    }
}
