package com.example.hugh.interesting.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CheckPermissionUtils {

    private static CheckPermissionUtils instance;

    private static List<String> permissionList;

    private int requestCode;

    private PermissionCallBack permissionCallBack;

    private PermissionCallBack permissionErrorCallBack;
    private Context mContext;


    private CheckPermissionUtils() {
    }

    public static CheckPermissionUtils getInstance() {
        if (null == instance) {
            instance = new CheckPermissionUtils();
            permissionList = new ArrayList<>();
        }
        return instance;
    }

    public boolean checkSelfPermission(String permission) {
        try {
            if (permission == null) {
                throw new IllegalArgumentException("permission is null");
            }

            int checkSelfPermission = mContext.getApplicationContext().checkPermission(permission, Process.myPid(), Process.myUid());
            if (checkSelfPermission == 0) {
                return true;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return false;
    }

    /**
     * 申请权限
     *
     * @param context
     * @param requestCode
     * @param permission
     */
    public void initPermission(Context context, int requestCode, PermissionCallBack permissionCallBack, String... permission) {
        this.requestCode = requestCode;
        this.permissionCallBack = permissionCallBack;
        permissionList.clear();
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission[i]);
                }
            }
            if (permissionList.size() > 0) {
                String[] permissionArray = new String[permissionList.size()];
                for (int i = 0; i < permissionList.size(); i++) {
                    permissionArray[i] = permissionList.get(i);
                }
                ActivityCompat.requestPermissions((Activity) context, permissionArray, requestCode);
            } else {
                permissionCallBack.permissionNext();
            }
        } else {
            permissionCallBack.permissionNext();
        }
    }

    /**
     * 申请权限
     *
     * @param context
     * @param requestCode
     * @param permission
     */
    public void initPermissionCamera(Context context, int requestCode, PermissionCallBack permissionCallBack, PermissionCallBack permissionErrorCallBack, String... permission) {
        this.mContext = context;
        this.requestCode = requestCode;
        this.permissionCallBack = permissionCallBack;
        this.permissionErrorCallBack = permissionErrorCallBack;
        permissionList.clear();
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission[i]);
                }
            }
            if (permissionList.size() > 0) {
                String[] permissionArray = new String[permissionList.size()];
                for (int i = 0; i < permissionList.size(); i++) {
                    permissionArray[i] = permissionList.get(i);
                }
                ActivityCompat.requestPermissions((Activity) context, permissionArray, requestCode);
            } else {
                permissionCallBack.permissionNext();
            }
        } else {
            permissionCallBack.permissionNext();
        }
    }


    /**
     * 申请权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
                    }
                }
                if (null != permissionCallBack) {
                    permissionCallBack.permissionNext();
                }
            } else {
                Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "请检查请求码是否一致", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 申请权限的回调，需要全部满足
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResultAnd(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (null != permissionCallBack) {
                    permissionCallBack.permissionNext();
                }
            } else {
                Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "请检查请求码是否一致", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 申请权限的回调，不用全部满足
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResultOr(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
                    }
                }
                if (null != permissionCallBack) {
                    permissionCallBack.permissionNext();
                }
            } else {
                Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "请检查请求码是否一致", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 申请权限的回调，如果没有全部通过，返回错误callback
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResultCamera(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isRefuse = true;
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        isRefuse = false;
                        Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    }
                }
                if (isRefuse) {
                    permissionCallBack.permissionNext();
                } else {
                    permissionErrorCallBack.permissionNext();
                }
            } else {
                Toast.makeText(mContext, "权限被拒绝，可能会影响某些功能使用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "请检查请求码是否一致", Toast.LENGTH_SHORT).show();
        }
    }
}
