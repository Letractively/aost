package org.telluriumsource.dsl

import org.telluriumsource.exception.NotWidgetObjectException
import org.telluriumsource.ui.widget.Widget
import org.telluriumsource.framework.Environment

abstract class DslContext implements IDslContext {

  private IDslContext delegate;

}