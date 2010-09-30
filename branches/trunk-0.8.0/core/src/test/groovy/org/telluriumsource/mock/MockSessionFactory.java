package org.telluriumsource.mock;

import org.telluriumsource.component.bundle.BundleProcessor;
import org.telluriumsource.component.connector.CustomSelenium;
import org.telluriumsource.component.connector.SeleniumConnector;
import org.telluriumsource.component.custom.Extension;
import org.telluriumsource.component.data.Accessor;
import org.telluriumsource.component.dispatch.Dispatcher;
import org.telluriumsource.component.event.EventHandler;
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.crosscut.i18n.ResourceBundle;
import org.telluriumsource.dsl.SeleniumWrapper;
import org.telluriumsource.dsl.TelluriumApi;
import org.telluriumsource.dsl.UiDslParser;
import org.telluriumsource.framework.DefaultLookup;
import org.telluriumsource.framework.Lookup;
import org.telluriumsource.framework.RuntimeEnvironment;
import org.telluriumsource.framework.Session;
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry;
import org.telluriumsource.ui.locator.JQueryOptimizer;
import org.telluriumsource.ui.locator.LocatorProcessor;
import org.telluriumsource.ui.widget.WidgetConfigurator;
import org.telluriumsource.util.BaseUtil;

import java.util.Locale;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 20, 2010
 */
public class MockSessionFactory {

    public static Session getNewSession() {
        RuntimeEnvironment env = new RuntimeEnvironment();
        String name = Thread.currentThread().getName();
        name = name + "@" + BaseUtil.toBase62(System.currentTimeMillis());
        Lookup lookup = new DefaultLookup();
        IResourceBundle i18nBundle = new org.telluriumsource.crosscut.i18n.ResourceBundle();
        env.setResourceBundle(i18nBundle);

        UiDslParser parser = new UiDslParser();
        lookup.register("uiParser", parser);
        EventHandler eventHandler = new EventHandler();
        lookup.register("eventHandler", eventHandler);
        Dispatcher dispatcher = new Dispatcher();
        lookup.register("dispatcher", dispatcher);
        Accessor accessor = new Accessor();
        lookup.register("accessor", accessor);
        Extension extension = new Extension();
        lookup.register("extension", extension);
        BundleProcessor bundleProcessor = new BundleProcessor();
        lookup.register("bundleProcessor", bundleProcessor);
        SeleniumConnector connector = new SeleniumConnector();
        lookup.register("connector", connector);
        SeleniumWrapper wrapper = new SeleniumWrapper();
        lookup.register("wrapper", wrapper);
        TelluriumApi api = new TelluriumApi();
        lookup.register("api", api);
        JQueryOptimizer optimizer = new JQueryOptimizer();
        lookup.register("optimizer", optimizer);
        LocatorProcessor locatorProcessor = new LocatorProcessor();
        lookup.register("locatorProcessor", locatorProcessor);
        IResourceBundle resourceBundle = new ResourceBundle();
        lookup.register("resourceBundle", resourceBundle);
        UiObjectBuilderRegistry uiObjectBuilderRegistry = new UiObjectBuilderRegistry();
        lookup.register("uiObjectBuilderRegistry", uiObjectBuilderRegistry);

        WidgetConfigurator widgetConfigurator = new WidgetConfigurator();
        lookup.register("widgetConfigurator", widgetConfigurator);
//         IResourceBundle i18nBundle =  new org.telluriumsource.crosscut.i18n.ResourceBundle();

        CustomSelenium customSelenium = new CustomSelenium(env.getServerHost(), env.getServerPort(), env.getBrowser(), env.getBaseUrl());
        lookup.register("customSelenium", customSelenium);

        String[] split = env.getLocale().split("_");
        Locale loc = new Locale(split[0], split[1]);
        i18nBundle.updateDefaultLocale(loc);

        env.setResourceBundle(i18nBundle);

        lookup.register("i18nBundle", i18nBundle);

        parser.setProperty("i18nBundle", i18nBundle);
        parser.setProperty("builderRegistry", uiObjectBuilderRegistry);

//        widgetConfigurator.setProperty("i18nBundle", i18nBundle);
        widgetConfigurator.setProperty("registry", uiObjectBuilderRegistry);

        wrapper.setProperty("ui", parser);
        wrapper.setProperty("i18nBundle", i18nBundle);
        wrapper.setProperty("optimizer", optimizer);
        wrapper.setProperty("locatorProcessor", locatorProcessor);
        wrapper.setProperty("env", env);
        wrapper.setProperty("eventHandler", eventHandler);
        wrapper.setProperty("accessor", accessor);
        wrapper.setProperty("extension", extension);

        api.setProperty("ui", parser);
        api.setProperty("env", env);
        api.setProperty("i18nBundle", i18nBundle);
        api.setProperty("locatorProcessor", locatorProcessor);
        api.setProperty("optimizer", optimizer);
        api.setProperty("eventHandler", eventHandler);
        api.setProperty("accessor", accessor);
        api.setProperty("extension", extension);

        bundleProcessor.setProperty("i18nBundle", i18nBundle);
        bundleProcessor.setProperty("dispatcher", dispatcher);
        bundleProcessor.setProperty("env", env);

        accessor.setProperty("cbp", bundleProcessor);

        eventHandler.setProperty("cbp", bundleProcessor);
        eventHandler.setProperty("i18nBundle", i18nBundle);

        extension.setProperty("cbp", bundleProcessor);

        customSelenium.setProperty("i18nBundle", i18nBundle);

        dispatcher.setProperty("i18nBundle", i18nBundle);
        dispatcher.setProperty("sel", customSelenium);
        dispatcher.setProperty("env", env);

        connector.setProperty("sel", customSelenium);
        connector.setProperty("processor", bundleProcessor);
        connector.setProperty("commandProcessor", customSelenium.getCommandProcessor());

        //customize configuration with Environment variables
        connector.setProperty("seleniumServerHost", env.getServerHost());
        connector.setProperty("port", env.getServerPort());
        connector.setProperty("browser", env.getBrowser());
        Session session = new Session();
        session.setSessionId(name);
        session.setEnv(env);
        session.setLookup(lookup);
        session.setApi(api);
        session.setWrapper(wrapper);
        session.setI18nBundle(i18nBundle);

        return session;
    }
}
