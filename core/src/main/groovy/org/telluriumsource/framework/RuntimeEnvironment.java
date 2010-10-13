package org.telluriumsource.framework;

import org.telluriumsource.crosscut.i18n.IResourceBundle;
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
public class RuntimeEnvironment implements Cloneable {
    
    private Map<String, Object> map = new HashMap<String, Object>();

    private boolean useNewEngine = false;

    private boolean useCssSelector = true;

    private boolean useClosestMatch = false;

    private boolean useEngineLog = false;

    private String lastUiModule = null;

    protected String lastErrorDescription = "";

    private IResourceBundle resourceBundle = null;

    public void addEnvironmentVariable(String key, Object val){
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
        return (String) map.get("tellurium.connector.baseUrl");
    }

    public void setBaseUrl(String baseUrl) {
        map.put("tellurium.connector.baseUrl", baseUrl);
    }

    public void useLocale(String locale) {
        map.put("tellurium.i18n.locale", locale);
        if(this.resourceBundle != null){
            String[] split = locale.split("_");
            Locale loc = new Locale(split[0], split[1]);
            this.resourceBundle.updateDefaultLocale(loc);
        }
    }

    public int getServerPort() {
        return (Integer)map.get("tellurium.connector.port");
    }

    public void setServerPort(int serverPort) {
        map.put("tellurium.connector.port", serverPort);
    }

    public String getServerHost() {
        return (String)map.get("tellurium.connector.serverHost");
    }

    public void setServerHost(String serverHost) {
        map.put("tellurium.connector.serverHost", serverHost);
    }

    public String getBrowser() {
        return (String) map.get("tellurium.connector.browser");
    }

    public void setBrowser(String browser) {
        map.put("tellurium.connector.browser", browser);
    }

    public void setCustomEnvironment(String name, Object value) {
        map.put(name, value);
    }

    public Object getCustomEnvironment(String name) {
        return map.get(name);
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
        return (Boolean)map.get("tellurium.test.execution.trace");
    }

    public void setUseTrace(boolean useTrace) {
        map.put("tellurium.test.execution.trace", useTrace);
    }

    public boolean isUseBundle() {
        return (Boolean)map.get("tellurium.bundle.useMacroCommand");
    }

    public void setUseBundle(boolean useBundle) {
        map.put("tellurium.bundle.useMacroCommand", useBundle);
    }

    public boolean isUseScreenshot() {
        return (Boolean)map.get("tellurium.test.exception.captureScreenshot");
    }

    public void setUseScreenshot(boolean useScreenshot) {
        map.put("tellurium.test.exception.captureScreenshot", useScreenshot);
    }

    public int getMaxMacroCmd() {
//        return maxMacroCmd;
        return (Integer)map.get("tellurium.bundle.maxMacroCmd");
    }

    public void setMaxMacroCmd(int maxMacroCmd) {
        map.put("tellurium.bundle.maxMacroCmd", maxMacroCmd);
    }

    public String getLocale() {
        return (String) map.get("tellurium.i18n.locale");
    }

    public void setLocale(String locale) {
        map.put("tellurium.i18n.locale", locale);
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
        return (Boolean)map.get("tellurium.test.exception.bugReport");
    }

    public void setUseBugReport(boolean useBugReport) {
        map.put("tellurium.test.exception.bugReport", useBugReport);
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

    public String toString(){
        StringBuffer sb = new StringBuffer(64);
        sb.append("RuntimeEnvironment: [").append("serverHost: ").append(getServerHost()).append(",")
            .append("serverPort: ").append(getServerPort()).append(",")
            .append("baseUrl: ").append(getBaseUrl()).append(",")
            .append("browser: ").append(getBrowser()).append(",")
            .append("useCssSelector: ").append(useCssSelector).append(",")
            .append("useTrace: ").append(isUseTrace()).append(",")
            .append("useBundle: ").append(isUseBundle()).append(",")
            .append("maxMacroCmd: ").append(getMaxMacroCmd()).append(",")
            .append("useScreenshot: ").append(isUseScreenshot()).append(",")
            .append("useNewEngine: ").append(useNewEngine).append(",")
            .append("useClosestMatch: ").append(useClosestMatch).append(",")
            .append("useEngineLog: ").append(useEngineLog).append(",")
            .append("useBugReport: ").append(isUseBugReport()).append(",")
            .append("locale: ").append(getLocale()).append(",")
            .append("lastError: ").append(this.lastErrorDescription);
        if(!map.isEmpty()){
            Set<String> keySet = map.keySet();
            for(String key: keySet){
                sb.append(",").append(key).append(": ").append(map.get(key));
            }
        }

        return sb.toString();
    }

    public RuntimeEnvironment clone(){

        RuntimeEnvironment newEnv = new RuntimeEnvironment();
        newEnv.setUseCssSelector(useCssSelector);
        newEnv.setUseNewEngine(useNewEngine);
        newEnv.setUseClosestMatch(useClosestMatch);
        newEnv.setUseEngineLog(useEngineLog);
        newEnv.setLastUiModule(lastUiModule);

        Set<String> keySet = map.keySet();
        for(String key: keySet){
            newEnv.setCustomEnvironment(key, map.get(key));
        }

        return newEnv;
    }
}
