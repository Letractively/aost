package telluriumworks

import java.awt.Color
import org.jdesktop.swingx.painter.GlossPainter

gloss = glossPainter(paint: new Color(1f, 1f, 1f, 0.2f),
    position: GlossPainter.GlossPosition.TOP)
stripes = pinstripePainter(paint: new Color(1f, 1f, 1f, 0.17f),
                           spacing: 5f)
matte = mattePainter(fillPaint: new Color(51, 51, 51))
compound = compoundPainter(painters: [matte, stripes, gloss])

application(title: 'TelluriumWorks',
  //size: [320,480],
  pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/tellurium.png').image,
  iconImages: [imageIcon('/tellurium.png').image]) {

    borderLayout()
    jxheader(constraints: NORTH,
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
    } 
}
