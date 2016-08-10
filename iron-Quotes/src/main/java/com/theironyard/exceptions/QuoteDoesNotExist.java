package com.theironyard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by EddyJ on 8/10/16.
 */
@ResponseStatus (value = HttpStatus.NOT_FOUND, reason = "Quote does not exist")
public class QuoteDoesNotExist extends RuntimeException {
}
