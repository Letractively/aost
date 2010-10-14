package org.telluriumsource.framework;

import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.inject.Lookup;
import org.telluriumsource.util.Helper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 11, 2010
 */
public class RuntimeEnvironment implements Lookup, Cloneable {

    public static final String I18N_LOCALE = "tellurium.i18n.locale";
    public static final String CONNECTOR_BASE_URL = "tellurium.connector.baseUrl";
    public static final String CONNECTOR_PORT = "tellurium.connector.port";
    public static final String CONNECTOR_SERVER_HOST = "tellurium.connector.serverHost";
    public static final String CONNECTOR_BROWSER = "tellurium.connector.browser";
    public static final String TEST_EXECUTION_TRACE = "tellurium.test.execution.trace";
    public static final String TEST_EXCEPTION_CAPTURE_SCREEN = "tellurium.test.exception.captureScreenshot";
    public static final String TEST_EXCEPTION_BUG_REPORT = "tellurium.test.exception.bugReport";
    public static final String BUNDLE_USE_MACRO_COMMAND = "tellurium.bundle.useMacroCommand";
    public static final String BUNDLE_MAX_MACRO_COMMAND = "tellurium.bundle.maxMacroCmd";

    private Map<String, Object> map = new HashMap<String, Object>();

    private boolean useNewEngine = false;

    private boolean useCssSelector = true;

    private boolean useClosestMatch = false;

    private boolean useEngineLog = false;

    private String lastUiModule = null;

    private String lastErrorDescription = "";

    private IResourceBundle resourceBundle = null;

    public String getLastErrorDescription() {
        return lastErrorDescription;
    }

    public void setLastErrorDescription(String lastErrorDescription) {
        this.lastErrorDescription = lastErrorDescription;
    }

    public void setEnvironmentVariable(String key, Object val){
        this.map.put(key, val);
    }

    public Object getEnvironmentVariable(String key){
        return this.map.get(key);
    }

    public IResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(IResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void setLastError(Exception e){
        this.lastErrorDescription = Helper.descException(e);
    }

    public String getBaseUrl() {
        return (String) map.get(CONNECTOR_BASE_URL);
    }

    public void setBaseUrl(String baseUrl) {
        map.put(CONNECTOR_BASE_URL, baseUrl);
    }

    public String getLocale() {
        return (String) map.get(I18N_LOCALE);
    }

    public void setLocale(String locale) {
        map.put(I18N_LOCALE, locale);
    }

    public void useLocale(String locale) {
        map.put(I18N_LOCALE, locale);
        if(this.resourceBundle != null){
            String[] split = locale.split("_");
            Locale loc = new Locale(split[0], split[1]);
            this.resourceBundle.updateDefaultLocale(loc);
        }
    }

    public int getServerPort() {
        return (Integer)map.get(CONNECTOR_PORT);
    }

    public void setServerPort(int serverPort) {
        map.put(CONNECTOR_PORT, serverPort);
    }

    public String getServerHost() {
        return (String)map.get(CONNECTOR_SERVER_HOST);
    }

    public void setServerHost(String serverHost) {
        map.put(CONNECTOR_SERVER_HOST, serverHost);
    }

    public String getBrowser() {
        return (String) map.get(CONNECTOR_BROWSER);
    }

    public void setBrowser(String browser) {
        map.put(CONNECTOR_BROWSER, browser);
    }
    
    public boolean isUseCssSelector() {
        return useCssSelector;
    }

    public void setUseCssSelector(boolean useCssSelector) {
        this.useCssSelector = useCssSelector;
    }

    public boolean isUseNewEngine() {
        return useNewEngine;
    }

    public void setUseNewEngine(boolean useNewEngine) {
        this.useNewEngine = useNewEngine;
    }

    public boolean isUseTrace() {
        return (Boolean)map.get(TEST_EXECUTION_TRACE);
    }

    public void setUseTrace(boolean useTrace) {
        map.put(TEST_EXECUTION_TRACE, useTrace);
    }

    public boolean isUseBundle() {
        return (Boolean)map.get(BUNDLE_USE_MACRO_COMMAND);
    }

    public void setUseBundle(boolean useBundle) {
        map.put(BUNDLE_USE_MACRO_COMMAND, useBundle);
    }

    public boolean isUseScreenshot() {
        return (Boolean)map.get(TEST_EXCEPTION_CAPTURE_SCREEN);
    }

    public void setUseScreenshot(boolean useScreenshot) {
        map.put(TEST_EXCEPTION_CAPTURE_SCREEN, useScreenshot);
    }

    public int getMaxMacroCmd() {
        return (Integer)map.get(BUNDLE_MAX_MACRO_COMMAND);
    }

    public void setMaxMacroCmd(int maxMacroCmd) {
        map.put(BUNDLE_MAX_MACRO_COMMAND, maxMacroCmd);
    }

    public boolean isUseClosestMatch() {
        return useClosestMatch;
    }

    public void setUseClosestMatch(boolean useClosestMatch) {
        this.useClosestMatch = useClosestMatch;
    }

    public boolean isUseEngineLog() {
        return useEngineLog;
    }

    public void setUseEngineLog(boolean useEngineLog) {
        this.useEngineLog = useEngineLog;
    }

    public boolean isUseBugReport() {
        return (Boolean)map.get(TEST_EXCEPTION_BUG_REPORT);
    }

    public void setUseBugReport(boolean useBugReport) {
        map.put(TEST_EXCEPTION_BUG_REPORT, useBugReport);
    }

    public String getLastUiModule() {
        return lastUiModule;
    }

    public void setLastUiModule(String lastUiModule) {
        this.lastUiModule = lastUiModule;
    }

    public boolean hasKey(String name){
        return map.containsKey(name);
    }

    public boolean has(String name){
        return map.containsKey(name);
    }

    public Object getByName(String name) {
        return map.get(name);
    }

    public <T> T getByClass(Class<T> clazz) {
        return (T)map.get(clazz.getCanonicalName());
    }

    public String toString(){
        StringBuffer sb = new StringBuffer(64);
        sb.append("RuntimeEnvironment: [")
            .append("useCssSelector: ").append(useCssSelector).append(",")
            .append("useNewEngine: ").append(useNewEngine).append(",")
            .append("useClosestMatch: ").append(useClosestMatch).append(",")
            .append("useEngineLog: ").append(useEngineLog).append(",")
            .append("lastError: ").append(this.lastErrorDescription);
        if(!map.isEmpty()){
            Set<String> keySet = map.keySet();
            for(String key: keySet){
                sb.append(",").append(key).append(": ").append(map.get(key));
            }
        }

        return sb.toString();
    }

    public RuntimeEnvironment clone() {
        RuntimeEnvironment newEnv = new RuntimeEnvironment();
        newEnv.setUseCssSelector(useCssSelector);
        newEnv.setUseNewEngine(useNewEngine);
        newEnv.setUseClosestMatch(useClosestMatch);
        newEnv.setUseEngineLog(useEngineLog);
        newEnv.setLastUiModule(lastUiModule);

        Set<String> keySet = map.keySet();
        for(String key: keySet){
            newEnv.setEnvironmentVariable(key, map.get(key));
        }

        return newEnv;
    }
}
