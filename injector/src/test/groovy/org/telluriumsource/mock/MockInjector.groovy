package org.telluriumsource.mock

import org.telluriumsource.inject.AbstractInjector
import org.telluriumsource.annotation.Injector

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 14, 2010
 * 
 */
@Injector
class MockInjector extends AbstractInjector {
  private static final String DEFAULT = "default";

  String getCurrentSessionId() {
    return DEFAULT;
  }
}
