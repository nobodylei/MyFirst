package com.cxhy.www.fasetest.permission;

public interface PermissionResult {
    void onGranted();

    void onDenied();
}
