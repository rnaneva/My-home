package bg.softuni.myhome.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UncaughtException implements Runnable {

    Logger logger = LoggerFactory.getLogger(UncaughtException.class);


    @Override
    public void run() {
        logger.warn("Uncaught exception");
        throw new RuntimeException();
    }
}

