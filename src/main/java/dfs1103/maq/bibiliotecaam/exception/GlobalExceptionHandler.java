package dfs1103.maq.bibiliotecaam.exception;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Si el Valid falla, vamos a tirarle esto.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errores = new LinkedHashMap<>();

        //Todavia me sigo sin acostumbrar que BindingResult no se escribe directamente ahora.
        ex.getBindingResult().getFieldErrors().forEach(erro -> errores.
                        put(erro.getField(), erro.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errores);
    }

    //Si algo no es encontrado, tiramos este error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRunTimeError(RuntimeException ex){
        Map<String, String> error = new LinkedHashMap<>();
        error.put("ERROR:", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
