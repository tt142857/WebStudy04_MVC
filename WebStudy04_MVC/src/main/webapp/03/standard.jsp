<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/standard.jsp</title>
</head>
<body>
<h4>JSP 표준 스펙</h4>
<pre>   
	JSP(Java Server Page)?
		: 서블릿의 하위 스펙으로 서블릿의 단점을 해결하기 위한 스크립트 형태의 표현 구조.
	** JSP 토큰 구성 요소 : backend 방식 동작 코드
	1. Scriptlet : <%
	// java code 
		String sampleVariable = "샘플 변수의 값";
	%>
	2. Expression : <%="// 표현하기 위한 값이나 변수 혹은 식" %>
	3. Directive : &lt;%@ 3가지 중 하나의 지시자와 해당 지시자의 속성및 값 %&gt;
	4. Declaration : <%! //전역 변수나 전역 메소드가 선언
		String instanceVariable = "전역변수의 값";
	%>
	5. Comment : <%-- background 방식 주석 --%>
		1) HTML, JavaScript, Css (FrontEnd comment)
		2) Java(single, multiline), JSP (BackEnd comment) 
<!-- 		주석 -->
<script>
// 주석
</script>
	
	** Servlet container 의 역할
	1. 이미 등록되고 매핑 걸려있는 서블릿을 대상으로 최초의 요청이 발생하면,
	   1) 해당 서블릿의 싱글턴 객체 생성 (두번째 요청에서는 생략.)
	   2) 해당 객체의 request callback(service->doXXX) 을 호출하여 요청을 처리함.
	
	** JSP container 의 역할
	1. JSP 페이지를 대상으로 서블릿 소스를 생성
	2. 컴파일
	3. 해당 서블릿의 싱글턴 객체 생성.
	4. 해당 객체의 request callback(_JSPservice) 을 호출하여 요청을 처리함.
		
	** 참고
	언어의 종류
	1. 정적 타입 언어 , 컴파일 방식 언어 -> 소스 작성 후 한번의 번역을 통해, 전체 소스를 파싱하고 번역함.
		Java, C, C++
 		int number;
			number = 2;
	2. 동적 타입 언어, 인터프리팅 방식 언어 , 스크립트 형태 언어-> 소스 작성 후  한줄 한줄 단위로 번역과 실행이 반복됨.
		JavaScript, Python
		var number;
			number = 2;	
			number = "2";
</pre>
</body>
</html>