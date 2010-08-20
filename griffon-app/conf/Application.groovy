application {
    title = 'Telluriumworks'
    startupGroups = ['telluriumworks']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "telluriumworks"
    'telluriumworks' {
        model = 'telluriumworks.TelluriumworksModel'
        controller = 'telluriumworks.TelluriumworksController'
        view = 'telluriumworks.TelluriumworksView'
    }
  
    'FilePanel' {
        model = 'telluriumworks.FilePanelModel'
        controller = 'telluriumworks.FilePanelController'
        view = 'telluriumworks.FilePanelView'
    }
}
