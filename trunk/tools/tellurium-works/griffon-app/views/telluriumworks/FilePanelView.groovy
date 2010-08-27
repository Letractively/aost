package telluriumworks

import java.awt.Color
import javax.swing.JScrollPane
import java.awt.BorderLayout

actions {
   action(id: 'runAction',
      name: 'Run',
      mnemonic: 'R',
      accelerator: shortcut('R'),
      closure: controller.runFile)
   action(id: 'saveAction',
      enabled: bind {model.dirty},
      name: 'Save',
      mnemonic: 'S',
      accelerator: shortcut('S'),
      closure: controller.saveFile)
   action(id: 'closeAction',
      name: 'Close',
      mnemonic: 'C',
      accelerator: shortcut('C'),
      closure: controller.closeFile)
}

filePanes = tabbedPane(tabGroup, selectedIndex: tabGroup.tabCount) {
   panel(title: tabName, id: 'tab', clientProperties: [mvcId: mvcId]) {
      borderLayout()
      scrollPane(constraints: BorderLayout.CENTER) {
        editorPane(id: 'editor', contentType:'text/groovy', text: bind {model.document.contents},
            opaque: false, editable: true,
            font: new java.awt.Font("Ariel", 0, 12),
            background: new Color(0,0,0,0))
 //           gridwidth: REMAINDER, weightx: 1.0, fill: BOTH, insets: [3, 3, 3, 6])
//         textArea(id: 'editor', text:  bind {model.document.contents})
      }
   }
}

bean(model.document, dirty: bind {editor.text != model.document.contents})
