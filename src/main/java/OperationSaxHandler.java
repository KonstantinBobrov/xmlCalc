import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OperationSaxHandler extends DefaultHandler {

    private List<BiFunction<Operation, Operation, Double>> expressions = new ArrayList<>();

    private BiFunction<Operation, Operation, Double> expression;

    private ElementName currentOperation;

    private enum ElementName {
        SIMPLECALCULATOR, EXPRESSIONS, EXPRESSION, OPERATION, ARG
    }

    private enum OperationType {
        SUB, SUM, MUL, DIV
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {

        System.out.printf("Start element: localName %s\n", localName);

        switch (ElementName.valueOf(localName.toUpperCase())) {
            case EXPRESSION:
                currentOperation = ElementName.EXPRESSION;
                break;
            case OPERATION:
                currentOperation = ElementName.OPERATION;
                //currentOperation = createOperation(attributes);
                break;
            case ARG:
                currentOperation = ElementName.ARG;
                //currentArg = 0;
                break;

            default: {
            }
        }
    }

    private Operation createOperation(Attributes attributes) {
        switch (OperationType.valueOf(attributes.getValue("OperationType"))) {
            case SUM:
                return new Operation((a, b) -> a.getOperationResult() + b.getOperationResult());
            case SUB:
                return new Operation((a, b) -> a.getOperationResult() - b.getOperationResult());
            case DIV:
                return new Operation((a, b) -> a.getOperationResult() / b.getOperationResult());
            case MUL:
                return new Operation((a, b) -> a.getOperationResult() * b.getOperationResult());
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (ElementName.valueOf(localName.toUpperCase())) {
            case OPERATION:

                currentExpression.setOperation(currentOperation);

                System.out.println("!!!!!! Set operation to exp");
                break;
            case EXPRESSION:
                expressions.add(currentExpression);
                System.out.println("!!!!!! Result: " + currentExpression.getExpressionResult());
                break;
            case ARG:
                currentOperation.setArg(currentArg);
                System.out.println("!!!!!! Set arg " + currentArg);
                currentArg = null;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentArg != null) {
            currentArg = new Integer(new String(ch, start, length));
        }
    }
}

class Expression {
    private Operation operation;

    Double getExpressionResult() {
        return operation.getOperationResult();
    }

    void setOperation(Operation operation) {
        this.operation = operation;
    }
}

interface Operation<T extends Operation, D extends Double> extends BiFunction {
    //private BiFunction<Operation, Operation, Double> function;
    //private Operation arg1;
    //private Operation arg2;

    //Operation(BiFunction<Operation, Operation, Double> function) {
    //    this.function = function;
    //}



    Double getOperationResult() {
        return function.apply(arg1, arg2);
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
            this.arg1 = new Operation((a,b) -> (double) number);
        } else {
            arg2 = new Operation((a,b) -> (double) number);
        }
    }
}