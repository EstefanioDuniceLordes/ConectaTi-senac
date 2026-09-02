package br.com.senac.conectati.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}