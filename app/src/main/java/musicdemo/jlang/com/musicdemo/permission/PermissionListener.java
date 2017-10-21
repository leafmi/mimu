package musicdemo.jlang.com.musicdemo.permission;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermission);

}
