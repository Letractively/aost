package org.telluriumsource.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 11, 2010
 */
public class RuntimeEnvironment {
    
    private Map<String, Object> map = new HashMap<String, Object>();

    private boolean useCssSelector = true;

    private boolean useNewEngine = false;

    private boolean useTrace = false;

    private boolean useBundle = false;

    private boolean useScreenshot = false;

    private int maxMacroCmd = 5;

    private String locale = "en_US";
    
    private boolean useClosestMatch = false;

    private boolean useEngineLog = false;

    private String lastUiModule = null;

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

    public String getLastUiModule() {
        return lastUiModule;
    }

    public void setLastUiModule(String lastUiModule) {
        this.lastUiModule = lastUiModule;
    }
}
