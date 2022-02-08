package dev.appkr;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.spring.webflux.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, SecurityAdviceTrait {

}
