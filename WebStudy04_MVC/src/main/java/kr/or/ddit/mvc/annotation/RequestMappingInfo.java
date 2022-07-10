package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;

/**
 * 핸들러가 처리할 수 있는 요청에 대한 정보와
 * 해당 요청을 처리할 수 있는 핸들러에 대한 정보를 가진 객체
 *
 */
public class RequestMappingInfo {
	private RequestMappingCondition mappingCondition;
	private Object handler; // 핸들러가 제각각이기 때문에 Object 타입임
	private Method handlerMethod;

	public RequestMappingInfo(RequestMappingCondition mappingCondition, Object commandHandler, Method handlerMethod) {
		this.mappingCondition = mappingCondition;
		this.handler = commandHandler;
		this.handlerMethod = handlerMethod;
	}

	@Override
	public String toString() {
		return "핸들러 정보 [commandHandler=" + handler + ", handlerMethod=" + handlerMethod + "]";
	}

	public RequestMappingCondition getMappingCondition() {
		return mappingCondition;
	}

	public Object getHandler() {
		return handler;
	}

	public Method getHandlerMethod() {
		return handlerMethod;
	}
}
