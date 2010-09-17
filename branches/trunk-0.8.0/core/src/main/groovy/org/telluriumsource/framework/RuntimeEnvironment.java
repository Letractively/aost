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

    private String serverHost = "localhost";

    private int serverPort = 4444;

    private String browser = null;

    private String baseUrl = "https://localhost:8080";

    private boolean useCssSelector = true;

    private boolean useNewEngine = false;

    private boolean useTrace = false;

    private boolean useBundle = false;

    private boolean useScreenshot = false;

    private int maxMacroCmd = 5;

    private String locale = "en_US";
    
    private boolean useClosestMatch = false;

    private boolean useEngineLog = false;

    protected boolean useBugReport = false;

    private String lastUiModule = null;

    protected String lastErrorDescription = "";

    private IResourceBundle resourceBundle = null;

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
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void useLocale(String locale) {
        this.locale = locale;
        if(this.resourceBundle != null){
            String[] split = locale.split("_");
            Locale loc = new Locale(split[0], split[1]);
            this.resourceBundle.updateDefaultLocale(loc);
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
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
        return useTrace;
    }

    public void setUseTrace(boolean useTrace) {
        this.useTrace = useTrace;
    }

    public boolean isUseBundle() {
        return useBundle;
    }

    public void setUseBundle(boolean useBundle) {
        this.useBundle = useBundle;
    }

    public boolean isUseScreenshot() {
        return useScreenshot;
    }

    public void setUseScreenshot(boolean useScreenshot) {
        this.useScreenshot = useScreenshot;
    }

    public int getMaxMacroCmd() {
        return maxMacroCmd;
    }

    public void setMaxMacroCmd(int maxMacroCmd) {
        this.maxMacroCmd = maxMacroCmd;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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
        return useBugReport;
    }

    public void setUseBugReport(boolean useBugReport) {
        this.useBugReport = useBugReport;
    }

    public String getLastUiModule() {
        return lastUiModule;
    }

    public void setLastUiModule(String lastUiModule) {
        this.lastUiModule = lastUiModule;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer(64);
        sb.append("RuntimeEnvironment: [").append("serverHost: ").append(serverHost).append(",")
            .append("serverPort: ").append(serverPort).append(",")
            .append("baseUrl: ").append(baseUrl).append(",")
            .append("browser: ").append(browser).append(",")
            .append("useCssSelector: ").append(useCssSelector).append(",")
            .append("useTrace: ").append(useTrace).append(",")
            .append("useBundle: ").append(useBundle).append(",")
            .append("maxMacroCmd: ").append(maxMacroCmd).append(",")
            .append("useScreenshot: ").append(useScreenshot).append(",")
            .append("useNewEngine: ").append(useNewEngine).append(",")
            .append("useClosestMatch: ").append(useClosestMatch).append(",")
            .append("useEngineLog: ").append(useEngineLog).append(",")
            .append("useBugReport: ").append(useBugReport).append(",")
            .append("locale: ").append(locale).append(",")
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
        newEnv.setServerHost(serverHost);
        newEnv.setServerPort(serverPort);
        newEnv.setBaseUrl(baseUrl);
        newEnv.setBrowser(browser);
        newEnv.setUseCssSelector(useCssSelector);
        newEnv.setUseNewEngine(useNewEngine);
        newEnv.setUseTrace(useTrace);
        newEnv.setUseBundle(useBundle);
        newEnv.setUseScreenshot(useScreenshot);
        newEnv.setMaxMacroCmd(maxMacroCmd);
        newEnv.setUseClosestMatch(useClosestMatch);
        newEnv.setUseEngineLog(useEngineLog);
        newEnv.setLastUiModule(lastUiModule);
        newEnv.setUseBugReport(useBugReport);
        newEnv.setLocale(locale);

        Set<String> keySet = map.keySet();
        for(String key: keySet){
            newEnv.setCustomEnvironment(key, map.get(key));
        }

        return newEnv;
    }
}
