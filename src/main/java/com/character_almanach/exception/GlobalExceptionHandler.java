package com.character_almanach.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.character_almanach.exception.character.CharacterDuplicateClassesException;
import com.character_almanach.exception.character.CharacterNotFoundException;
import com.character_almanach.exception.character.ClassRemovalNotAllowedException;
import com.character_almanach.exception.character.ReducingCharacterLevelException;
import com.character_almanach.exception.character.SubclassChangeNotAllowedException;
import com.character_almanach.exception.token.ExpiredRefreshTokenException;
import com.character_almanach.exception.token.InvalidRefreshTokenException;
import com.character_almanach.exception.user.UserAlreadyExistsException;
import com.character_almanach.exception.user.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Character Exceptions

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(CharacterNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CharacterDuplicateClassesException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateClasses(CharacterDuplicateClassesException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReducingCharacterLevelException.class)
    public ResponseEntity<Map<String, String>> handleReducingCharacterLevel(ReducingCharacterLevelException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubclassChangeNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleSubclassChangeNotAllowed(SubclassChangeNotAllowedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClassRemovalNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleClassRemovalNotAllowed(ClassRemovalNotAllowedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //User Exceptions

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    } 

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //Token Exceptions
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRefreshToken(InvalidRefreshTokenException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<Map<String, String>> handleExpiredRefreshToken(ExpiredRefreshTokenException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //THIS HANDLER IS ADDED TO HANDLE VALIDATION ERRORS

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    // Handle other exceptions globally
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Error", "Internal server error: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
