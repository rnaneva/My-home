package bg.softuni.myhome.exception;

public abstract class ServiceException extends RuntimeException{


    protected String methodName;
    protected Object object;
    protected String message;


    public ServiceException(String methodName, Object object) {
        super(methodName + " throws for " + object);
        this.methodName = methodName;
        this.object = object;

    }



}
