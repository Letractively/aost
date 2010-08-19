package telluriumworks

import java.awt.Color
import org.jdesktop.swingx.painter.GlossPainter

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
   menuBar {
      menu('File') {
         menuItem openAction
         separator()
         menuItem quitAction
      }
   }

  borderLayout()

  tabbedPane id: 'tabGroup', constraints: CENTER

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