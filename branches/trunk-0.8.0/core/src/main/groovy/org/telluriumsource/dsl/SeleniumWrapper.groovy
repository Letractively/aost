package org.telluriumsource.dsl

import org.telluriumsource.entity.EngineState
import org.telluriumsource.entity.CacheUsageResponse
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse
import org.telluriumsource.ui.locator.JQueryOptimizer
import org.telluriumsource.ui.locator.LocatorProcessor
import org.telluriumsource.ui.object.StandardTable
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.exception.UiObjectNotFoundException
import org.telluriumsource.ui.object.Repeat
import org.telluriumsource.entity.DiagnosisRequest
import org.telluriumsource.entity.KeyValuePairs
import org.telluriumsource.ui.builder.AllPurposeObjectBuilder
import org.telluriumsource.ui.object.AllPurposeObject
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.ui.widget.Widget
import org.telluriumsource.exception.NotWidgetObjectException
import org.telluriumsource.component.event.EventHandler
import org.telluriumsource.component.data.Accessor
import org.telluriumsource.component.custom.Extension
import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.util.Helper
import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.ui.locator.JQueryProcessor
import org.telluriumsource.ui.locator.XPathProcessor

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Sep 10, 2010
 * 
 */
class SeleniumWrapper extends BaseDslContext {

  protected static final String JQUERY_SELECTOR = "jquery="
  protected static final String DEFAULT_XPATH = "default"
  protected static final String JAVASCRIPT_XPATH = "javascript-xpath"
  protected static final String AJAXSLT_XPATH = "ajaxslt"
  protected static final String LOCATOR = "locator"
  protected static final String OPTIMIZED_LOCATOR = "optimized"

  public static final String KEY = "key"
  public static final String OBJECT = "obj"
  public static final String GENERATED = "generated"

  protected JQueryOptimizer optimizer

  protected LocatorProcessor locatorProcessor

  protected RuntimeEnvironment env

  protected EventHandler eventHandler

  protected Accessor accessor

  protected Extension extension

//  abstract protected String locatorMapping(WorkflowContext context, loc)

//  abstract protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc)


  protected String postProcessSelector(WorkflowContext context, String jqs) {
//    String locator = jqs

    String optimized = optimizer.optimize(jqs)

/*    if (context.isUseUiModuleCache()) {
      JSONObject obj = new JSONObject()
      //meta command shoud not be null for locators
      MetaCmd metaCmd = context.extraMetaCmd()
      obj.put(LOCATOR, locator)
      obj.put(OPTIMIZED_LOCATOR, optimized)

      obj.put(MetaCmd.UID, metaCmd.getProperty(MetaCmd.UID))
      obj.put(MetaCmd.CACHEABLE, metaCmd.getProperty(MetaCmd.CACHEABLE))
      obj.put(MetaCmd.UNIQUE, metaCmd.getProperty(MetaCmd.UNIQUE))

      String jsonjqs = obj.toString()

      return UIModule_CACHE + jsonjqs
    }
    */

    return JQUERY_SELECTOR + optimized
  }

    def customUiCall(String uid, String method, Object[] args) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      return walkToWithException(context, uid).customMethod() {loc ->
        String locator = locatorMapping(context, loc)
        Object[] list = [context, locator, args].flatten()
        return extension.invokeMethod(method, list)
      }
    }

    def customDirectCall(String method, Object[] args) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      Object[] list = [context, args].flatten()
      return extension.invokeMethod(method, list)
    }

    public void triggerEventOn(String uid, String event) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid).customMethod() {loc ->
        String locator = locatorMapping(context, loc)
        Object[] list = [context, locator, event]

        extension.invokeMethod("triggerEvent", list)
      }
    }

    //uid should use the format table2[2][3] for Table or list[2] for List
    def getUiElement(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = ui.walkTo(context, uid)

      return obj
    }

    protected String getUiModuleId(String uid){
      UiID uiid = UiID.convertToUiID(uid);
      return uiid.pop();
    }

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

    String getConsoleInput() {
      return (String) System.in.withReader {
        it.readLine();
      }
    }

    void click(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.click() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.click(context, locator, events)
      }
    }

    void doubleClick(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.doubleClick(context, locator, events)
      }
    }

    void clickAt(String uid, String coordination) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.clickAt(context, locator, coordination, events)
      }
    }

    void check(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.check() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.check(context, locator, events)
      }
    }

    void uncheck(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.uncheck(context, locator, events)
      }
    }

    void type(String uid, def input) {
      String str = (input==null) ? "" : input.toString();
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.type(str) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.type(context, locator, str, events)
      }
    }

    void keyPress(String uid, String key) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.keyPress(key) {loc ->
        String locator = locatorMapping(context, loc)
        extension.keyPress(context, locator, key)
      }
    }

    void keyDown(String uid, String key) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.keyDown(key) {loc ->
        String locator = locatorMapping(context, loc)
        extension.keyDown(context, locator, key)
      }
    }

    void keyUp(String uid, String key) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.keyUp(key) {loc ->
        String locator = locatorMapping(context, loc)
        extension.keyUp(context, locator, key)
      }
    }

    void focus(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.focus() {loc ->
        String locator = locatorMapping(context, loc)
        extension.focus(context, locator)
      }
    }

    void fireEvent(String uid, String eventName){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.fireEvent(eventName) {loc ->
        String locator = locatorMapping(context, loc)
        extension.fireEvent(context, locator, eventName)
      }
    }

    void keyType(String uid, def input) {
      String str = (input==null) ? "" : input.toString();
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.keyType(str) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.keyType(context, locator, str, events)
      }
    }

    void typeAndReturn(String uid, def input) {
      String str = (input==null) ? "" : input.toString();
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.typeAndReturn(str) {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.typeAndReturn(context, locator, str, events)
      }
    }

    void altKeyUp() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.altKeyUp(context)
    }

    void altKeyDown() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.altKeyDown(context)
    }

    void ctrlKeyUp() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.ctrlKeyUp(context)
    }

    void ctrlKeyDown() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.ctrlKeyDown(context)
    }

    void shiftKeyUp() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.shiftKeyUp(context)
    }

    void shiftKeyDown() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.shiftKeyDown(context)
    }

    void metaKeyUp() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.metaKeyUp(context)
    }

    void metaKeyDown() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.metaKeyDown(context)
    }

    void clearText(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.clearText() {loc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.clearText(context, locator, events)
      }
    }

    void select(String uid, String target) {
      selectByLabel(uid, target)
    }

    void selectByLabel(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void selectByValue(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void selectByIndex(String uid, int target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.selectByIndex(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void selectById(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.selectById(target) {loc, optloc, String[] events ->
        String locator = locatorMapping(context, loc)
        eventHandler.select(context, locator, optloc, events)
      }
    }

    void addSelectionByLabel(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.addSelection(context, locator, optloc)
      }
    }

    void addSelectionByValue(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.addSelection(context, locator, optloc)
      }
    }

    void removeSelectionByLabel(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.removeSelection(context, locator, optloc)
      }
    }

    void removeSelectionByValue(String uid, String target) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
        String locator = locatorMapping(context, loc)
        eventHandler.removeSelection(context, locator, optloc)
      }
    }

    void removeAllSelections(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.removeAllSelections() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.removeAllSelections(context, locator)
      }
    }

    String[] getSelectOptions(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectOptions() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectOptions(context, locator)
      }
    }

    String[] getSelectValues(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectValues() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectValues(context, locator)
      }
    }

    String[] getSelectedLabels(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedLabels() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedLabels(context, locator)
      }
    }

    String getSelectedLabel(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedLabel() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedLabel(context, locator)
      }
    }

    String[] getSelectedValues(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedValues() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedValues(context, locator)
      }
    }

    String getSelectedValue(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedValue() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedValue(context, locator)
      }
    }

    String[] getSelectedIndexes(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedIndexes() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedIndexes(context, locator)
      }
    }

    String getSelectedIndex(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedIndex() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedIndex(context, locator)
      }
    }

    String[] getSelectedIds(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedIds() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedIds(context, locator)
      }
    }

    String getSelectedId(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.getSelectedId() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getSelectedId(context, locator)
      }
    }

    boolean isSomethingSelected(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)

      return obj.isSomethingSelected() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.isSomethingSelected(context, locator)
      }
    }

    String waitForText(String uid, int timeout) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
        String locator = locatorMapping(context, loc)
        accessor.waitForText(context, locator, tmo)
      }
    }

    boolean isElementPresent(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      if(this.exploreUiModuleCache() && env.isUseNewEngine()){
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)
      return obj.isVisible() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.isVisible(context, locator)
      }
    }

    boolean isChecked(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)
      return obj.waitForElementPresent(timeout) {loc ->
        String locator = locatorMapping(context, loc)
        accessor.waitForElementPresent(context, locator, timeout)
      }
    }

    boolean waitForElementPresent(String uid, int timeout, int step) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)
      return obj.waitForElementPresent(timeout, step) {loc ->
        String locator = locatorMapping(context, loc)
        accessor.waitForElementPresent(context, locator, timeout, step)
      }
    }

    boolean waitForCondition(String script, int timeoutInMilliSecond) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      accessor.waitForCondition(context, script, Integer.toString(timeoutInMilliSecond))
    }

    String getText(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getText() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getText(context, locator)
      }
    }

    String getValue(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getValue() {loc ->
        String locator = locatorMapping(context, loc)
        accessor.getValue(context, locator)
      }
    }

    String getLink(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getLink() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    String getImageSource(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getImageSource() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    String getImageAlt(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    String getImageTitle(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
        String locator = locatorMapping(context, loc)
        accessor.getAttribute(context, locator + attr)
      }
    }

    void submit(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.submit() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.submit(context, locator)
      }
    }

    boolean isEditable(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return walkToWithException(context, uid)?.isEditable() {loc ->
        String locator = locatorMapping(context, loc)
        return accessor.isEditable(context, locator)
      }
    }

    String getEval(String script) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return accessor.getEval(context, script)
    }

    void mouseOver(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseOver() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseOver(context, locator)
      }
    }

    void mouseOut(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseOut() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseOut(context, locator)
      }
    }

    void dragAndDrop(String uid, String movementsString) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.dragAndDrop(context, locator, movementsString)
      }
    }

    void dragAndDropTo(String sourceUid, String targetUid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def src = walkToWithException(context, sourceUid)

      WorkflowContext ncontext = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def target = walkToWithException(ncontext, targetUid)

      if (src != null && target != null) {
        String srcLocator = locatorMapping(context, src.locator)
        String targetLocator = locatorMapping(ncontext, target.locator)
        eventHandler.dragAndDropToObject(context, srcLocator, targetLocator)
      }
    }

    void mouseDown(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseDown() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDown(context, locator)
      }
    }

    void mouseDownRight(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseDownRight() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDownRight(context, locator)
      }
    }

    void mouseDownAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseDownAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDownAt(context, locator, coordinate)
      }
    }

    void mouseDownRightAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseDownRightAt(context, locator, coordinate)
      }
    }

    void mouseUp(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseUp() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseUp(context, locator)
      }
    }

    void mouseUpRight(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseUpRight() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseUpRight(context, locator)
      }
    }

    void mouseUpRightAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseUpRightAt(context, locator, coordinate)
      }
    }

    void mouseMove(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseMove() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseMove(context, locator)
      }
    }

    void mouseMoveAt(String uid, String coordinate) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.mouseMoveAt() {loc ->
        String locator = locatorMapping(context, loc)
        eventHandler.mouseMoveAt(context, locator, coordinate)
      }
    }

    Number getXpathCount(String xpath) {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return accessor.getXpathCount(context, xpath)
    }

    String captureNetworkTraffic(String type) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return accessor.captureNetworkTraffic(context, type)
    }

    void addCustomRequestHeader(String key, String value) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      accessor.addCustomRequestHeader(context, key, value)
    }

    Number getCssSelectorCount(String cssSelector) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, false)
      //do not cache any selectors for counting
      context.updateCacheableForMetaCmd(false);
      String jq = postProcessSelector(context, cssSelector.trim())

      return extension.getCssSelectorCount(context, jq)
    }

    Number getLocatorCount(String locator) {
      if (this.exploreCssSelector())
        return getCssSelectorCount(locator)

      return getXpathCount(locator)
    }

    String getXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      walkToWithException(context, uid)?.getXPath() {loc ->
        locatorMapping(context, loc)
      }

      String locator = context.getReferenceLocator()
//    if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
      if (locator != null && (!locator.startsWith("//"))) {
        locator = "/" + locator
      }

      return locator
    }

    String getSelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
      walkToWithException(context, uid)?.getSelector() {loc ->
        locatorMapping(context, loc)
      }

      String locator = context.getReferenceLocator()
      locator = optimizer.optimize(locator.trim())

      return JQUERY_SELECTOR + locator
    }

    String getLocator(String uid) {
      if (this.exploreCssSelector()) {
        return getSelector(uid)
      }

      return getXPath(uid)
    }

    String[] getCSS(String uid, String cssName) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid)?.getCSS(cssName) {loc ->
        String locator = locatorMapping(context, loc)

        def out = extension.getCSS(context, locator, cssName)

        return (ArrayList) parseSeleniumJSONReturnValue(out)
      }
    }

    //This only works for jQuery selector
    String[] getAllTableCellText(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableHeaderColumnNum(context, uid)
    }

    int getTableHeaderColumnNum(String uid) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeTableHeaderColumnNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableHeaderColumnNumBySelector(uid)

        return getTableHeaderColumnNumByXPath(uid)
      }
    }

    int getTeTableFootColumnNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableFootColumnNum(context, uid)
    }

    int getTableFootColumnNum(String uid) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeTableFootColumnNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableFootColumnNumBySelector(uid)

        return getTableFootColumnNumByXPath(uid)
      }
    }

    int getTeTableRowNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableRowNum(context, uid)
    }

    int getTableMaxRowNum(String uid) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeTableRowNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxRowNumBySelector(uid)

        return getTableMaxRowNumByXPath(uid)
      }
     }

    int getTeTableColumnNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableColumnNum(context, uid)
    }

    int getTableMaxColumnNum(String uid) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeTableColumnNum(uid)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxColumnNumBySelector(uid)

        return getTableMaxColumnNumByXPath(uid)
      }
    }

    int getTeTableRowNumForTbody(String uid, int ntbody){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableRowNumForTbody(context, uid, ntbody)
    }

    int getTableMaxRowNumForTbody(String uid, int ntbody) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeTableRowNumForTbody(uid, ntbody)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxRowNumForTbodyBySelector(uid, ntbody)

        return getTableMaxRowNumForTbodyByXPath(uid, ntbody)
      }
    }

    int getTeTableColumnNumForTbody(String uid, int ntbody){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableColumnNumForTbody(context, uid, ntbody)
    }

    int getTableMaxColumnNumForTbody(String uid, int ntbody) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeTableColumnNumForTbody(uid, ntbody)
      }else{
        if (this.exploreCssSelector())
          return getTableMaxColumnNumForTbodyBySelector(uid, ntbody)

        return getTableMaxColumnNumForTbodyByXPath(uid, ntbody)
      }
    }

    int getTeTableTbodyNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeTableTbodyNum(context, uid)
    }

    int getTableMaxTbodyNum(String uid) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
      Repeat obj = (Repeat) walkToWithException(context, uid)
      return obj.getRepeatNum() {loc ->
        String locator = locatorMapping(context, loc)
        String jq = postProcessSelector(context, locator.trim())

        return extension.getCssSelectorCount(context, jq)
      }
    }
    int getTeRepeatNum(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getRepeatNum(context, uid)
    }

    int getRepeatNum(String uid) {
      if (this.exploreUiModuleCache() && this.isUseTelluriumApi()) {
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)

      return extension.getTeListSize(context, uid)
    }

    int getListSize(String uid) {
      if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
        return getTeListSize(uid)
      }else{
        if (this.exploreCssSelector())
          return getListSizeBySelector(uid)

        return getListSizeByXPath(uid)
      }
    }

    boolean isDisabled(String uid) {
       WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return walkToWithException(context, uid).isDisabled() {loc ->
        String locator = locatorMapping(context, loc)

        extension.isDisabled(context, locator)
      }
    }

    def getParentAttribute(String uid, String attribute) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
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
        return false
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      walkToWithException(context, uid).toggle() {loc ->
        String locator = locatorMapping(context, loc)

        extension.toggle(context, locator)
      }
    }

    //delay in milliseconds
    void show(String uid, int delay) {
      if(!this.exploreUiModuleCache() || !this.isUseTelluriumApi()){
        println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
      }else{
        WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

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
      if(!this.exploreUiModuleCache() || !this.isUseTelluriumApi()){
        println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
      }else{
        WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

        def obj = walkToWithException(context, uid)
        if(obj != null){
          extension.showUi(context, uid)
          pause(200)
        }
      }
    }

    void endShow(String uid) {
      if(!this.exploreUiModuleCache() || !this.isUseTelluriumApi()){
        println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
      }else{
        WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

        def obj = walkToWithException(context, uid)
        if(obj != null){
          extension.cleanUi(context, uid)
          pause(200)
        }
      }
    }

    public void dump(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)
      if (obj != null) {
        context.setNewUid(uid)
        obj.traverse(context)
        ArrayList list = context.getUidList()

        println(i18nBundle.getMessage("BaseDslContext.DumpLocatorInformation", uid))
        println("-------------------------------------------------------")
        list.each {String key ->
          String loc = getLocator(key)
          context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
          walkToWithException(context, key)

          if (this.exploreCssSelector()) {
            loc = this.postProcessSelector(context, loc)
          }
          println("${key}: ${loc}")
        }
        println("-------------------------------------------------------\n")
      }
    }

    public String toString(String uid) {
/*    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid);
    JSONArray arr = new JSONArray();
    context.setJSONArray(arr);
    obj.treeWalk(context);
    JSONArray jsa = context.getJSONArray();

    return jsa.toString();*/
      JSONArray ar = toJSONArray(uid);
      if(ar != null)
        return ar.toString();

      return null;
    }

    public JSONArray toJSONArray(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid);
      JSONArray arr = new JSONArray();
      context.setJSONArray(arr);
      obj.treeWalk(context);

      return context.getJSONArray();
    }

    public void validate(String uid) {
      UiModuleValidationResponse response = getUiModuleValidationResult(uid);
      response?.showMe();
    }

    public UiModuleValidationResponse getUiModuleValidationResult(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.diagnose() {loc ->
        String locator = locatorMapping(context, loc)
        String ploc;
        if (this.exploreCssSelector()) {
          ploc = JQueryProcessor.popLast(locator)
        } else {
          ploc = XPathProcessor.popXPath(locator)
        }
        DiagnosisOption options = new DiagnosisOption()
        DiagnosisRequest request = new DiagnosisRequest(uid, ploc, loc, options)

        def out = extension.getDiagnosisResponse(context, locator, request.toJSON())

        return new DiagnosisResponse(parseSeleniumJSONReturnValue(out))
      }
    }

    public DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid)?.diagnose() {loc ->
        String locator = locatorMapping(context, loc)
        String ploc
        if (this.exploreCssSelector()) {
          ploc = JQueryProcessor.popLast(locator)
        } else {
          ploc = XPathProcessor.popXPath(locator)
        }

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

    public String toHTML(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      def obj = walkToWithException(context, uid)
      return obj.toHTML()
    }

    public String toHTML() {
      StringBuffer sb = new StringBuffer(128)
      ui.registry.each {String key, UiObject val ->
        sb.append(val.toHTML())
      }

      return sb.toString()
    }

    public java.util.List getHTMLSourceResponse(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
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

    String getHtmlSource() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      return accessor.getHtmlSource(context)
    }

    String retrieveLastRemoteControlLogs(){
       WorkflowContext context = WorkflowContext.getDefaultContext()
       return accessor.retrieveLastRemoteControlLogs(context)
    }

    void setTimeout(long timeoutInMilliseconds) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      accessor.setTimeout(context, (new Long(timeoutInMilliseconds)).toString())
    }

    boolean isCookiePresent(String name) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      return accessor.isCookiePresent(context, name)
    }

    String getCookieByName(String name) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      return accessor.getCookieByName(context, name)
    }

    void createCookie(String nameValuePair, String optionsString) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      accessor.createCookie(context, nameValuePair, optionsString)
    }

    void deleteCookie(String name, String optionsString) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      accessor.deleteCookie(context, name, optionsString)
    }

    void deleteAllVisibleCookies() {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      accessor.deleteAllVisibleCookies(context)
    }

    //NEW Cookie APIs based on jQuery Cookie plugin
    void deleteAllCookiesByJQuery() {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      extension.deleteAllCookiesByJQuery(context)
    }

    void deleteCookieByJQuery(String cookieName) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      extension.deleteCookieByJQuery(context, cookieName);
    }

    void setCookieByJQuery(String cookieName, String value, def options){
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.setCookieByJQuery(context, cookieName, value, options);
    }

    void setCookieByJQuery(String cookieName, String value){
      setCookieByJQuery(cookieName, value, null);
    }

    String getCookieByJQuery(String cookieName){
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return extension.getCookieByJQuery(context, cookieName);
    }

    UiByTagResponse getUiByTag(String tag, Map filters){
      KeyValuePairs pairs = new KeyValuePairs(filters);
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.removeMarkedUids(context, tag);
    }

    //reset a form
    void reset(String uid){
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      walkToWithException(context, uid).reset() {loc ->
        String locator = locatorMapping(context, loc)

        extension.reset(context, locator)
      }
    }

    public void bugReport() {
      println "\nPlease cut and paste the following bug report to Tellurium user group (http://groups.google.com/group/tellurium-users)\n"
      println "---------------------------- Bug Report --------------------------------"

//      Environment env = env;
      if (env.lastUiModule != null) {
        println "\n-----------------------------------"
        println "UI Module " + env.lastUiModule + ": \n";

        println toString(env.lastUiModule);
        println "-----------------------------------\n"
      }

      println "\n-----------------------------------"
      println "HTML Source: \n";
      println getHtmlSource();
      println "-----------------------------------\n"

      if (env.lastUiModule != null) {
        println "\n-----------------------------------"
        println "Validate UI Module" + env.lastUiModule + ": \n";
        try {
//        getHtmlSource(env.lastUiModule);
          validate(env.lastUiModule);
        } catch (Exception e) {

        }
        println "-----------------------------------\n"
      }

      println "\n-----------------------------------"
      println "Environment: \n";
      //dump Environment variables
      println env.toString();
      println "-----------------------------------\n"

      println "\n-----------------------------------"
      println "Last Error: \n";
      println env.lastErrorDescription;
      println "-----------------------------------\n"

      println "\n-----------------------------------"
      println "System log: \n";
      println retrieveLastRemoteControlLogs();
      println "-----------------------------------\n"

      println "----------------------------    End     --------------------------------\n"
    }

    //later on, may need to refactor it to use resource file so that we can show message for different localities
    protected static final String XML_DOCUMENT_SCRIPT = """var doc = window.document;
        var xml = null;
        if(doc instanceof XMLDocument){
           xml = new XMLSerializer().serializeToString(doc);
        }else{
           xml = getText(doc.body);
        }
        xml; """

//    def defUi = ui.&Container

    //Must implement this method to define UI
//    remove this constraint so that DSL script does not need to define this method
//    public abstract void defineUi()

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

    protected String locatorMapping(WorkflowContext context, loc) {
      return locatorMappingWithOption(context, loc, null)
    }

    protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc) {
      String locator;
      if(context.noMoreProcess){
        locator = ""
      }else{
        locator = locatorProcessor.locate(context, loc)
      }

      //get the reference locator all the way to the ui object
      if (context.getReferenceLocator() != null){
//            locator = context.getReferenceLocator() + locator
          context.appendReferenceLocator(locator)
          locator = context.getReferenceLocator()
      }

      if(optLoc != null)
        locator = locator + optLoc

      if(context.isUseCssSelector()){
//          locator = optimizer.optimize(JQUERY_SELECTOR + locator.trim())
          locator = postProcessSelector(context, locator.trim())
      } else {
        //make sure the xpath starts with "//"
//          if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
          if (locator != null && (!locator.startsWith("//"))){
            locator = "/" + locator
        }
      }

      return locator
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

    void selectMainWindow() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.selectWindow(context, null)
    }

    void selectParentWindow() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.selectWindow(context, ".")
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

    void windowFocus() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.windowFocus(context)
    }

    void windowMaximize() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.windowMaximize(context)
    }

    String getBodyText() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getBodyText(context)
    }

    boolean isTextPresent(String pattern) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isTextPresent(context, pattern)
    }

    String getExpression(String expression) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getExpression(context, expression)
    }

    String getCookie() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getCookie(context)
    }

    void runScript(String script) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.runScript(context, script)
    }

    void captureScreenshot(String filename) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.captureScreenshot(context, filename)
    }

    void captureEntirePageScreenshot(String filename, String kwargs){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.captureEntirePageScreenshot(context, filename, kwargs)
    }

    String captureScreenshotToString(){
       WorkflowContext context = WorkflowContext.getDefaultContext()
       return accessor.captureScreenshotToString(context)
    }

    String captureEntirePageScreenshotToString(String kwargs){
       WorkflowContext context = WorkflowContext.getDefaultContext()
       return accessor.captureEntirePageScreenshotToString(context, kwargs)
    }

    void chooseCancelOnNextConfirmation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.chooseCancelOnNextConfirmation(context)
    }

    void chooseOkOnNextConfirmation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.chooseOkOnNextConfirmation(context)
    }

    void answerOnNextPrompt(String answer) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.answerOnNextPrompt(context, answer)
    }

    void goBack() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.goBack(context)
    }

    void refresh() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.refresh(context)
    }

    boolean isAlertPresent() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isAlertPresent(context)
    }

    boolean isPromptPresent() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isPromptPresent(context)
    }

    boolean isConfirmationPresent() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isConfirmationPresent(context)
    }

    String getAlert() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAlert(context)
    }

    String getConfirmation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getConfirmation(context)
    }

    String getPrompt() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getPrompt(context)
    }

    String getLocation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getLocation(context)
    }

    String getTitle() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getTitle(context)
    }

    String[] getAllButtons() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllButtons(context)
    }

    String[] getAllLinks() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllLinks(context)
    }

    String[] getAllFields() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllFields(context)
    }

    String[] getAllWindowIds() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllWindowIds(context)
    }

    String[] getAllWindowNames() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllWindowNames(context)
    }

    String[] getAllWindowTitles() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllWindowTitles(context)
    }

    void waitForPageToLoad(int timeout) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.waitForPageToLoad(context, Integer.toString(timeout))
    }

    public String getXMLDocument(){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        String xml =  getEval(context, XML_DOCUMENT_SCRIPT)

        return xml
    }

    //let the missing property return the a string of the properity, this is useful for the onWidget method
    //so that we can pass in widget method directly, instead of passing in the method name as a String
    def propertyMissing(String name) {
        println i18nBundle.getMessage("BaseDslContext.PropertyIsMissing" , name)
        return name
    }


    public EngineState getEngineState(){
      WorkflowContext context = WorkflowContext.getDefaultContext();
//    String out = extension.getEngineState(context);
//    Map map = parseSeleniumJSONReturnValue(out);
      Map map = extension.getEngineState(context);
      EngineState state = new EngineState();
      state.parseJson(map);

      return state;
    }
    //flag to decide whether we should use jQuery Selector
    protected boolean exploreCssSelector() {
      return env.isUseCssSelector();
    }

    //flag to decide whether we should cache jQuery selector
    protected boolean exploreUiModuleCache() {
      return env.isUseNewEngine();
    }

    public void helpTest() {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.helpTest(context);
    }

    public void noTest() {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.noTest(context);
    }

    public void enableClosestMatch(){
      env.setUseClosestMatch(true);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useClosestMatch(context, true);
    }

    public void disableClosestMatch(){
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.cleanCache(context)
    }

/*
    public boolean getCacheState() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return extension.getCacheState(context)
    }
*/

    public void setCacheMaxSize(int size) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.setCacheMaxSize(context, size)
    }

    public int getCacheSize() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return extension.getCacheSize(context).intValue()
    }

    public int getCacheMaxSize() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return extension.getCacheMaxSize(context).intValue()
    }

    public String getCacheUsage(){
      CacheUsageResponse resp = this.getCacheUsageResponse();
      return resp.toString();
    }

    public CacheUsageResponse getCacheUsageResponse() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

/*
    String out = extension.getCacheUsage(context);
    ArrayList list = (ArrayList) parseSeleniumJSONReturnValue(out)
    Map<String, Long> usages = new HashMap<String, Long>()
    list.each {Map elem ->
      elem.each {key, value ->
        usages.put(key, value)
      }
    }

    return usages
*/
      List out = extension.getCacheUsage(context);
      CacheUsageResponse resp = new CacheUsageResponse();
      resp.parseJSON(out);

      return resp;
    }

    public void useDiscardNewCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.useDiscardNewCachePolicy(context)
    }

    public void useDiscardOldCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.useDiscardOldCachePolicy(context)
    }

    public void useDiscardLeastUsedCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.useDiscardLeastUsedCachePolicy(context)
    }

    public void useDiscardInvalidCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.useDiscardInvalidCachePolicy(context)
    }

    public String getCurrentCachePolicy() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return extension.getCachePolicyName(context)
    }

    public void useDefaultXPathLibrary() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      accessor.useXpathLibrary(context, DEFAULT_XPATH)
    }

    public void useJavascriptXPathLibrary() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      accessor.useXpathLibrary(context, JAVASCRIPT_XPATH)
    }

    public void useAjaxsltXPathLibrary() {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
      accessor.useXpathLibrary(context, AJAXSLT_XPATH)
    }

    public void registerNamespace(String prefix, String namespace) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      extension.addNamespace(context, prefix, namespace)
    }

    public String getNamespace(String prefix) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      return extension.getNamespace(context, prefix)
    }

    public void pause(int milliseconds) {
      //flush out remaining commands in the command bundle before disconnection
      BundleProcessor processor = BundleProcessor.instance;
      processor.flush();

      Helper.pause(milliseconds);
    }

    public void flush() {
      //flush out remaining commands in the command bundle before disconnection
      BundleProcessor processor = BundleProcessor.instance;
      processor.flush();
    }

    public void enableMacroCmd() {
       env.setUseBundle(true);
    }

    public void disableMacroCmd() {
        env.setUseBundle(false);
    }

    public void enableTrace(){
       env.setUseTrace(true);
    }

    public void disableTrace(){
       env.setUseTrace(false);
    }

    public void showTrace() {
      Dispatcher dispatcher = new Dispatcher();
      dispatcher.showTrace()
    }

    public void setEnvironment(String name, Object value){
      env.setCustomEnvironment(name, value);
    }

    public Object getEnvironment(String name){
      return env.getCustomEnvironment(name);
    }

    public void setMaxMacroCmd(int max){
      env.setMaxMacroCmd(max);
    }

    public int getMaxMacroCmd(){
      return env.getMaxMacroCmd();
    }

    void allowNativeXpath(boolean allow) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      accessor.allowNativeXpath(context, allow)
    }

    void addScript(String scriptContent, String scriptTagId){
       WorkflowContext context = WorkflowContext.getDefaultContext();
       accessor.addScript(context, scriptContent, scriptTagId);
    }

    void removeScript(String scriptTagId){
       WorkflowContext context = WorkflowContext.getDefaultContext();
       accessor.removeScript(context, scriptTagId);
    }

     /*void enableLogging(LogLevels levels){
      env.enableLogging(levels);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.enableLogging(context, levels.toString());
    }
    */
    void enableEngineLog(){
       WorkflowContext context = WorkflowContext.getDefaultContext();
       env.setUseEngineLog(true);
       extension.useEngineLog(context, true);
    }

    void disableEngineLog(){
      WorkflowContext context = WorkflowContext.getDefaultContext();
      env.setUseEngineLog(false);
      extension.useEngineLog(context, false);
    }

    void useEngineLog(boolean isUse){
       WorkflowContext context = WorkflowContext.getDefaultContext();
       env.setUseEngineLog(isUse);
       extension.useEngineLog(context, isUse);
    }

    void enableTelluriumEngine(){
      env.setUseNewEngine(true);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useTeApi(context, true);
      this.enableMacroCmd();
    }

    void disableTelluriumEngine(){
//      this.disableCache();
      env.setUseNewEngine(false);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useTeApi(context, false);
      
      this.disableMacroCmd();
    }

  def void setCookie(String cookieName, String value, Object options) {

  }

  def void setCookie(String cookieName, String value) {

  }
}
