package bg.softuni.myhome.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UncaughtException implements Runnable {

    Logger logger = LoggerFactory.getLogger(UncaughtException.class);


    @Override
    public void run() {
        logger.warn("Uncaught exception");
        throw new RuntimeException();
    }
}

