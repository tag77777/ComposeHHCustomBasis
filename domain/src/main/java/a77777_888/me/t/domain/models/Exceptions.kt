package a77777_888.me.t.domain.models

import java.lang.reflect.Field

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class EmptyFieldException(val field: Field) : AppException()

class InvalidCredentialsException(cause: Exception) : AppException(cause = cause)

class ConnectionException(cause: Throwable) : AppException(cause = cause)

open class BackendException(val code: Int, message: String) : AppException(message)

class ParseBackendResponseException(cause: Throwable) : AppException(cause)

//internal inline fun <T> wrapBackendExceptions(block: () -> T): T {
//    try {
//        return block.invoke()
//    } catch (e: BackendException) {
//        if (e.code == 401) {
//            throw e
//        } else {
//            throw e
//        }
//    }
//}