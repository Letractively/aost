package telluriumworks

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
   panel(title: tabName, id: 'tab') {
      borderLayout()
      scrollPane(constraints: CENTER) {
         textArea(id: 'editor', text: bind {model.fileText})
      }
      hbox(constraints: SOUTH) {
         button runAction
         button saveAction
         button closeAction
      }
   }
}

bean(model, dirty: bind {editor.text != model.fileText})
