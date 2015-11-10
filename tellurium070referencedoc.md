# Introduction #

Tellurium 0.7.0 is a major release and we have worked on this release for almost one year.
We have closed over 200 issues and have included many interesting new features. There have been a lot of fundamental changes in Tellurium 0.7.0 as compared to 0.6.0 such as the group locating algorithm, module caching, macro command, jquery based APIs and i18n support.

To make it easier for people to get familiar with Tellurium, we prepared the reference document to cover almost everything you need to know about Tellurium. The pdf file is about 280 pages, which can be downloaded from [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf) and the table of contents is listed as follows.

```
1. Overview of Tellurium

    What is Tellurium
    Tellurium, the New Approach for Web Testing
    Tellurium Architecture
    History of Tellurium
    Tellurium Team and Community
    Tellurium Sub-projects

2. What's New in Tellurium 0.7.0

    Introduction
    New features
    Changes in Engine
    Changes in Maven Build
    Reference Project
    How to Obtain Tellurium 0.7.0

3. Getting Started

    Create a Tellurium Project

        Tellurium Maven Archetypes
        Tellurium Ant Projects

    Setup Tellurium Project in IDEs

        IntelliJ IDEA
        NetBeans
        Eclipse

    Create a UI Module
    Create Tellurium Test Cases

4. Tellurium UI Objects

    Basic UI Object
    UI Object Default Attributes
    UI Object Description

        Button
        Submit Button
        Check Box
        Div
        Image
        Icon
        Radio Button
        Text Box
        Input Box
        URL Link
        Selector
        Container
        Repeat Object
        Form
        List
        Table
        Standard Table
        Frame
        Window
        Option
        All Purpose Object

5. Tellurium Core Basics

    UiID Attribute
    Locator Attributes
    Group Attribute
    self attribute
    Respond Attribute
    CSS Selector

        New DSL Methods
        Additional CSS Attribute Selectors
        Locator Agnostic Methods

    UI Templates
    "Include" Frequently Used Sets of Elements in UI Modules
    Logical Container
    toggle
    getHTMLSource
    type and keyType
    Customize Individual Test Settings Using setCustomConfig
    User Custom Selenium Extensions
    The Dump Method
    Engine State Offline Update
    Testing Support

        Tellurium JUnit Test Case
        Tellurium TestNG Test Case
        Tellurium Groovy Test Case
        TelluriumMockJUnitTestCase and TelluriumMockTestNGTestCase 

    Tellurium Configuration
    Run DSL Script
    useTelluriumEngine
    Trace
    Methods Accessible in Test Cases
    Environment
    Get UIs by Tag Name
    Misc

6. Tellurium Core APIs

    DSL Methods
    Data Access Methods
    UI Module APIs

        Example
        dump
        toString
        toHTML
        getHTMLSource
        show
        validate
        Closest Match

    Test Support DSLs

        Example:
        Example:

7. Tellurium Core Advanced Topics

    Data Driven Testing

        Data Provider
        TelluriumDataDrivenModule
        Tellurium Data Driven Test
        Implementations

    Selenium Grid Support
    Mock Http Server

        Mock Http Handler Class
        Mock Http Server

    Generate Html Source From UI Modules

        Implementation
        Usage

    Tellurium Powerful Utility: Diagnose

        Implementation
        Usage

    Internationalization support in Tellurium
    Better Reporting With ReportNG
    Macro Command
    Groovy Features Used in Tellurium

        BuildSupport
        Dynamic Scripting
        GroovyInterceptable
        methodMissing
        Singleton
        GString
        Optional Type
        Closure
        Groovy Syntax
        Varargs
        propertyMissing
        Delegate
        Grape

8. Tellurium UID Description Language

    Introduction
    Tellurium UID Description Language

        UDL Grammars
        Routing

    Implementation

        Antlr 3
        Data Structure
        Embedded Java Code
        Parser
        Unit Test

9. Tellurium Widgets

    Introduction
    Widget Implementation
    Tellurium Widget Archetype

10. Tellurium Engine

    Code Structure
    CSS Selector Support

        :te_text
        :group
        :styles
        :nextToLast
        outerHTML

    UI Module Group Locating

        Basic Flow
        Data Structures
        Locate
        Relax
        Usage

    UI Module Caching
    New APIs
    Debug Tellurium Engine
    Tellurium UI Module Visual Effect

        Build a Snapshot Tree
        Demo

    Engine Logging
    JavaScript Error Stack

11. Tellurium Maven Archetypes

    Prerequisites
    settings.xml
    Tellurium JUnit Archetype
    Tellurium TestNG Archetype
    Tellurium Widget Archetype

12. Tellurium UI Module Firefox Plugin

    Install TrUMP
    TrUMP Workflow
    How TrUMP Works
    The UI Module Generating Algorithm

13. Tellurium Reference Projects

    Introduction
    Tellurium Website Project

        Create Custom UI objects
        Create UI modules
        Create Java Test Cases
        Create and Run DSL Scripts
        Data Driven Testing

    Tellurium ui-examples Project

        Selector
        Container
        Form
        List
        Table
        Repeat

14. Tellurium Reference

    Maven
    DocBook

        Book
        Chapter
        section
        Link
        Image
        screen
        programlist
        list
        table

    Tools

A. FAQs

    When Did Tellurium Start?
    What Are the Main Differences Between Tellurium and Selenium?
    Do I Need to Know Groovy Before I Use Tellurium?
    What Unit Test and Functional Test Frameworks Does Tellurium Support?
    Does Tellurium Provide Any Tools to Automatically Generate UI Modules?
    What Build System Does Tellurium Use?
    What is the Best Way to Create a Tellurium Project?
    Where Can I Find API Documents for Tellurium?
    Is There a Tellurium Tutorial Available?
    Where Can I Find a Sample Tellurium Configuration File?
    Tellurium Dependencies
    What Is the ui. in UI Module?
    How Do I Add My Own UI Object to Tellurium?
    How to Build Tellurium from Sourc
    What is the Issue with Selenium XPath Expressions and Why is There a Need to Create a UI Module?
    How to write assertions in Tellurium DSL scripts
    How to upgrade Firefox version in Selenium server
    How to run Selenium server remotely in Tellurium
    Differences among Tellurium Table, List, and Container
    How do I use a Firefox profile in Tellurium
    How to Overwrite Tellurium Settings in My Test Class
    How to reuse a frequently used set of elements
    How to handle Table with multiple tbody elements
    How to Run Tellurium Tests in Different Browsers
    How to use the new XPath library in Selenium
    How to Debug Selenium Core
    How to Debug Tellurium in IE
    How to use jQuery Selector with weird characters in its ID
    How to Use Tellurium for XHTML
    hat Are the Differences Between connectUrl and openUrl
    How to do Attribute Partial Matching in Tellurium
    What are the rules to define Tellurium UIDs
    How to load Tellurium configuration from a String
    How to register a custom method in Tellurium API
    How to Access Tellurium Maven Repo Behind a Firewall
    How to Generate IDE project files
    How to Run Tellurium Tests in Google Chrome
    How to run headless tests with Xvfb
    How to setup Groovy Grape
    How to Search Tellurium Documents
    How to Contribute to Tellurium
    Tellurium Future Directions

B. Tellurium Project Setup with IntelliJ

    Prerequisites
    Project Setup
    Summary

C. Integration Tests with Maven Cargo Plugin

    Introduction
    Example

        Maven Update
        Tellurium Tests

    Cargo Maven Plugin

D. Use Firebug and JQuery to Trace Problems in Tellurium Tests

    Prerequisites

        Firefox Profile
        Firebug Support

    Debug and Trace

        Debug JavaScript Using Firebug
        Trace Problems Using jQuery

E. Resources

    Tellurium Community
    Users' Experiences
    Interviews
    Presentations and Videos
    IDEs
    Build
    Related

F. Sample Maven Configurations

    settings.xml
    Sample Tellurium Project Maven POM

G. Sample Tellurium Project Configuration

    TelluriumConfig.groovy
    JSON String

H. Sample Ant Build Script

    build.properties
    build.xml

I. Sample Run DSL Script

    Sample Groovy Grape Configuration
    rundsl.groovy
```