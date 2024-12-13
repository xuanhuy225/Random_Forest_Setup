package me.common.libs;

public class SystemProp {
    /**
     * run the application with additional parameter -Dzappname=your_app_name or
     * -Dzapp=your_app_name
     *
     * @return
     */
    public static String getAppName() {
        String appName = getProp("zappname");
        if (appName == null) {
            appName = getProp("zapp");
        }

        if (appName == null) {
            appName = "test-service";
        }

        return appName;
    }

    /**
     * run the application with additional parameter -Dzappprof=your_app_env or
     * -Dzenv=your_app_env
     *
     * @return
     */
    public static String getAppEnv() {
        String appEnv = getProp("zappprof");
        if (appEnv == null) {
            appEnv = getProp("zenv");
        }

        if (appEnv == null) {
            appEnv = "development";
        }

        return appEnv;
    }

    public static String getConfDir() {
        return getProp("zconfdir", "conf");
    }

    public static String getProp(String key, String def) {
        String prop = null;

        try {
            prop = System.getProperty(key, def);
        } catch (Exception ex) {
        }

        return prop;
    }

    public static String getProp(String key) {
        String prop = null;

        try {
            prop = System.getProperty(key);
        } catch (Exception ex) {
        }

        return prop;
    }

    public static String setProp(String key, String value) {
        return System.setProperty(key, value);
    }

    public static String javaVersion() {
        return getProp("java.version");
    }
}
