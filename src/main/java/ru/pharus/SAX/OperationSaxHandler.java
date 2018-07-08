package SAX;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OperationSaxHandler extends DefaultHandler {

    private List<Expression> expressions = new ArrayList<>();
    private Deque<Operation> stack = new LinkedList<>();

    private ElementName currentElement;
    private Expression expression;

    private enum ElementName {
        SIMPLECALCULATOR, EXPRESSIONS, EXPRESSION, OPERATION, ARG
    }

    private enum OperationType {
        SUB, SUM, MUL, DIV
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        switch (ElementName.valueOf(localName.toUpperCase())) {
            case EXPRESSION:
                currentElement = ElementName.EXPRESSION;
                expression = new Expression();
                break;
            case OPERATION:
                currentElement = ElementName.OPERATION;
                stack.push(createOperation(attributes));
                break;
            case ARG:
                currentElement = ElementName.ARG;
                break;

            default: {
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (ElementName.valueOf(localName.toUpperCase())) {
            case OPERATION:
                Operation closeOperation = stack.pop();
                if (stack.isEmpty()){
                    expression.setOperation(closeOperation);
                }else {
                    stack.peek().setArg(closeOperation);
                }
                break;

            case EXPRESSION:
                expressions.add(expression);
                System.out.println("ExpressionResult: " + expression.getExpressionResult());
                break;

            case ARG:
                currentElement = ElementName.OPERATION;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentElement == ElementName.ARG) {
            stack.peek().setArg(new Integer(new String(ch, start, length)));
        }
    }

    private Operation createOperation(Attributes attributes) {
        switch (OperationType.valueOf(attributes.getValue("OperationType"))) {
            case SUM:
                return new Operation((a, b) -> a.doOperation() + b.doOperation());
            case SUB:
                return new Operation((a, b) -> a.doOperation() - b.doOperation());
            case DIV:
                return new Operation((a, b) -> a.doOperation() / b.doOperation());
            case MUL:
                return new Operation((a, b) -> a.doOperation() * b.doOperation());
        }

        throw new UnsupportedOperationException();
    }
}

class Expression {
    private Operation operation;

    Double getExpressionResult() {
        return operation.doOperation();
    }

    void setOperation(Operation operation) {
        this.operation = operation;
    }
}


class Operation {
    private BiFunction<Operation, Operation, Double> function;
    private Operation arg1;
    private Operation arg2;

    Operation(BiFunction<Operation, Operation, Double> function){
        this.function = function;
    }

    Double doOperation(){
        return function.apply(arg1,arg2);
    }

    void setArg(Operation arg) {
        if (arg1 == null) {
            this.arg1 = arg;
        } else {
            arg2 = arg;
        }
    }

    void setArg(Integer number) {
        if (arg1 == null) {
            this.arg1 = new Operation((a, b) -> (double) number);
        } else {
            arg2 = new Operation((a, b) -> (double) number);
        }
    }

}