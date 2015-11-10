

# Tellurium Widgets #

Tellurium team is refactoring the trunk code base into multiple sub-projects. One purpose is to separate tellurium core framework from its extensions. Tellurium widget is a good example of tellurium extensions. Tellurium provides you the capability to composite UI objects into a widget object and then you can use the widget directly just like using a tellurium UI object. The advantage is that you do not need to deal with the UI at the link or button level for the widget, you just work on the high level methods. Another advantage is that this widget is reusable.

## Widget Implementation ##

Usually, Java script frameworks provide a lot of widgets. Take the Dojo framework as an example. We use the widget DatePicker to prototype the tellurium widget. The steps to define and use the widget are listed as follows,

### Extend the widget object and define the DojoWidget object with its name space as "DOJO" ###

For widgets, it is important to include name space to avoid name collision between different widget modules. For example, what is Dojo and ExtJs both have the widget Date Picker? After add the name space, the widget will be like "DOJO\_DatePicker".

### Define widget by combining tellurium UI Objects ###

For example, the DataPicker widget is defined as follows,

```
class DatePicker extends DojoWidget{

    public void defineWidget() {
        ui.Container(uid: "DatePicker", locator: "/div[@class='datePickerContainer' and child::table[@class='calendarContainer']]"){
            Container(uid: "Title", locator: "/table[@class='calendarContainer']/thead/tr/td[@class='monthWrapper']/table[@class='monthContainer']/tbody/tr/td[@class='monthLabelContainer']"){
                Icon(uid: "increaseWeek", locator: "/span[@dojoattachpoint='increaseWeekNode']")
                Icon(uid: "increaseMonth", locator: "/span[@dojoattachpoint='increaseMonthNode']")
                Icon(uid: "decreaseWeek", locator: "/span[@dojoattachpoint='decreaseWeekNode']")
                Icon(uid: "decreaseMonth", locator: "/span[@dojoattachpoint='decreaseMonthNode']")
                TextBox(uid: "monthLabel", locator: "/span[@dojoattachpoint='monthLabelNode']")   
            }
            StandardTable(uid: "calendar", locator: "/table[@class='calendarContainer']/tbody/tr/td/table[@class='calendarBodyContainer']"){
                TextBox(uid: "header: all", locator: "")
                ClickableUi(uid: "all", locator: "")
            }
            Container(uid: "year", locator: "/table[@class='calendarContainer']/tfoot/tr/td/table[@class='yearContainer']/tbody/tr/td/h3[@class='yearLabel']"){
                Span(uid: "prevYear", locator: "/span[@class='previousYear' and @dojoattachpoint='previousYearLabelNode']")
                TextBox(uid: "currentYear", locator: "/span[@class='selectedYear' and @dojoattachpoint='currentYearLabelNode']")
                Span(uid: "nextYear", locator: "/span[@class='nextYear' and @dojoattachpoint='nextYearLabelNode']")
            }
        }
    }
}
```

Since widget is a fixed block of UIs, it is fine to use the XPath directly instead of the composite locator.

### Define widget methods ###

For example, we defined the following methods:

```
    public String getCurrentYear(){
        return getText("DatePicker.year.currentYear")
    }

    public void selectPrevYear(){
        click "DatePicker.year.prevYear"
    }
```

### Define widget builder ###

The widget is treated as a tellurium UI object and the builder is the same as regular tellurium objects

```
class DatePickerBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        DatePicker datepicker = this.internBuild(new DatePicker(), map, df)
        datepicker.defineWidget()

        return datepicker
    }
}
```

### Hook the widget into the Tellurium Core framework ###

Each widget module will be compiled as a separate jar file and it should define a bootstrap class to register all the widgets inside the module. By default, the full class name of the bootstrap class is org.tellurium.widget.XXXX.Init, where the class Init should implement the WidgetBootstrap interface to register widgets and XXXX stands for the widget module name. It is DOJO in our case.

```
class Init implements WidgetBootstrap{
    public void loadWidget(UiObjectBuilderRegistry uiObjectBuilderRegistry) {
        if(uiObjectBuilderRegistry != null){
           uiObjectBuilderRegistry.registerBuilder(getFullName("DatePicker"), new DatePickerBuilder())      
        }
    }
}
```

Then in the tellurium configuration file TelluriumConfig.groovy, you should include your module name there,

```
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included="dojo"
        }
    }
```

If you use your own package name for the bootstrap class, for example, com.mycompay.widget.Boot, then you should specify the full name there like

```
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included="com.mycompay.widget.Boot"
        }
    }
```

Note, you can load multiple widget modules into the Tellurium Core framework by define

```
  included="dojo, com.mycompay.widget.Boot"
```

### Use widgets ###

Widget is defined as a regular tellurium UI object. For example,

```
class DatePickerDemo extends DslContext{
    
    public void defineUi() {
        ui.Form(uid: "dropdown", clocator: [:], group: "true"){
            TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])
            InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])
            Image(uid: "selectDate", clocator: [title: "select a date", dojoattachpoint: "containerDropdownNode", alt: "date"])
            DOJO_DatePicker(uid: "datePicker", clocator: [tag: "div", dojoattachpoint: "subWidgetContainerNode"])
        }
    }
}
```

Then on the module file DatePickerDemo, you can call the widget methods instead of dealing with low level links, buttons, and so on.

To make the testing more expressive, Tellurium provides an onWidget method

```
  onWidget(String uid, String method, Object[] args)
```

In that way, we can call the widget methods as follows:

```
onWidget "dropdown.datePicker", selectPrevYear
```

This prototype is on the tellurium trunk/extensions/dojo-widget project. Please see the project for details and try out by yourself. Your feedback is always welcome.

## Tellurium Widget Archetype ##

To create a Tellurium UI widget project, we can use Tellurium Widget archetype as follows,

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-widget-archetype -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```