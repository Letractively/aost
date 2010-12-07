root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
jx {
    'groovy.swing.SwingXBuilder' {
        view = '*'
    }
}

root.'MiglayoutGriffonAddon'.addon=true


root.'JsyntaxpaneGriffonAddon'.addon=true

root.'TangoiconsGriffonAddon'.addon=true


root.'I18nGriffonAddon'.addon=true

root.'ValidationGriffonAddon'.addon=true
