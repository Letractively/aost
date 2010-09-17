package org.telluriumsource.dsl

import org.telluriumsource.entity.EngineState
import org.telluriumsource.entity.CacheUsageResponse
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse
import org.telluriumsource.exception.UiObjectNotFoundException
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.entity.DiagnosisRequest
import org.telluriumsource.entity.KeyValuePairs
import org.telluriumsource.ui.builder.AllPurposeObjectBuilder
import org.telluriumsource.ui.object.AllPurposeObject
import org.telluriumsource.exception.NotWidgetObjectException
import org.telluriumsource.ui.widget.Widget

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Sep 10, 2010
 * 
 */
class TelluriumApi extends BaseDslContext {

  UiObject walkToWithException(WorkflowContext context, String uid) {
//      env.lastDslContext = this;
    UiObject obj = ui.walkTo(context, uid);
    if (obj != null) {
      context.attachMetaCmd(uid, obj.amICacheable(), true);
//        context.putContext(WorkflowContext.DSLCONTEXT, this);
      env.lastUiModule = getUiModuleId(uid);

      return obj;
    }

    throw new UiObjectNotFoundException(i18nBundle.getMessage("BaseDslContext.CannotFindUIObject", uid));
  }

  public void enableClosestMatch() {
      env.setUseClosestMatch(true);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useClosestMatch(context, true);
  }

  public void disableClosestMatch() {
      env.setUseClosestMatch(false);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useClosestMatch(context, false)
  }

  public void enableCssSelector() {
      env.setUseCssSelector(true);
  }

  public void disableCssSelector() {
      env.setUseCssSelector(false);
  }

  public void cleanCache() {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.cleanCache(context)
  }

  public void setCacheMaxSize(int size) {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.cleanCache(context)
  }

  public int getCacheSize() {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return extension.getCacheSize(context).intValue();
  }

  public int getCacheMaxSize() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     return extension.getCacheMaxSize(context).intValue()
  }

  public String getCacheUsage() {
     CacheUsageResponse resp = this.getCacheUsageResponse();

     return resp.toString();
  }

  public CacheUsageResponse getCacheUsageResponse() {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    List out = extension.getCacheUsage(context);
    CacheUsageResponse resp = new CacheUsageResponse();
    resp.parseJSON(out);

    return resp;
  }

  public void useDiscardNewCachePolicy() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.useDiscardNewCachePolicy(context)
  }

  public void useDiscardOldCachePolicy() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.useDiscardOldCachePolicy(context)
  }

  public void useDiscardLeastUsedCachePolicy() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.useDiscardLeastUsedCachePolicy(context)
  }

  public void useDiscardInvalidCachePolicy() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    extension.useDiscardInvalidCachePolicy(context)
  }

  public String getCurrentCachePolicy() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return extension.getCachePolicyName(context)
  }

  public def customUiCall(String uid, String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    walkToWithException(context, uid);
    Object[] list = [context, uid, args].flatten();

    return extension.invokeMethod(method, list);
  }

  def customDirectCall(String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    Object[] list = [context, args].flatten()
    return extension.invokeMethod(method, list)
  }

  public void triggerEventOn(String uid, String event) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid);
    extension.triggerEvent(context, uid, event);
  }

  public void click(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.click() {loc, String[] events ->
      eventHandler.click(context, uid, events);
    }
  }

  public void doubleClick(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
      eventHandler.doubleClick(context, uid, events)
    }
  }

  public void clickAt(String uid, String coordination) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
      eventHandler.clickAt(context, uid, coordination, events)
    }
  }

  public void check(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.check() {loc, String[] events ->
      eventHandler.check(context, uid, events)
    }
  }

  public void uncheck(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
      eventHandler.uncheck(context, uid, events)
    }
  }

  public void type(String uid, Object input) {
    String str = (input==null) ? "" : input.toString();
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.type(str) {loc, String[] events ->
      eventHandler.type(context, uid, str, events);
    }
  }

  public void keyPress(String uid, String key) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.keyPress(key) {loc ->
      extension.keyPress(context, uid, key);
    }
  }

  public void keyDown(String uid, String key) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.keyDown(key) {loc ->
      extension.keyDown(context, uid, key)
    }
  }

  public void keyUp(String uid, String key) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.keyUp(key) {loc ->
      extension.keyUp(context, uid, key);
    }
  }

  public void focus(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.focus() {loc ->
      extension.focus(context, uid);
    }
  }

  public void fireEvent(String uid, String eventName) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.fireEvent(eventName) {loc ->
      extension.fireEvent(context, uid, eventName)
    }
  }

  public void keyType(String uid, Object input) {
    String str = (input == null) ? "" : input.toString();
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.keyType(str) {loc, String[] events ->
      eventHandler.keyType(context, uid, str, events)
    }
  }

  public void typeAndReturn(String uid, Object input) {
    String str = (input==null) ? "" : input.toString();
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.typeAndReturn(str) {loc, String[] events ->
      eventHandler.typeAndReturn(context, uid, str, events)
    }
  }

  public void clearText(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.clearText() {loc, String[] events ->
      eventHandler.clearText(context, uid, events)
    }
  }

  public void select(String uid, String target) {
    selectByLabel(uid, target);
  }

  public void selectByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
      eventHandler.select(context, uid, optloc, events)
    }
  }

  public void selectByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
      eventHandler.select(context, uid, optloc, events)
    }
  }

  public void selectByIndex(String uid, int target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectByIndex(target) {loc, optloc, String[] events ->
      eventHandler.select(context, uid, optloc, events)
    }
  }

  public void selectById(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectById(target) {loc, optloc, String[] events ->
      eventHandler.select(context, uid, optloc, events)
    }
  }

  public void addSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
      eventHandler.addSelection(context, uid, optloc)
    }
  }

  public void addSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
      eventHandler.addSelection(context, uid, optloc)
    }
  }

  public void removeSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
      eventHandler.removeSelection(context, uid, optloc)
    }
  }

  public void removeSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
      eventHandler.removeSelection(context, uid, optloc)
    }
   }

  public void removeAllSelections(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.removeAllSelections() {loc ->
      eventHandler.removeAllSelections(context, uid)
    }
  }

  public String[] getSelectOptions(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectOptions() {loc ->
      accessor.getSelectOptions(context, uid)
    }
  }

  public String[] getSelectValues(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectValues() {loc ->
      accessor.getSelectValues(context, uid);
    }
  }

  public String[] getSelectedLabels(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedLabels() {loc ->
      accessor.getSelectedLabels(context, uid);
    }
  }

  public String getSelectedLabel(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedLabel() {loc ->
      accessor.getSelectedLabel(context, uid);
    }
  }

  public String[] getSelectedValues(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedValues() {loc ->
      accessor.getSelectedValues(context, uid)
    }
  }

  public String getSelectedValue(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedValue() {loc ->
      accessor.getSelectedValue(context, uid);
    }
  }

  public String[] getSelectedIndexes(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedIndexes() {loc ->
      accessor.getSelectedIndexes(context, uid);
    }
  }

  public String getSelectedIndex(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedIndex() {loc ->
      accessor.getSelectedIndex(context, uid);
    }
  }

  public String[] getSelectedIds(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedIds() {loc ->
      accessor.getSelectedIds(context, uid);
    }
  }

  public String getSelectedId(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.getSelectedId() {loc ->
      accessor.getSelectedId(context, uid);
    }
  }

  public boolean isSomethingSelected(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.isSomethingSelected() {loc ->
      accessor.isSomethingSelected(context, uid);
    }
  }

  public String waitForText(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
      accessor.waitForText(context, uid, tmo)
    }
  }

  public boolean isElementPresent(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    walkToWithException(context, uid);
    return accessor.isElementPresent(context, uid);
  }

  public boolean isVisible(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.isVisible() {loc ->
        accessor.isVisible(context, uid);
      }
  }

  public boolean isChecked(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.isChecked() {loc ->
        accessor.isChecked(context, uid);
      }
  }

  public boolean isEnabled(String uid) {
      return !isDisabled(uid);
  }

  public boolean waitForElementPresent(String uid, int timeout) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.waitForElementPresent(timeout) {loc ->
        return accessor.waitForElementPresent(context, uid, timeout);
      }

  }

  public boolean waitForElementPresent(String uid, int timeout, int step) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.waitForElementPresent(timeout, step) {loc ->
        return accessor.waitForElementPresent(context, uid, timeout, step)
      }
  }

  public String getText(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getText() {loc ->
        return accessor.getText(context, uid);
      }
  }

  public String getValue(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getValue() {loc ->
        return accessor.getValue(context, uid);
      }
  }

  public String getLink(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      walkToWithException(context, uid)?.getLink() {loc, attr ->
        return extension.getAttribute(context, uid, attr);
      }
  }

  public String getImageSource(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getImageSource() {loc, attr ->
        return extension.getAttribute(context, uid, attr);
      }
  }

  public String getImageAlt(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
        return extension.getAttribute(context, uid, attr);
      }
  }

  public String getImageTitle(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
        return extension.getAttribute(context, uid, attr);
      }
  }

  public boolean isEditable(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    return walkToWithException(context, uid)?.isEditable() {loc ->
      return accessor.isEditable(context, uid)
    }
  }

  public void submit(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      walkToWithException(context, uid)?.submit() {loc ->
        eventHandler.submit(context, uid);
      }
  }

  public void mouseOver(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseOver() {loc ->
        eventHandler.mouseOver(context, uid);
      }
  }

  public void mouseOut(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseOut() {loc ->
        eventHandler.mouseOut(context, uid);
      }
  }

  public void dragAndDrop(String uid, String movementsString) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
        eventHandler.dragAndDrop(context, uid, movementsString);
      }
  }

  public void dragAndDropTo(String sourceUid, String targetUid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      walkToWithException(context, sourceUid);
      walkToWithException(context, targetUid);
      eventHandler.dragAndDropToObject(context, sourceUid, targetUid);
  }

  public void mouseDown(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseDown() {loc ->
        eventHandler.mouseDown(context, uid);
      }
  }

  public void mouseDownRight(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseDownRight() {loc ->
        eventHandler.mouseDownRight(context, uid);
      }
  }

  public void mouseDownAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseDownAt() {loc ->
        eventHandler.mouseDownAt(context, uid, coordinate);
      }
  }

  public void mouseDownRightAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
        eventHandler.mouseDownRightAt(context, uid, coordinate);
      }
  }

  public void mouseUp(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseUp() {loc ->
        eventHandler.mouseUp(context, uid);
      }
  }

  public void mouseUpRight(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseUpRight() {loc ->
        eventHandler.mouseUpRight(context, uid);
      }
  }

  public void mouseUpRightAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
        eventHandler.mouseUpRightAt(context, uid, coordinate)
      }
  }

  public void mouseMove(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseMove() {loc ->
        eventHandler.mouseMove(context, uid);
      }
  }

  public void mouseMoveAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid)?.mouseMoveAt() {loc ->
        eventHandler.mouseMoveAt(context, uid, coordinate)
      }
  }

  public String[] getCSS(String uid, String cssName) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return walkToWithException(context, uid)?.getCSS(cssName) {loc ->

        def out = extension.getCSS(context, uid, cssName)

        return (ArrayList) parseSeleniumJSONReturnValue(out)
      }
  }

  public String[] getAllTableCellText(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid).getAllTableCellText(){loc, cell ->
          def out = extension.getAllTableBodyText(context, uid);

          return (ArrayList) parseSeleniumJSONReturnValue(out);
      }
  }

  public String[] getAllTableCellTextForTbody(String uid, int index) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getAllTableCellTextForTbody(index) {loc, cell ->
        def out = extension.getAllTableCellTextForTbody(context, uid, index);

        return (ArrayList) parseSeleniumJSONReturnValue(out);
      }
  }

  public int getTableHeaderColumnNum(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableHeaderColumnNum(){loc, optloc ->

        return extension.getTableHeaderColumnNum(context, uid);
      }
  }

  public int getTableFootColumnNum(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableFootColumnNum(){loc, optloc ->

        return extension.getTableFootColumnNum(context, uid);
      }
  }

  public int getTableMaxRowNum(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableMaxRowNum(){loc, optloc ->

        return extension.getTableMaxRowNum(context, uid);
      }
  }

  public int getTableMaxColumnNum(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableMaxColumnNum(){loc, optloc ->

        return extension.getTableMaxColumnNum(context, uid);
      }
  }

  public int getTableMaxRowNumForTbody(String uid, int ntbody) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableMaxRowNumForTbody(){loc, optloc ->

        return extension.getTableMaxRowNumForTbody(context, uid, ntbody);
      }
  }

  public int getTableMaxColumnNumForTbody(String uid, int ntbody) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableMaxColumnNumForTbody(){loc, optloc ->

        return extension.getTableMaxColumnNumForTbody(context, uid, ntbody);
      }
  }

  public int getTableMaxTbodyNum(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getTableMaxTbodyNum(){loc, optloc ->

        return extension.getTableMaxTbodyNum(context, uid);
      }
  }

  public int getRepeatNum(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getRepeatNum(){loc ->

        return extension.getRepeatNum()(context, uid);
      }
  }

  public int getListSize(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.getListSize(){loc ->

        return extension.getListSize()(context, uid);
      }
  }

  public boolean isDisabled(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return walkToWithException(context, uid).isDisabled() {loc ->

        return extension.isDisabled(context, uid);
      }
  }

  public Object getAttribute(String uid, String attribute) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return walkToWithException(context, uid).getAttribut() {loc ->

        extension.getAttribut(context, uid, attribute);
      }
  }

  public boolean hasCssClass(String uid, String cssClass) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    String[] strings = walkToWithException(context, uid)?.hasCssClass() {loc, classAttr ->

      String clazz = extension.getAttribut(context, uid, cssClass);
      if (clazz != null && clazz.trim().length() > 0) {
        return clazz.split(" ");
      }
      return null;
    }

    if (strings?.length) {
      for (i in 0..<strings?.length) {
        if (cssClass.equalsIgnoreCase(strings[i])) {
          return true
        }
      }
    }

    return false
  }

  public void toggle(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      walkToWithException(context, uid).toggle() {loc ->

        extension.toggle(context, uid);
      }
  }

  public void show(String uid, int delay) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    def obj = walkToWithException(context, uid)
    if (obj != null) {
      extension.showUi(context, uid);
      pause(delay);
      extension.cleanUi(context, uid);
      pause(200);
    }
  }

  public void startShow(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    def obj = walkToWithException(context, uid);
    if (obj != null) {
      extension.showUi(context, uid);
      pause(200);
    }
  }

  public void endShow(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    def obj = walkToWithException(context, uid)
    if (obj != null) {
      extension.cleanUi(context, uid);
      pause(200);
    }
  }

  public void validate(String uid) {
      UiModuleValidationResponse response = getUiModuleValidationResult(uid);
      response?.showMe();
  }

  public UiModuleValidationResponse getUiModuleValidationResult(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      def obj = walkToWithException(context, uid);
      JSONArray arr = new JSONArray();
      context.setJSONArray(arr);
      obj.treeWalk(context);
      JSONArray jsa = context.getJSONArray();

      def out = extension.getValidateUiModule(context, jsa);

      return parseUseUiModuleResponse(out);
  }

  public DiagnosisResponse getDiagnosisResult(String uid) {
      return getDiagnosisResult(uid, null);
  }

  public DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return walkToWithException(context, uid)?.diagnose() {loc ->

        if(options == null)
          options = new DiagnosisOption();

        DiagnosisRequest request = new DiagnosisRequest(uid, null, loc, options);

        def out = extension.getDiagnosisResponse(context, uid, request.toJSON());

        return new DiagnosisResponse(parseSeleniumJSONReturnValue(out));
      }
  }

  public void diagnose(String uid) {
    DiagnosisResponse resp = this.getDiagnosisResult(uid);
    resp.showMe();
  }

  public void diagnose(String uid, DiagnosisOption options) {
    DiagnosisResponse resp = getDiagnosisResult(uid, options);
    resp.showMe();
  }

  public java.util.List getHTMLSourceResponse(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)
    def out = extension.getHTMLSource(context, uid)

    return parseSeleniumJSONReturnValue(out);
  }

  public void getHTMLSource(String uid) {
    java.util.List list = getHTMLSourceResponse(uid);
    if (list != null && list.size() > 0) {
      list.each {Map map ->
        String key = map.get("key");
        String val = map.get("val");
        println(key + ": ");
        println("");
        println(val);
        println("");
      }
    }
  }

  public UiByTagResponse getUiByTag(String tag, Map filters) {
      KeyValuePairs pairs = new KeyValuePairs(filters);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      def out = extension.getUiByTag(context, tag, pairs.toJSON());

      UiByTagResponse response = new UiByTagResponse(tag, filters, out);
      AllPurposeObjectBuilder builder = new AllPurposeObjectBuilder();
      if(out != null && out.size() > 0){
        for(int i=0; i<out.size(); i++){
          AllPurposeObject obj = builder.build(out[i], out[i], tag, filters, false);
          ui.addUiObjectToRegistry(obj);
        }
      }

      return response;
  }

  public void removeMarkedUids(String tag) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.removeMarkedUids(context, tag);
  }

  public void reset(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      walkToWithException(context, uid).reset() {loc ->

        extension.reset(context, uid);
      }
  }

  def getWidget(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)
    if (!(obj instanceof Widget)) {
      println i18nBundle.getMessage("DslContext.UIObject", {uid})

      throw new NotWidgetObjectException(i18nBundle.getMessage("DslContext.UIObject", uid))
    }

    //add reference xpath for the widget
    Widget widget = (Widget) obj
    widget.updateParentRef(context.getReferenceLocator())

    return obj
  }

  def onWidget(String uid, String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)
    if (!(obj instanceof Widget)) {
      println i18nBundle.getMessage("DslContext.UIObject", uid)

      throw new NotWidgetObjectException(i18nBundle.getMessage("DslContext.UIObject", uid))
    } else {
      if (obj.metaClass.respondsTo(obj, method, args)) {

        //add reference xpath for the widget
        Widget widget = (Widget) obj
        widget.updateParentRef(context.getReferenceLocator())

        return obj.invokeMethod(method, args)
      } else {

        throw new MissingMethodException(method, obj.metaClass.class, args)
      }
    }
  }

  public void selectFrame(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectFrame() {String loc ->
      eventHandler.selectFrame(context, uid);
    }
  }

  public void selectFrameByIndex(int index) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    eventHandler.selectFrame(context, "index=${index}")
  }

  public void selectParentFrameFrom(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectParentFrame() {String loc ->
      eventHandler.selectFrame(context, uid);
    }
  }

  public void selectTopFrameFrom(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectTopFrame() {String loc ->
      eventHandler.selectFrame(context, uid);
    }
  }

  public boolean getWhetherThisFrameMatchFrameExpression(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.getWhetherThisFrameMatchFrameExpression(target) {String loc, String tgt ->
      accessor.getWhetherThisFrameMatchFrameExpression(context, uid, tgt);
    }
  }

  public void waitForFrameToLoad(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.waitForFrameToLoad(timeout) {String loc, int tmo ->
      accessor.waitForFrameToLoad(context, uid, Integer.toString(tmo));
    }
  }

  public void openWindow(String uid, String url) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.openWindow(url) {String loc, String aurl ->
      eventHandler.openWindow(context, aurl, uid);
    }
  }

  public void selectWindow(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.selectWindow() {String loc ->
      eventHandler.selectWindow(context, uid);
    }
  }

  public void closeWindow(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.closeWindow() {String loc ->
      eventHandler.closeWindow(context, uid);
    }
  }

  public void waitForPopUp(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.waitForPopUp(timeout) {String loc ->
      accessor.waitForPopUp(context, uid, Integer.toString(timeout));
    }
  }

  public boolean getWhetherThisWindowMatchWindowExpression(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    walkToWithException(context, uid)?.getWhetherThisWindowMatchWindowExpression(target) {String loc ->
      accessor.getWhetherThisWindowMatchWindowExpression(context, uid, target);
    }
  }

}
