package org.telluriumsource.mock

import org.telluriumsource.inject.Injector
import org.telluriumsource.annotation.Provider
import org.telluriumsource.annotation.Inject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 14, 2010
 * 
 */
@Inject
@Provider
class MockInjector extends Injector {
  private static final String DEFAULT = "default";

  String getCurrentSessionId() {
    return DEFAULT;
  }
}
