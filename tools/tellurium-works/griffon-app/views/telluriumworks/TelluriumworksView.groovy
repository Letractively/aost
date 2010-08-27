package telluriumworks

import javax.swing.JTabbedPane
import static javax.swing.SwingConstants.*
import net.miginfocom.swing.MigLayout
import java.awt.Color

import java.awt.Dimension
import java.awt.GradientPaint
import java.awt.Graphics
import java.awt.Graphics2D

import javax.swing.JPanel
import javax.swing.JLabel;


import javax.swing.JScrollPane
import javax.swing.table.DefaultTableModel
import javax.swing.UIManager

import org.jdesktop.swingx.JXPanel

import static griffon.util.GriffonApplicationUtils.isMacOSX

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
  action(id: 'tipsAction',
          name: 'Tips',
          mnemonic: 'T',
          accelerator: shortcut('T'),
          closure: controller.showTips)
  action(id: 'aboutAction',
          name: 'About',
          mnemonic: 'A',
          accelerator: shortcut('A'),
          closure: controller.about)
  action(id: 'openFile',
          shortDescription: "Open Tellurium DSL script",
//          smallIcon: crystalIcon(size: 24, category: "actions", icon: "fileopen"),
          smallIcon: tangoIcon(size: 22, category: "actions", icon: "document-open"),
          closure: controller.openFile)
  action(id: 'runFile',
          shortDescription: "Run Tellurium DSL script",
//          smallIcon: crystalIcon(size: 24, category: "actions", icon: "player_end1"),
          smallIcon: tangoIcon(size: 22, category: "actions", icon: "go-last"),
          closure: controller.runFile)
  action(id: 'saveFile',
          shortDescription: "Save Tellurium DSL script",
//          smallIcon: crystalIcon(size: 24, category: "actions", icon: "filesave"),
          smallIcon: tangoIcon(size: 22, category: "actions", icon: "document-save"),
          closure: controller.saveFile)
  action(id: 'closeFile',
          shortDescription: "Close Tellurium DSL script",
//          smallIcon: crystalIcon(size: 24, category: "actions", icon: "fileclose"),
          smallIcon: tangoIcon(size: 22, category: "actions", icon: "process-stop"),
          closure: controller.closeFile)
  action(id: 'exitAction',
          shortDescription: "Exit the IDE",
//          smallIcon: crystalIcon(size: 24, category: "actions", icon: "exit"),      t
          smallIcon: tangoIcon(size: 22, category: "actions", icon: "system-log-out"),
          closure: controller.quit)
}

fileChooserWindow = fileChooser()
mainWindow = application(title: 'TelluriumWorks',
        size: [640, 600], locationByPlatform: true,
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
      if (!isMacOSX) {
        separator()
        menuItem quitAction
      }
    }
    menu('Help') {
 //     menuItem helpAction
      menuItem tipsAction
      separator()
      menuItem aboutAction
    }
  }

  borderLayout()
  splitPane(id: "splitPane1", orientation: HORIZONTAL, resizeWeight: 0.85f) {

    tabbedPane(id: "mainPane", constraints: CENTER, tabPlacement: JTabbedPane.LEFT, selectedIndex: 0) {

      panel(title: "Script",
//              tabIcon: crystalIcon(size: 32, category: "apps", icon: "kmenuedit"),
              tabIcon: tangoIcon(size: 32, category: "apps", icon: "accessories-text-editor"),
              tabToolTip: "Tellurium DSL Script") {
        migLayout(layoutConstraints: 'fill')
        tabbedPane(id: 'tabGroup', preferredSize: [800, 640], constraints: "grow 100 1, wrap")
        noparent {
          tabGroup.addChangeListener(model)
        }
      }

      panel(title: "Server", id: 'box',
//              tabIcon: crystalIcon(size: 32, category: "apps", icon: "multiple_monitors"),
              tabIcon: tangoIcon(size: 32, category: "status", icon: "network-idle"),
              tabToolTip: "Selenium Server") {
        migLayout(layoutConstraints: 'fill')

        label('Status')
        textField(id: "serverStatus", columns: 10, constraints: 'span 2, wrap',
                text: bind('serverStatus', source: model.serverConfig, mutual: true))

        separator(constraints: 'grow, span 3, wrap')

        label('Server')
        buttonGroup(id: 'mode')
        radioButton('local', buttonGroup: mode, selected: true, actionPerformed: {model.serverConfig.local = true})
        radioButton('remote', buttonGroup: mode, constraints: 'wrap', actionPerformed: {model.serverConfig.local = false})

        label('Port:')
        textField(id: "localServerPort", columns: 20, constraints: 'span 2, wrap',
                text: bind('port', source: model.serverConfig, mutual: true))

        label('Profile:')
        textField(columns: 20, constraints: 'span 2, wrap',
                text: bind('profile', source: model.serverConfig, mutual: true))

        checkBox(id: "multipleWindow", selected: bind('multipleWindow', source:model.serverConfig, mutual: true), text: "Multiple Windows", constraints: 'span 2,wrap')

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

      panel(title: "Config",
//              tabIcon: crystalIcon(size: 32, category: "apps", icon: "kdmconfig"),
              tabIcon: tangoIcon(size: 32, category: "categories", icon: "preferences-system"),
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
                text: bind('serverHost', source: model.telluriumConfig, mutual: true)
        )

        label("Server Port:")

        textField(id: 'selServerPort',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('serverPort', source: model.telluriumConfig, mutual: true)
        )

        label("Macro Command:")

        textField(id: 'macroCmdValue',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('macroCmd', source: model.telluriumConfig, mutual: true)
        )

        label("Option:")

        textField(id: 'optionValue',
                columns: 20,
                constraints: "span 2, wrap",
                text: bind('option', source: model.telluriumConfig, mutual: true)
        )

//        checkBox(id: "useTrace", selected: false, text: "Trace", constraints: 'wrap')
        checkBox(id: "useTrace", selected: bind ('useTrace', source: model.telluriumConfig, mutual: true), text: "Trace", constraints: 'wrap')

//        checkBox(id: "useScreenShot", selected: false, text: "ScreenShot", constraints: 'wrap')

        checkBox(id: "useScreenShot", selected: bind ('useScreenShot', source: model.telluriumConfig, mutual: true), text: "ScreenShot", constraints: 'wrap')

        checkBox(id: "useTelluriumEngine", selected: bind('useTelluriumEngine', source: model.telluriumConfig, mutual: true), text: "New Engine", constraints: 'wrap')

        label("locale:")
        comboBox(id: 'localeType',
                items: ["en_US", "fr_FR", "zh_CN"],
                selectedIndex: 0,
                constraints: "span 2, wrap"
        )
        separator(constraints: 'grow, span 3, wrap')
        
        button(id: 'applyBtn',
                label: "Apply",
                actionPerformed: {controller.updateTelluriumConfig()},
                constraints: "span 2, center")

      }

    }          

    hstrut(5)

    panel(border: titledBorder('Console'), constraints: "grow 15 1, wrap") {
      migLayout(layoutConstraints: 'fill')
      button(id: 'clearConsole',
              label: "Clear",
//              actionPerformed: {this.consoleTxt.text = ""},
              actionPerformed: {controller.clearConsole()},
              constraints: "span 2,wrap, right"
      )

      scrollPane(preferredSize: [800, 640], constraints: "grow 100 1, wrap") {
        textArea(id: "consoleTxt",
                editable: false,
                lineWrap: true,
                wrapStyleWord: true,
                text: bind('consoleText', target: model))
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

bean(model.telluriumConfig, browser: bind {browserType.selectedItem})
bean(model.telluriumConfig, locale: bind {localeType.selectedItem})

JPanel buildTitlePanel() {
	def mess = """ ${ResourceUtils.getString('about.message1')}
    	\n\n\251${ResourceUtils.getString('about.message2')}
    	\n\n${ResourceUtils.getString("version")} - ${ResourceUtils.getString('version.date')}"""

    panel( title: "Title", layout: new MigLayout("inset 0 0 0 0", "grow, fill", "grow, fill")) {
		textArea(mess.toString())
    }
}

JPanel buildSystemPropertiesPanel() {
	m_model = new DefaultTableModel()
	m_model.addColumn("Property")
	m_model.addColumn("Value")

	// load the data
	try {
		Properties props = System.getProperties();
		props.each{k,v ->
			Object[] row = new Object[2]
			row[0] = k
			row[1] = v
			m_model.addRow(row)
		}
	} catch (Exception e) {	}

	panel(title: "System", layout: new MigLayout("insets 0 0 0 0", "grow, fill", "grow, fill"), opaque: false) {
		scrollPane() {
			table(id: 'table', model: m_model)
		}
	}
}

JScrollPane addTab(final String tabTitle, final String resource) {
//	URL url = getClass().getResource(resource)
    URL url = this.getClass().getClassLoader().getResource(resource)
	scrollPane(title: tabTitle) {
		editorPane(page: url, preferredSize: new Dimension(500,300),
			background: UIManager.getColor("control"), editable: false)
	}
}

class AboutGradientPanel extends JXPanel
{
	public void paintComponent(Graphics g) {
		if( !this.visible ) return
		super.paintComponent(g)
		if(!isOpaque()) {
			return
		} else {
			Color control = UIManager.getColor("control")
			int width = getWidth()
			int height = getHeight()
			Graphics2D g2 = (Graphics2D)g
			java.awt.Paint storedPaint = g2.getPaint()
			g2.setPaint(new GradientPaint(0.0F, 0.0F, getBackground(), width, 0.0F, control))
			g2.fillRect(0, 0, width, height)
			g2.setPaint(storedPaint)
			return
		}
	}
}

JPanel buildHeader() {
	MigLayout mlayout = new MigLayout("insets 0 0 0 0", "[][grow, fill]")

	AboutGradientPanel apanel = new AboutGradientPanel(background: Color.WHITE)
	apanel.setLayout(mlayout)
	apanel.add(new JLabel(ResourceUtils.getIcon("about.header.image")))
	apanel.add(new JLabel(ResourceUtils.getString("about.title")))
	return apanel
}

dialog(title: ResourceUtils.getString("about.title"), id: "aboutDialog", modal: true  ) {
	panel(layout: new MigLayout("insets 0 0 0 0", "grow, fill") ) {
		container(buildHeader(), constraints:"cell 0 0")
		separator(constraints:"cell 0 1")
		tabbedPane(constraints: "cell 0 2") {
			buildTitlePanel()
			buildSystemPropertiesPanel()
			addTab("Release Notes", "docs/release.html")
			addTab("Resources", "docs/resources.html")
		}
		panel(constraints: "cell 0 4" ) {
			button("Close", actionPerformed: {event ->
				aboutDialog.dispose()
			})
		}
	}
}