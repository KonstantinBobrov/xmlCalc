package ru.Prototype

import ru.pharus.Constants

class derwentParser {

    def parser = new XmlSlurper()
    def sourceXML = parser.parse(Constants.sourceFile)


}
