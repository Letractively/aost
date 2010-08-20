package telluriumworks

import java.awt.Color
import org.jdesktop.swingx.painter.GlossPainter
import net.miginfocom.swing.MigLayout

gloss = glossPainter(paint: new Color(1f, 1f, 1f, 0.2f),
    position: GlossPainter.GlossPosition.TOP)
stripes = pinstripePainter(paint: new Color(1f, 1f, 1f, 0.17f),
                           spacing: 5f)
matte = mattePainter(fillPaint: new Color(51, 51, 51))
compound = compoundPainter(painters: [matte, stripes, gloss])
actions {
   action(id: 'openAction',
      name: 'Open',
      mnemonic: 'O',
      accelerator: shortcut('O'),
      closure: controller.openFile)
   action(id: 'quitAction',
      name: 'Quit',
      mnemonic: 'Q',
      accelerator: shortcut('Q'),
      closure: controller.quit)
}

fileChooserWindow = fileChooser()
fileViewerWindow = application(title: 'TelluriumWorks',
  size:[480,320], locationByPlatform:true,
  iconImage: imageIcon('/tellurium.png').image,
  iconImages: [imageIcon('/tellurium.png').image]) {
  lookAndFeel('system')
  
   menuBar {
      menu('File') {
         menuItem openAction
         separator()
         menuItem quitAction
      }
   }

  borderLayout()
  //tabbedPane(id: 'tabGroup', selectedIndex: bind(value:model.tabSelected), constraints: CENTER)    //selectedIndex:bind{model.tabSelected},
                         
  panel(layout: new MigLayout('fill')) {
    panel(layout: new MigLayout('fill'), border: titledBorder('File'), constraints: 'grow 100 1, wrap, height 70%') {

         panel(layout: new MigLayout('fill'), constraints: 'grow 50 , wrap' ) {
                label('Tellurium Script:', constraints: 'left')
                button ( id: 'runScript',
                  label: "Run",
                  actionPerformed: {this.scriptTxt.text=""},
                  constraints: "right,width 10px ,shrinkx,shrinky"
                )

         }

        scrollPane(constraints: 'grow,width 50%, height 70%') {
            textArea(id: "scriptTxt",
                editable: true,
                lineWrap: true,
                wrapStyleWord: true)
        }
    }

     panel(layout: new MigLayout('fill'), border: titledBorder('Console'), constraints: 'grow 100 1, wrap, height 30%') {
         button ( id: 'clearConsole',
                  label: "Clear",
                      actionPerformed: {this.consoleTxt.text=""},
                      constraints: "right,width 10px ,shrinkx,shrinky,wrap"
                )
        scrollPane(constraints: 'growx,width 100%,height 80%,span') {
             textArea(id: "consoleTxt",
                editable: false,
                lineWrap: true,
                wrapStyleWord: true)
        }
    }
  }
/*    jxheader(constraints: NORTH,
        title: "Tellurium Works",
        description: "Tellurium IDE to run Tellurium DSL script",
        titleForeground: Color.WHITE,
        descriptionForeground: Color.WHITE,
        icon: imageIcon("/tellurium.png"),
        preferredSize: [480,80],
        backgroundPainter: compound
    )
    jxtaskPaneContainer(constraints: CENTER) {
        jxtaskPane(title: "Task group 1") {
            jxlabel("Action 1")
        }
        jxtaskPane(title: "Task group 2", expanded: false) {
            label("Action 2")
        }
    } */
}