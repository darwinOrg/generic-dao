package org.darwin.shardingDataSource.exception;

public class DataSourceRoutingException extends RuntimeException {

    public DataSourceRoutingException() {
        super();
    }

    public DataSourceRoutingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceRoutingException(String message) {
        super(message);
    }

    public DataSourceRoutingException(Throwable cause) {
        super(cause);
    }

}
