package com.example.domain.exceptions

class CustomException(override var cause: Throwable = Throwable(), var exceptionType: ExceptionType = ExceptionType.DEFAULT, var customMessage: String = "")
    : RuntimeException(cause)