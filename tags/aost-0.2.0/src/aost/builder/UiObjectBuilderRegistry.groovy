package aost.builder

class UiObjectBuilderRegistry{

    //use this registry to map the ui object name to its builder
    //so that we can let the user customerizes and add new ui objects
    //by simply add the builder to the registry
    def registry = [:]

    def getBuilder(String uiObjectName){

        return registry.get(uiObjectName)
    }

    //register ui object builder
    //users can overload the builders or add new builders for new ui objects
    public void registerBuilder(String uiObjectName, UiObjectBuilder builder){
        registry.put(uiObjectName, builder)
    }

    public UiObjectBuilderRegistry(){
        //put default builders into the register
        registerBuilder("Button", new ButtonBuilder())
        registerBuilder("CheckBox", new CheckBoxBuilder())
        registerBuilder("Container", new ContainerBuilder())
        registerBuilder("Icon", new IconBuilder())
        registerBuilder("InputBox", new InputBoxBuilder())
        registerBuilder("Selector", new SelectorBuilder())
        registerBuilder("TextBox", new TextBoxBuilder())
        registerBuilder("Table", new TableBuilder())
        registerBuilder("UrlLink", new UrlLinkBuilder())
    }
}