package com.db.iPayments.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logError(String message, Throwable error) {
        logger.error(message, error);
    }
}
