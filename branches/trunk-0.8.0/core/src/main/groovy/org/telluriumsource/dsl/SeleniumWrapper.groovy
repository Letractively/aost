package org.telluriumsource.dsl

import org.telluriumsource.entity.CacheUsageResponse
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse

import org.telluriumsource.ui.object.StandardTable
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.exception.UiObjectNotFoundException
import org.telluriumsource.ui.object.Repeat
import org.telluriumsource.entity.DiagnosisRequest
import org.telluriumsource.entity.KeyValuePairs
import org.telluriumsource.ui.builder.AllPurposeObjectBuilder
import org.telluriumsource.ui.object.AllPurposeObject

import org.telluriumsource.ui.widget.Widget
import org.telluriumsource.exception.NotWidgetObjectException

import org.telluriumsource.ui.locator.JQueryProcessor
import org.telluriumsource.ui.locator.XPathProcessor
import org.telluriumsource.exception.NotMeaningfulMethodException

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Sep 10, 2010
 * 
 */
class SeleniumWrapper extends BaseDslContext {

  public static final String KEY = "key"
  public static final String OBJECT = "obj"
  public static final String GENERATED = "generated"

//  abstract protected String locatorMapping(WorkflowContext context, loc)

//  abstract protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc)

    UiObject walkToWithException(WorkflowContext context, String uid) {
//      env.lastDslContext = this;
      UiObject obj = ui.walkTo(context, uid);
      if (obj != null) {
        context.attachMetaCmd(uid, obj.amICacheable(), true);
        context.putContext(WorkflowContext.DSLCONTEXT, this);
        env.lastUiModule = getUiModuleId(uid);

        return obj
      }

      throw new UiObjectNotFoundException(i18nBundle.getMessage("BaseDslContext.CannotFindUIObject", uid))
    }

    def customUiCall(String uid, String method, Object[] args) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid).customMethod() {loc ->
        String locator = locatorMapping(context, loc)
        Object[] list = [context, locator, args].flatten()
        return extension.invokeMethod(method, list)
      }
    }

    def customDirectCall(String method, Object[] args) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      Object[] list = [context, args].flatten()
      return extension.invokeMethod(method, list)
    }

    public void triggerEventOn(String uid, String event) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid).customMethod() {loc ->
        String locator = locatorMapping(context, loc)
        Object[] list = [context, locator, event]

        extension.invokeMethod("triggerEvent", list)
      }
    }

    void click(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.click() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.click(context, locator, events)
      }
    }

    void doubleClick(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.doubleClick(context, locator, events)
      }
    }

    void clickAt(String uid, String coordination) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.clickAt(context, locator, coordination, events)
      }
    }

    void check(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.check() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.check(context, locator, events)
      }
    }

    void uncheck(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.uncheck(context, locator, events)
      }
    }

    void type(String uid, def input) {
      String str = (input==null) ? "" : input.toString();
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.type(str) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.type(context, locator, str, events)
      }
    }

    void keyPress(String uid, String key) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.keyPress(key) {loc ->
        String locator = locatorMapping(context, loc)
        extension.keyPress(context, locator, key)
      }
    }

    void keyDown(String uid, String key) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.keyDown(key) {loc ->
        String locator = locatorMapping(context, loc)
        extension.keyDown(context, locator, key)
      }
    }

    void keyUp(String uid, String key) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.keyUp(key) {loc ->
        String locator = locatorMapping(context, loc)
        extension.keyUp(context, locator, key)
      }
    }

    void focus(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.focus() {loc ->
        String locator = locatorMapping(context, loc)
        extension.focus(context, locator)
      }
    }

    void fireEvent(String uid, String eventName){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.fireEvent(eventName) {loc ->
        String locator = locatorMapping(context, loc)
        extension.fireEvent(context, locator, eventName)
      }
    }

    void keyType(String uid, def input) {
      String str = (input==null) ? "" : input.toString();
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.keyType(str) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.keyType(context, locator, str, events)
      }
    }

    void typeAndReturn(String uid, def input) {
      String str = (input==null) ? "" : input.toString();
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.typeAndReturn(str) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.typeAndReturn(context, locator, str, events)
      }
    }

    void clearText(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.clearText() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.clearText(context, locator, events)
      }
    }

    void select(String uid, String target) {
      selectByLabel(uid, target)
    }

    void selectByLabel(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void selectByValue(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void selectByIndex(String uid, int target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.selectByIndex(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void selectById(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.selectById(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void addSelectionByLabel(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.addSelection(context, locator, optloc)
      }
    }

    void addSelectionByValue(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.addSelection(context, locator, optloc)
      }
    }

    void removeSelectionByLabel(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.removeSelection(context, locator, optloc)
      }
    }

    void removeSelectionByValue(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.removeSelection(context, locator, optloc)
      }
    }

    void removeAllSelections(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.removeAllSelections() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.removeAllSelections(context, locator)
      }
    }

    String[] getSelectOptions(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectOptions() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectOptions(context, locator)
      }
    }

    String[] getSelectValues(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectValues() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectValues(context, locator)
      }
    }

    String[] getSelectedLabels(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedLabels() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedLabels(context, locator)
      }
    }

    String getSelectedLabel(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedLabel() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedLabel(context, locator)
      }
    }

    String[] getSelectedValues(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedValues() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedValues(context, locator)
      }
    }

    String getSelectedValue(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedValue() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedValue(context, locator)
      }
    }

    String[] getSelectedIndexes(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedIndexes() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedIndexes(context, locator)
      }
    }

    String getSelectedIndex(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedIndex() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedIndex(context, locator)
      }
    }

    String[] getSelectedIds(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedIds() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedIds(context, locator)
      }
    }

    String getSelectedId(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedId() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedId(context, locator)
      }
    }

    boolean isSomethingSelected(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)

      return obj.isSomethingSelected() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.isSomethingSelected(context, locator)
      }
    }

    String waitForText(String uid, int timeout) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
        String locator = locatorMapping(context, loc)
        accessor.waitForText(context, locator, tmo)
      }
    }

    boolean isElementPresent(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      if(this.exploreNewEngine() && env.isUseNewEngine()){
        walkToWithException(context, uid);
//      return extension.isElementPresent(context, uid);
        return accessor.isElementPresent(context, uid);
      }else{
        def obj = walkToWithException(context, uid);
        return obj.isElementPresent() {loc ->
          String locator = locatorMapping(context, loc);
          accessor.isElementPresent(context, locator);
        }
      }
    }

    boolean isVisible(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      return obj.isVisible() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.isVisible(context, locator)
      }
    }

    boolean isChecked(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      return obj.isChecked() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.isChecked(context, locator)
      }
    }

    boolean isEnabled(String uid) {
      return !isDisabled(uid);
    }

    boolean waitForElementPresent(String uid, int timeout) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      return obj.waitForElementPresent(timeout) {loc ->
        String locator = locatorMapping(context, loc)
        accessor.waitForElementPresent(context, locator, timeout)
      }
    }

    boolean waitForElementPresent(String uid, int timeout, int step) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      return obj.waitForElementPresent(timeout, step) {loc ->
        String locator = locatorMapping(context, loc)
        accessor.waitForElementPresent(context, locator, timeout, step)
      }
    }

    String getText(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.getText() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getText(context, locator)
      }
    }

    String getValue(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.getValue() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getValue(context, locator)
      }
    }

    String getLink(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.getLink() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    String getImageSource(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.getImageSource() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    String getImageAlt(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    String getImageTitle(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    void submit(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.submit() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.submit(context, locator)
      }
    }

    boolean isEditable(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      return walkToWithException(context, uid)?.isEditable() {loc ->
        String locator = locatorMapping(context, loc)
        return accessor.isEditable(context, locator)
      }
    }

    void mouseOver(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseOver() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseOver(context, locator)
      }
    }

    void mouseOut(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseOut() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseOut(context, locator)
      }
    }

    void dragAndDrop(String uid, String movementsString) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.dragAndDrop(context, locator, movementsString)
      }
    }

    void dragAndDropTo(String sourceUid, String targetUid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def src = walkToWithException(context, sourceUid)

      WorkflowContext ncontext = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def target = walkToWithException(ncontext, targetUid)

      if (src != null && target != null) {
        String srcLocator = locatorMapping(context, src.locator)
        String targetLocator = locatorMapping(ncontext, target.locator)
        eventHandler.dragAndDropToObject(context, srcLocator, targetLocator)
      }
    }

    void mouseDown(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseDown() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDown(context, locator)
      }
    }

    void mouseDownRight(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseDownRight() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDownRight(context, locator)
      }
    }

    void mouseDownAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseDownAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDownAt(context, locator, coordinate)
      }
    }

    void mouseDownRightAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDownRightAt(context, locator, coordinate)
      }
    }

    void mouseUp(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseUp() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseUp(context, locator)
      }
    }

    void mouseUpRight(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseUpRight() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseUpRight(context, locator)
      }
    }

    void mouseUpRightAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseUpRightAt(context, locator, coordinate)
      }
    }

    void mouseMove(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseMove() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseMove(context, locator)
      }
    }

    void mouseMoveAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid)?.mouseMoveAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseMoveAt(context, locator, coordinate)
      }
    }

    String[] getCSS(String uid, String cssName) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      return walkToWithException(context, uid)?.getCSS(cssName) {loc ->
        String locator = locatorMapping(context, loc)

        def out = extension.getCSS(context, locator, cssName)

        return (ArrayList) parseSeleniumJSONReturnValue(out)
      }
    }

    //This only works for jQuery selector
    String[] getAllTableCellText(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      if(this.exploreNewEngine()){
        def obj = walkToWithException(context, uid)
        if(obj != null && obj.respondsTo("getAllTableCellText")){
          def out = extension.getAllTableBodyText(context, uid)

          return (ArrayList) parseSeleniumJSONReturnValue(out)
        }

      } else {
        return walkToWithException(context, uid)?.getAllTableCellText() {loc, cell ->
          //for bulk data, the selector will not return a unique element
          context.updateUniqueForMetaCmd(false)
          //force not to cache the selector
          context.updateCacheableForMetaCmd(false)
          String locator = locatorMappingWithOption(context, loc, cell)

          def out = extension.getAllText(context, locator)

          return (ArrayList) parseSeleniumJSONReturnValue(out)
        }
      }

      return null;
    }

    //This only works for jQuery selector and Standard Table
    String[] getAllTableCellTextForTbody(String uid, int index) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      return walkToWithException(context, uid)?.getAllTableCellTextForTbody(index) {loc, cell ->
        context.updateUniqueForMetaCmd(false)
        //force not to cache the selector
        context.updateCacheableForMetaCmd(false)
        String locator = locatorMappingWithOption(context, loc, cell)

        def out = extension.getAllText(context, locator)

        return (ArrayList) parseSeleniumJSONReturnValue(out)
      }
    }

    int getTableHeaderColumnNumByXPath(String uid) {
      //force to use xpath
      WorkflowContext context = WorkflowContext.getDefaultContext()
      def obj = walkToWithException(context, uid)

      return obj.getTableHeaderColumnNumByXPath {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableFootColumnNumByXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      def obj = walkToWithException(context, uid)

      return obj.getTableFootColumnNumByXPath {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableMaxRowNumByXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      def obj = walkToWithException(context, uid)

      return obj.getTableMaxRowNumByXPath() {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableMaxColumnNumByXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      def obj = walkToWithException(context, uid)

      return obj.getTableMaxColumnNumByXPath() {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      StandardTable obj = (StandardTable) walkToWithException(context, uid)

      return obj.getTableMaxRowNumForTbodyByXPath(ntbody) {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      StandardTable obj = (StandardTable) walkToWithException(context, uid)

      return obj.getTableMaxColumnNumForTbodyByXPath(ntbody) {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableMaxTbodyNumByXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      StandardTable obj = (StandardTable) walkToWithException(context, uid)

      return obj.getTableMaxTbodyNumByXPath() {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    int getTableHeaderColumnNumBySelector(String uid) {
      //force to use jQuery selector
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableHeaderColumnNumBySelector() {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTableFootColumnNumBySelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableFootColumnNumBySelector() {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTableMaxRowNumBySelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableMaxRowNumBySelector() {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTableMaxColumnNumBySelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      def obj = walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableMaxColumnNumBySelector() {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      StandardTable obj = (StandardTable) walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableMaxRowNumForTbodyBySelector(ntbody) {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      StandardTable obj = (StandardTable) walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableMaxColumnNumForTbodyBySelector(ntbody) {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTableMaxTbodyNumBySelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      StandardTable obj = (StandardTable) walkToWithException(context, uid)
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)

      return obj.getTableMaxTbodyNumBySelector() {loc, optloc ->
        String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, locator)
      }
    }

    int getTeTableHeaderColumnNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableHeaderColumnNum(context, uid)
    }

    int getTableHeaderColumnNum(String uid) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableHeaderColumnNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableHeaderColumnNumBySelector(uid)

        return getTableHeaderColumnNumByXPath(uid)
      }
    }

    int getTeTableFootColumnNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableFootColumnNum(context, uid)
    }

    int getTableFootColumnNum(String uid) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableFootColumnNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableFootColumnNumBySelector(uid)

        return getTableFootColumnNumByXPath(uid)
      }
    }

    int getTeTableRowNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableRowNum(context, uid)
    }

    int getTableMaxRowNum(String uid) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableRowNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxRowNumBySelector(uid)

        return getTableMaxRowNumByXPath(uid)
      }
     }

    int getTeTableColumnNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableColumnNum(context, uid)
    }

    int getTableMaxColumnNum(String uid) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableColumnNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxColumnNumBySelector(uid)

        return getTableMaxColumnNumByXPath(uid)
      }
    }

    int getTeTableRowNumForTbody(String uid, int ntbody){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableRowNumForTbody(context, uid, ntbody)
    }

    int getTableMaxRowNumForTbody(String uid, int ntbody) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableRowNumForTbody(uid, ntbody)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxRowNumForTbodyBySelector(uid, ntbody)

        return getTableMaxRowNumForTbodyByXPath(uid, ntbody)
      }
    }

    int getTeTableColumnNumForTbody(String uid, int ntbody){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableColumnNumForTbody(context, uid, ntbody)
    }

    int getTableMaxColumnNumForTbody(String uid, int ntbody) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableColumnNumForTbody(uid, ntbody)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxColumnNumForTbodyBySelector(uid, ntbody)

        return getTableMaxColumnNumForTbodyByXPath(uid, ntbody)
      }
    }

    int getTeTableTbodyNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeTableTbodyNum(context, uid)
    }

    int getTableMaxTbodyNum(String uid) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeTableTbodyNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxTbodyNumBySelector(uid)

        return getTableMaxTbodyNumByXPath(uid)
      }
    }

    int getRepeatNumByXPath(String uid){
      WorkflowContext context = WorkflowContext.getDefaultContext()
      Repeat obj = (Repeat) walkToWithException(context, uid)
      return obj.getRepeatNum() {loc ->
        String locator = locatorMapping(context, loc)

        return accessor.getXpathCount(context, locator)
      }
    }

    int getRepeatNumByCssSelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      Repeat obj = (Repeat) walkToWithException(context, uid)
      return obj.getRepeatNum() {loc ->
        String locator = locatorMapping(context, loc)
        String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, jq)
      }
    }
    int getTeRepeatNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getRepeatNum(context, uid)
    }

    int getRepeatNum(String uid) {
      if (this.exploreNewEngine() && this.isUseTelluriumApi()) {
        return getTeRepeatNum(uid)
      } else {
        if (this.exploreCssSelector())
          return getRepeatNumByCssSelector(uid)

        return getRepeatNumByXPath(uid)
      }
    }

    int getListSizeByXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      org.telluriumsource.ui.object.List obj = (org.telluriumsource.ui.object.List) walkToWithException(context, uid)
      return obj.getListSizeByXPath() {loc ->
        String locator = locatorMapping(context, loc)
        locator
      }
    }

    //use CSS Selector to optimize the list operations
    int getListSizeBySelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
      org.telluriumsource.ui.object.List obj = (org.telluriumsource.ui.object.List) walkToWithException(context, uid)
//    context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
//    context.updateCacheableForMetaCmd(false)
      return obj.getListSizeByCssSelector() {loc, separators ->
        String locator = locatorMapping(context, loc)

        return extension.getListSize(context, locator, separators)
      }
    }

    int getTeListSize(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)

      return extension.getTeListSize(context, uid)
    }

    int getListSize(String uid) {
      if(this.exploreNewEngine() && this.isUseTelluriumApi()){
        return getTeListSize(uid)
      }else{
        if (this.exploreCssSelector())
          return getListSizeBySelector(uid)

        return getListSizeByXPath(uid)
      }
    }

    boolean isDisabled(String uid) {
       WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      return walkToWithException(context, uid).isDisabled() {loc ->
        String locator = locatorMapping(context, loc)

        extension.isDisabled(context, locator)
      }
    }

    def getParentAttribute(String uid, String attribute) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.getAttribute(attribute) {loc, attr ->
        String locator = locatorMapping(context, loc)
        if (this.exploreCssSelector()) {
          String ploc = JQueryProcessor.popLast(locator)
          return accessor.getAttribute(context, ploc + "@${attr}")
        } else {
          String ploc = XPathProcessor.popXPath(locator)
          return accessor.getAttribute(context, ploc + "/self::node()@${attr}")
        }
      }
    }

    def getAttribute(String uid, String attribute) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)?.getAttribute(attribute) {loc, attr ->
        String locator = locatorMapping(context, loc)
        if (this.exploreCssSelector()) {
          return accessor.getAttribute(context, locator + "@${attr}")
        } else {
          return accessor.getAttribute(context, locator + "/self::node()@${attr}")
        }
      }
    }

    boolean hasCssClass(String uid, String cssClass) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      String[] strings = walkToWithException(context, uid)?.hasCssClass() {loc, classAttr ->
        String locator = locatorMapping(context, loc)
        String clazz
        if (this.exploreCssSelector()) {
          clazz = accessor.getAttribute(context, locator + "@${classAttr}")
        } else {
          clazz = accessor.getAttribute(context, locator + "/self::node()@${classAttr}")
        }
        if (clazz != null && clazz.trim().length() > 0) {
          return clazz.split(" ")
        }
 //       return false
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

    //Toggle displaying each of the set of matched elements.
    //If they are shown, toggle makes them hidden.
    //If they are hidden, toggle makes them shown
    void toggle(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      walkToWithException(context, uid).toggle() {loc ->
        String locator = locatorMapping(context, loc)

        extension.toggle(context, locator)
      }
    }

    //delay in milliseconds
    void show(String uid, int delay) {
      if(!this.exploreNewEngine() || !this.isUseTelluriumApi()){
        println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
      }else{
        WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

        def obj = walkToWithException(context, uid)
        if(obj != null){
          extension.showUi(context, uid)
          pause(delay)
          extension.cleanUi(context, uid)
          pause(200)
        }
      }
    }

    void startShow(String uid) {
      if(!this.exploreNewEngine() || !this.isUseTelluriumApi()){
        println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
      }else{
        WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

        def obj = walkToWithException(context, uid)
        if(obj != null){
          extension.showUi(context, uid)
          pause(200)
        }
      }
    }

    void endShow(String uid) {
      if(!this.exploreNewEngine() || !this.isUseTelluriumApi()){
        println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
      }else{
        WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

        def obj = walkToWithException(context, uid)
        if(obj != null){
          extension.cleanUi(context, uid)
          pause(200)
        }
      }
    }

    public void validate(String uid) {
      UiModuleValidationResponse response = getUiModuleValidationResult(uid);
      response?.showMe();
    }

    public UiModuleValidationResponse getUiModuleValidationResult(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid);
      JSONArray arr = new JSONArray();
      context.setJSONArray(arr);
      obj.treeWalk(context);
      JSONArray jsa = context.getJSONArray();

//    def out = extension.getValidateUiModule(context, jsa.toString());
      def out = extension.getValidateUiModule(context, jsa);

      return parseUseUiModuleResponse(out);
    }

    public DiagnosisResponse getDiagnosisResult(String uid) {
      return getDiagnosisResult(uid, null);
    }

    public DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      return walkToWithException(context, uid)?.diagnose() {loc ->
        String locator = locatorMapping(context, loc)
        String ploc
        if (this.exploreCssSelector()) {
          ploc = JQueryProcessor.popLast(locator)
        } else {
          ploc = XPathProcessor.popXPath(locator)
        }

        if(options == null)
          options = new DiagnosisOption();

        DiagnosisRequest request = new DiagnosisRequest(uid, ploc, loc, options)

        def out = extension.getDiagnosisResponse(context, locator, request.toJSON())

        return new DiagnosisResponse(parseSeleniumJSONReturnValue(out))
      }
    }

    public void diagnose(String uid) {
      DiagnosisResponse resp = this.getDiagnosisResult(uid)
      resp.showMe()
    }

    public void diagnose(String uid, DiagnosisOption options) {
      DiagnosisResponse resp = this.getDiagnosisResult(uid, options)
      resp.showMe()
    }

    public java.util.List getHTMLSourceResponse(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid)
      def out = extension.getHTMLSource(context, uid)

      return parseSeleniumJSONReturnValue(out);
    }

    public void getHTMLSource(String uid){
      java.util.List list =  getHTMLSourceResponse(uid);
      if(list != null && list.size() > 0){
        list.each{Map map ->
          String key = map.get("key");
          String val = map.get("val");
          println(key + ": ");
          println("");
          println(val);
          println("");
        }
      }
    }

    UiByTagResponse getUiByTag(String tag, Map filters){
      KeyValuePairs pairs = new KeyValuePairs(filters);
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

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

    void removeMarkedUids(String tag){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.removeMarkedUids(context, tag);
    }

    //reset a form
    void reset(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      walkToWithException(context, uid).reset() {loc ->
        String locator = locatorMapping(context, loc)

        extension.reset(context, locator)
      }
    }

    def getWidget(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        def obj = walkToWithException(context, uid)
        if (!(obj instanceof Widget)) {
            println i18nBundle.getMessage("DslContext.UIObject" , {uid})

            throw new NotWidgetObjectException(i18nBundle.getMessage("DslContext.UIObject" , uid))
        }

        //add reference xpath for the widget
        Widget widget = (Widget)obj
        widget.updateParentRef(context.getReferenceLocator())

        return obj
    }

    def onWidget(String uid, String method, Object[] args) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        def obj = walkToWithException(context, uid)
        if (!(obj instanceof Widget)) {
            println i18nBundle.getMessage("DslContext.UIObject" , uid)

//            throw new RuntimeException(i18nManager.getMessage("DslContext.UIObject" , {uid}))
            throw new NotWidgetObjectException(i18nBundle.getMessage("DslContext.UIObject" , uid))
        } else {
            if (obj.metaClass.respondsTo(obj, method, args)) {

                //add reference xpath for the widget
                Widget widget = (Widget)obj
                widget.updateParentRef(context.getReferenceLocator())

                return obj.invokeMethod(method, args)
            } else {

                throw new MissingMethodException(method, obj.metaClass.class, args)
            }
        }
    }

    void selectFrame(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectFrame() {String loc ->
            eventHandler.selectFrame(context, loc)
        }
    }

    void selectFrameByIndex(int index) {
        WorkflowContext context = WorkflowContext.getDefaultContext()

        eventHandler.selectFrame(context, "index=${index}")
    }

    void selectParentFrameFrom(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectParentFrame() {String loc ->
            eventHandler.selectFrame(context, loc)
        }
    }

    void selectTopFrameFrom(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectTopFrame() {String loc ->
            eventHandler.selectFrame(context, loc)
        }
    }

    boolean getWhetherThisFrameMatchFrameExpression(String uid, String target) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.getWhetherThisFrameMatchFrameExpression(target) {String loc, String tgt ->
            accessor.getWhetherThisFrameMatchFrameExpression(context, loc, tgt)
        }
    }

    void waitForFrameToLoad(String uid, int timeout) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.waitForFrameToLoad(timeout) {String loc, int tmo ->
            accessor.waitForFrameToLoad(context, loc, Integer.toString(tmo))
        }
    }

    void openWindow(String uid, String url) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.openWindow(url) {String loc, String aurl ->
            eventHandler.openWindow(context, aurl, loc)
        }
    }

    void selectWindow(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectWindow() {String loc ->
            eventHandler.selectWindow(context, loc)
        }
    }

    void closeWindow(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.closeWindow() {String loc ->
            eventHandler.closeWindow(context, loc)
        }
    }

    void waitForPopUp(String uid, int timeout) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.waitForPopUp(timeout) {String loc ->
            accessor.waitForPopUp(context, loc, Integer.toString(timeout))
        }
    }

    boolean getWhetherThisWindowMatchWindowExpression(String uid, String target) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.getWhetherThisWindowMatchWindowExpression(target) {String loc ->
            accessor.getWhetherThisWindowMatchWindowExpression(context, loc, target)
        }
    }

    public void enableClosestMatch(){
      throw new NotMeaningfulMethodException(i18nBundle.getMessage("ApiCall.NotMeaningfulMethodCall", "enableClosestMatch"));
    }

    public void disableClosestMatch(){
      throw new NotMeaningfulMethodException(i18nBundle.getMessage("ApiCall.NotMeaningfulMethodCall", "disableClosestMatch"));
    }

    public void enableCssSelector() {
      env.setUseCssSelector(true);
    }

    public void disableCssSelector() {
      env.setUseCssSelector(false);
    }

    public void cleanCache() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.cleanCache(context)
    }

    public void setCacheMaxSize(int size) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.setCacheMaxSize(context, size)
    }

    public int getCacheSize() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      return extension.getCacheSize(context).intValue()
    }

    public int getCacheMaxSize() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      return extension.getCacheMaxSize(context).intValue()
    }

    public String getCacheUsage(){
      CacheUsageResponse resp = this.getCacheUsageResponse();
      return resp.toString();
    }

    public CacheUsageResponse getCacheUsageResponse() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      List out = extension.getCacheUsage(context);
      CacheUsageResponse resp = new CacheUsageResponse();
      resp.parseJSON(out);

      return resp;
    }

    public void useDiscardNewCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.useDiscardNewCachePolicy(context)
    }

    public void useDiscardOldCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.useDiscardOldCachePolicy(context)
    }

    public void useDiscardLeastUsedCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.useDiscardLeastUsedCachePolicy(context)
    }

    public void useDiscardInvalidCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      extension.useDiscardInvalidCachePolicy(context)
    }

    public String getCurrentCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())

      return extension.getCachePolicyName(context)
    }

  public void setCookie(String cookieName, String value, Object options) {

  }

  public void setCookie(String cookieName, String value) {

  }

  //let the missing property return the a string of the properity, this is useful for the onWidget method
  //so that we can pass in widget method directly, instead of passing in the method name as a String

  def propertyMissing(String name) {
    println i18nBundle.getMessage("BaseDslContext.PropertyIsMissing", name)
    return name
  }
}
