package org.telluriumsource.crosscut
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 27, 2009
 * 
 */

public @Singleton(lazy = true) class SimpleLogger implements Logger {

  def List listeners = new ArrayList()

  public void log(String message) {
    listeners.each {appender ->
      appender.listen(message)
    }
  }

  public void addAppender(Appender appender) {
    listeners.add(appender);
  }

}