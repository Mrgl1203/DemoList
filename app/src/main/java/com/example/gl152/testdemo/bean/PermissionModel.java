package com.example.gl152.testdemo.bean;

/**
 * Created by gl152 on 2017/5/17.
 */

public class PermissionModel {
    public String permission;
    public String requestreason;
    public String permissionname;
    public int permissioncode;

    public PermissionModel(String permission, String requestreason, String permissionname, int permissioncode) {
        this.permission = permission;
        this.requestreason = requestreason;
        this.permissionname = permissionname;
        this.permissioncode = permissioncode;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRequestreason() {
        return requestreason;
    }

    public void setRequestreason(String requestreason) {
        this.requestreason = requestreason;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    public int getPermissioncode() {
        return permissioncode;
    }

    public void setPermissioncode(int permissioncode) {
        this.permissioncode = permissioncode;
    }
}
