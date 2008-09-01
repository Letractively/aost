package org.tellurium.locator

abstract class AbstractLocateStrategy{

    def abstract boolean canHandle(locator)

    def abstract String locate(locator)
}