package com.hb.neobank.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hb.neobank.config.UserSessionContext;
import com.hb.neobank.icici.common.IciciBankApiException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseDTO> accessDeniedException(HttpServletRequest request, Exception ex) {
		logger.error("Don't have access!!  ******************************************** :: " + ex.getMessage());

		ResponseDTO response = new ResponseDTO(400, "AccessDenied",
				"Client: " + UserSessionContext.getCurrentTenant().getName()
						+ " attempted to access the protected URL: " + request.getRequestURI());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(IciciBankApiException.class)
	public ResponseEntity<ResponseDTO> iciciBankApiException(HttpServletRequest request, Exception ex) {
		logger.error("Icici Bank Api Exception!!  ******************************************** :: " + ex.getMessage());

		ResponseDTO response = new ResponseDTO(400, "ICICI", "Something went wrong!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(UserNotValidException.class)
	public ResponseEntity<ResponseDTO> userNotValidException(HttpServletRequest request, Exception ex) {
		logger.error("Credentials are not valid!!  ******************************************** :: " + ex.getMessage());

		ResponseDTO response = new ResponseDTO(400, "ICICI", "Client not valid!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(ReqLimitExceedException.class)
	public ResponseEntity<ResponseDTO> reqLimitExceedException(HttpServletRequest request, Exception ex) {
		logger.error(
				"Req Limit Exceed Exception!!  ******************************************** :: " + ex.getMessage());

		ResponseDTO response = new ResponseDTO(400, "ICICI", "Req Limit Exceeded!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
