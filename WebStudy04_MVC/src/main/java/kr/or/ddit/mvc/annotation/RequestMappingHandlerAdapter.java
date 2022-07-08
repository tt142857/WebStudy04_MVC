package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.resolvers.HandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttributeArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletSpecArgumentResolver;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

	private List<HandlerMethodArgumentResolver> argumentResolvers;
	
	{
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletSpecArgumentResolver());
		argumentResolvers.add(new ModelAttributeArgumentResolver());
		argumentResolvers.add(new RequestParamArgumentResolver());
	}
	
	private HandlerMethodArgumentResolver findHandlerArgumentResolver(Parameter parameter) {
		HandlerMethodArgumentResolver finded = null;
		for(HandlerMethodArgumentResolver resolver : argumentResolvers) {
			if(resolver.isSupported(parameter)) {
				finded = resolver;
				break;
			}
		}
		return finded;
	}
	
	@Override
	public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object handler = mappingInfo.getHandler();
		Method handlerMethod = mappingInfo.getHandlerMethod();
		
		int parameterCount = handlerMethod.getParameterCount();
		try {
			String viewName = null;
			
			if(parameterCount > 0) {
				Parameter[] parameters = handlerMethod.getParameters();
				Object[] parameterValues = new Object[parameters.length];
				for(int i = 0; i < parameters.length; i++) {
					Parameter singleParameter = parameters[i];
					HandlerMethodArgumentResolver resolver = findHandlerArgumentResolver(singleParameter);
					if(resolver == null) {
						throw new ServletException(String.format("%s를 처리할 수 있는 argument resolver가 없음", singleParameter));
					}
					Object singleValue = resolver.argumentResolve(singleParameter, req, resp);
					parameterValues[i] = singleValue;
				}
				viewName = (String) handlerMethod.invoke(handler, parameterValues);
			} else {
				viewName = (String) handlerMethod.invoke(handler, req, resp);
			}
			return viewName;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// 핸들러가 잘못 만들어진 경우
			throw new ServletException(e);
		}
	}

}
