package com.rj.litewifidebug.litewifidebug.utils;

public class Utility {

    public static String TAG = "Utility";

	/* json 操作 */

    /**
     * 执行命令行操作的工具类
     *
     * @return
     */
    public static boolean exec(String cmd) {

        if (ShellTool.checkRootPermission()) {
            ShellTool.CommandResult result = ShellTool.execCommand(cmd, true);

            if (result != null && result.result == 0) {
                return true;
            } else {
                LogTool.i(TAG, "execCommand result is null or result.result != 0");
            }
        } else {
            LogTool.i(TAG, "ShellTool.checkRootPermission() false");
        }
        return false;
    }

    /**
     * 判断用户是否root的工具类
     *
     * @return
     */
    public static boolean isRoot() {
        try {
            return ShellTool.checkRootPermission();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}