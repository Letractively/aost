package module

import util.TimingDecorator

/**
 * Organize the tests
 * 
 */

public class TelluriumIssueModuleDecorator {
  
  private TimingDecorator decorator

/*
  public TelluriumIssueModuleDecorator() {
    decorator = new TimingDecorator(new TelluriumIssueModule());
    List<String> whiteList = ["getIsssueTypes", "selectIssueType", "searchIssue", "getAdvancedIsssueTypes", "advancedSearchIssue", "clickMoreSearchTips", "getTableHeaderNum", "getHeaderNames", "getDataForColumn", "clickTable", "clickOnTableHeade", "selectDataLayout", "getIssueData"];
    decorator.setWhiteList(whiteList);
    decorator.defineUi();
  }
*/

  public TelluriumIssueModuleDecorator(module) {
    decorator = new TimingDecorator(module);
    List<String> whiteList = ["getIsssueTypes", "selectIssueType", "searchIssue", "getAdvancedIsssueTypes", "advancedSearchIssue", "clickMoreSearchTips", "getTableHeaderNum", "getHeaderNames", "getDataForColumn", "clickTable", "clickOnTableHeade", "selectDataLayout", "getIssueData"];
    decorator.setWhiteList(whiteList);
    decorator.defineUi();
  }

  public long getAccumulatedTime(){
    return decorator.getAccumulatedTime();
  }

  public void resetAccumulatedTime(){
     decorator.resetAccumulatedTime();
  }

  public long getStartTime(){
     return decorator.getStartTime();
  }

  public long getEndTime(){
    return decorator.getEndTime();
  }

  public void testGetIssueTypes(int issueTypeIndex){
        String[] ists = decorator.getIsssueTypes();
        decorator.selectIssueType(ists[issueTypeIndex]);
        decorator.searchIssue("Alter");
    }


    public void testAdvancedSearch(int issueTypeIndex){
        String[] ists = decorator.getAdvancedIsssueTypes();
        decorator.selectIssueType(ists[issueTypeIndex]);

        decorator.advancedSearchIssue(ists[issueTypeIndex], "table", null, null, null, null, null, null, null);
    }


    public void testAdvancedSearchTips(){
        decorator.clickMoreSearchTips();
    }

    public void testIssueData(int column){
        int mcolumn = decorator.getTableHeaderNum();
        List<String> list = decorator.getHeaderNames();
//        list = decorator.getDataForColumn(column);
    }


    public void testGetData(){
      decorator.getIssueData();
    }

    public void testClickIssueResult(int row, int column){
        decorator.clickTable(row, column);
    }

    public void testClickHeader(int index){
        decorator.clickOnTableHeader(index);
    }

    public void testSelectDataLayout(String layout){
        decorator.selectDataLayout(layout);
    }

    public void waitPageLod(){
        decorator.waitPageLod();
    }

    public void pauseTest(){
        decorator.pauseTest(1000);
    }

    public void disableJQuerySelector(){
        decorator.disableJQuerySelector();
    }

    public void useJQuerySelector(){
        decorator.useJQuerySelector();
    }

    public void useDefaultXPathLibrary(){
        decorator.useDefaultXPathLibrary();
    }

    public void useJavascriptXPathLibrary(){
        decorator.useJavascriptXPathLibrary();
    }

    public void enableSelectorCache(){
        decorator.enableSelectorCache();
    }

    public void disableSelectorCache(){
        decorator.disableSelectorCache();
    }

    public void cleanSelectorCache(){
        decorator.cleanSelectorCache();
    }

    public void showCacheUsage(){
        int size = decorator.getCacheSize();
        int maxSize = decorator.getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);
        System.out.println("Cache Usage: ");
        Map<String, Long> usages = decorator.getCacheUsage();
        usages.each {key, val ->
            System.out.println("UID: " + key + ", Count: " + val);
        }
    }
}