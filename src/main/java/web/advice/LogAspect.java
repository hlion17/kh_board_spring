package web.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // 스프링 빈으로 등록하기 - component-scan 설정 필요
@Aspect // AspectJ 관리를 받도록 설정하기
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	// 포인트 컷 설정 메소드 - advice가 적용될 위치 지정
//	@Pointcut("execution(* web.service.impl.*ServiceImpl.*(..))")
	@Pointcut("bean(*ServiceImpl)")
	private static void logPointCut() {}
	
	// 어드바이스 코드 작성 - 포인트 컷을 함께 지정한다.
	@Before("logPointCut()")
	public void logBefore(JoinPoint joinPoint) {

		// 어드바이스 코드가 실행되는 메소드에 대한 정보 객체
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		
		// 실행된 메소드의 이름을 로그로 출력
		logger.info("#### Before - Method : {}", signature.toString());
	}
}
