package com.RusGruz.JobAggregator.Exception;

public class ApiRequestException extends RuntimeException
{
    public ApiRequestException(String message)
    {
        super(message);
    }
}
