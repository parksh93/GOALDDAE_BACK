package com.goalddae.exception;

import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class PaymentGlobalException {
        /**
         * 게시글작성시 모든값이 제대로 입력되지 않았을때 발생하는 예외
         */
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<String> dataIntegrityViolationError(){
            String message = "값이 제대로 입력되지 않았습니다.(DataIntegrityViolationException)";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        /**
         * 실제 결제금액과 DB의 결제 금액이 다를때 발생하는 예외
         */
        @ExceptionHandler(VerifyIamPortException.class)
        public ResponseEntity<String> verifyIamportException(){
            return new ResponseEntity<>("실제 결제금액과 서버에서 결제금액이 다릅니다.",HttpStatus.BAD_REQUEST);
        }

        /**
         * 환불가능 금액과 아임포트 서버에서 조회한 결제했던 금액이 일치하지 않을때 발생하는 예외
//         * @return
//         */
//        @ExceptionHandler(RefundAmountIsDifferent.class)
//        public ResponseEntity<String> RefundAmountIsDifferent(){
//            return new ResponseEntity<String>("환불가능 금액과 결제했던 금액이 일치하지 않습니다.",HttpStatus.BAD_REQUEST);
//        }

        /**
         * 결제관련 예외
         */
        @ExceptionHandler(IamportResponseException.class)
        public ResponseEntity<String> IamPortResponseException(){
            return new ResponseEntity<>("결제관련해서 에러가 발생",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /**
         * 그외 모든 에러는 서버에러로~
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleArgumentException(Exception e) {
            String message = "서버 에러 입니다.(Exception)";
            return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
