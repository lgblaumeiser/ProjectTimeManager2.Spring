// SPDX-FileCopyrightText: 2022 Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
// SPDX-License-Identifier: MIT
package de.lgblaumeiser.ptm.spring.backend.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalTime
import kotlin.Exception

@ControllerAdvice
class ResponseExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleBadRequest(ex: Exception): ResponseEntity<ErrorDetails> = ResponseEntity(errorDetails(ex), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(IllegalAccessException::class)
    fun handleForbidden(ex: IllegalAccessException) : ResponseEntity<ErrorDetails> = ResponseEntity(errorDetails(ex), HttpStatus.FORBIDDEN)

    @ExceptionHandler(Throwable::class)
    fun handleGeneralException(ex: Throwable): ResponseEntity<ErrorDetails> = ResponseEntity(errorDetails(ex), HttpStatus.INTERNAL_SERVER_ERROR)

    private fun errorDetails(ex: Throwable) = ErrorDetails(LocalTime.now(), ex.message ?: "")
}

data class ErrorDetails(val time: LocalTime, val message: String)