package telluriumworks

import javax.swing.JTabbedPane
import static javax.swing.SwingConstants.*

actions {
  action(id: 'openAction',
          name: 'Open',
          mnemonic: 'O',
          accelerator: shortcut('O'),
          closure: controller.openFile)
  action(id: 'runAction',
          name: 'Run',
          mnemonic: 'R',
          accelerator: shortcut('R'),
          closure: controller.runFile)
  action(id: 'saveAction',
          name: 'Save',
          mnemonic: 'S',
          accelerator: shortcut('S'),
          closure: controller.saveFile)
  action(id: 'closeAction',
          name: 'Close',
          mnemonic: 'C',
          accelerator: shortcut('C'),
          closure: controller.closeFile)
  action(id: 'quitAction',
          name: 'Quit',
          mnemonic: 'Q',
          accelerator: shortcut('Q'),
          closure: controller.quit)
  action(id: 'helpAction',
          name: 'Help',
          mnemonic: 'H',
          accelerator: shortcut('H'),
          closure: controller.help)
  action(id: 'aboutAction',
          name: 'About',
          mnemonic: 'A',
          accelerator: shortcut('A'),
          closure: controller.about)
  action(id: 'openFile',
          shortDescription: "Open Tellurium DSL script",
          smallIcon: crystalIcon(size: 24, category: "actions", icon: "fileopen"),
          closure: controller.openFile)
  action(id: 'runFile',
          shortDescription: "Run Tellurium DSL script",
          smallIcon: crystalIcon(size: 24, category: "actions", icon: "player_end1"),
          closure: controller.runFile)
  action(id: 'saveFile',
          shortDescription: "Save Tellurium DSL script",
          smallIcon: crystalIcon(size: 24, category: "actions", icon: "filesave"),
          closure: controller.saveFile)
  action(id: 'closeFile',
          shortDescription: "Close Tellurium DSL script",
          smallIcon: crystalIcon(size: 24, category: "actions", icon: "fileclose"),
          closure: controller.closeFile)
  action(id: 'exitAction',
          shortDescription: "Exit the IDE",
          smallIcon: crystalIcon(size: 24, category: "actions", icon: "exit"),
          closure: controller.quit)
}

fileChooserWindow = fileChooser()
mainWindow = application(title: 'TelluriumWorks',
        size: [480, 400], locationByPlatform: true,
        iconImage: imageIcon('/tellurium.png').image,
        iconImages: [imageIcon('/tellurium.png').image]) {
  lookAndFeel('system')

  menuBar {
    menu('File') {
      menuItem openAction
      menuItem saveAction
      menuItem closeAction
      separator()
      menuItem runAction
      separator()
      menuItem quitAction
    }
    menu('Help') {
      menuItem helpAction
      separator()
      menuItem aboutAction
    }
  }

  borderLayout()
  splitPane(id: "splitPane1", orientation: HORIZONTAL, resizeWeight: 0.8f) {

    tabbedPane(constraints: CENTER, tabPlacement: JTabbedPane.LEFT, selectedIndex: 0) {

      panel(title: "Script", tabIcon: crystalIcon(size: 32, category: "apps", icon: "kmenuedit"),
              tabToolTip: "Tellurium DSL Script") {
        migLayout(layoutConstraints: 'fill')
        tabbedPane(id: 'tabGroup', preferredSize: [600, 480], constraints: "grow 100 1, wrap")
      }

      panel(title: "Server", id: 'box', tabIcon: crystalIcon(size: 32, category: "apps", icon: "multiple_monitors"),
              tabToolTip: "Selenium Server") {
        migLayout(layoutConstraints: 'fill')

        label('Server')
        buttonGroup(id: 'mode')
        radioButton('local', buttonGroup: mode, selected: true, actionPerformed: {model.mode = 'local'})
        radioButton('remote', buttonGroup: mode, constraints: 'wrap', actionPerformed: {model.mode = 'remote'})

        label('Port:')
        textField(columns: 20, constraints: 'span 2, wrap',
                text: bind('port', target: model, mutual: true))

        label('Profile:')
        textField(columns: 20, constraints: 'span 2, wrap',
                text: bind('profile', target: model, mutual: true))

        checkBox(id: "multipleWindow", selected: false, text: "Multiple Windows", constraints: 'span 2,wrap')

        separator(constraints: 'grow, span 3, wrap')

        button(id: 'runBtn',
                label: "Run",
                actionPerformed: {controller.runSeleniumServer()},
                constraints: "span 2, left"
        )

        button(id: 'stopBtn',
                label: "Stop",
                actionPerformed: {controller.stopSeleniumServer()},
                constraints: "span 2, right"
        )

      }

      panel(title: "Config", tabIcon: crystalIcon(size: 32, category: "apps", icon: "kdmconfig"),
              tabToolTip: "Tellurium Configuration") {
        migLayout(layoutConstraints: 'fill')
        label("Browser:")
        comboBox(id: 'browserType',
                items: ["*chrome", "*firefox", "*iexplore", "*iehta"],
                selectedIndex: 0,
                constraints: "span 2, wrap"
        )

        label("Server Host:")

        textField(id: 'selServerHost',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('serverHost', target: model, mutual: true)
        )

        label("Server Port:")

        textField(id: 'selServerPort',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('serverPort', target: model, mutual: true)
        )

        label("Macro Command:")

        textField(id: 'macroCmdValue',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('macroCmd', target: model, mutual: true)
        )

        label("Option:")

        textField(id: 'optionValue',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('option', target: model, mutual: true)
        )

        checkBox(id: "useTrace", selected: false, text: "Trace", constraints: 'wrap')

        checkBox(id: "useScreenShot", selected: false, text: "ScreenShot", constraints: 'wrap')

        label("locale:")
        comboBox(id: 'localeType',
                items: ["en_US", "fr_FR", "zh_CN"],
                selectedIndex: 0,
                constraints: "span 2, wrap"
        )
        button(id: 'applyBtn',
                label: "Apply",
                actionPerformed: {controller.setTelluriumConfig()},
                constraints: "span 2, center")

      }

    }
    panel(border: titledBorder('Console'), constraints: "grow 15 1, wrap") {
      migLayout(layoutConstraints: 'fill')

      button(id: 'clearConsole',
              label: "Clear",
              actionPerformed: {this.consoleTxt.text = ""},
              constraints: "span 2,wrap, right"
      )
      scrollPane(preferredSize: [400, 100], constraints: "grow 100 1, wrap") {
        textArea(id: "consoleTxt",
                editable: false,
                lineWrap: true,
                wrapStyleWord: true)
      }
    }


  }

  toolBar(constraints: 'left', floatable: false, opaque: false) {
    button(openFile, constraints: 'left')
    button(runFile, constraints: 'left')
    button(saveFile, constraints: 'left')
    button(closeFile, constraints: 'left')
    button(exitAction, constraints: 'left')
  }
}


