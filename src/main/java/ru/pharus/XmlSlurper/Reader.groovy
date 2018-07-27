package ru.pharus.XmlSlurper

import groovy.util.slurpersupport.GPathResult
import ru.pharus.Constants
import ru.pharus.XSDValidator

import static ru.pharus.Constants.XSDFile
import static ru.pharus.Constants.sourceFile

class Reader {
    static main(args){
        def parser = new XmlSlurper()
        def simpleCalculator = parser.parse(Constants.sourceFile)

        if (!XSDValidator.validate(sourceFile, XSDFile)) {
            println("XML File validation failed")
        }
//        simpleCalculator.expressions.each {
//            expressions ->
//                println "expression index: ${expressions.@indexNum}"
//        }

        List expressions = []

        for (operation in simpleCalculator?.expressions?.expression?.operation){
            def result = getOperationResult(operation)
            expressions.add(result)
            println "${operation.@OperationType} blabla"
        }
    }

    static Double getOperationResult(def operation) {
        def arg1 = operation.arg[0]
        def arg2 = operation.arg[1]

        switch (operation.@OperationType){
            case 'SUM' : return arg1 + arg2
            case 'SUB' : return arg1 - arg2
            case 'DIV' : return arg1 - arg2
            case 'MUL' : return arg1 - arg2
            default: return 0
        }
    }

    static

    class Operation{
        String type
        Double arg1
        Double arg2
    }
}
