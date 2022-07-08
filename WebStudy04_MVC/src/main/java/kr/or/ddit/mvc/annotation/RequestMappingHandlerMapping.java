package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMappingHandlerMapping implements HandlerMapping {

	private Map<RequestMappingCondition, RequestMappingInfo> handlerMap;
	
	public RequestMappingHandlerMapping(String...basePackages) {
		
		handlerMap = new LinkedHashMap<>();
		
		// Controller의 어노테이션을 가진 클래스들을 찾는다.
		Map<Class<?>, Controller> handlerClasses = ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages);
		handlerClasses.keySet().stream().forEach((handlerClz) -> {
			try {
				Object handler = handlerClz.newInstance(); // 객체 생성
				// 찾은 클래스의 각각을 RequestMapping 어노테이션을 갖고, String을 반환하고 파라미터를 req와 resp를 가지는 메소드를 찾는다.
				Map<Method, RequestMapping> handlerMethodsMap = ReflectionUtils.getMethodsWithAnnotationAtClass(
																		handlerClz, 
																		RequestMapping.class,
																		String.class);
				
				
				handlerMethodsMap.entrySet().stream().forEach((entry) -> {
					Method handlerMethod = entry.getKey();
					RequestMapping annotation = entry.getValue();
					
					RequestMappingCondition mappingCondition = new RequestMappingCondition(annotation.value(), annotation.method());
					RequestMappingInfo mappingInfo = new RequestMappingInfo(mappingCondition, handler, handlerMethod);
					
					handlerMap.put(mappingCondition, mappingInfo);
					
					log.info("{} : {}", mappingCondition, mappingInfo);
				}); // forEach end 
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} // try end
			
		}); // forEach end
	}

	@Override
	public RequestMappingInfo findCommandHandler(HttpServletRequest req) {
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		uri = uri.split(";")[0];
		
		RequestMethod method = RequestMethod.valueOf(req.getMethod().toUpperCase());
		RequestMappingCondition mappingCondition = new RequestMappingCondition(uri, method);
		RequestMappingInfo mappingInfo = handlerMap.get(mappingCondition);
		log.info("현재 요청 : {} \n 요청을 처리할 핸들러 : {}", mappingCondition, mappingInfo);
		return mappingInfo;
	}
}
