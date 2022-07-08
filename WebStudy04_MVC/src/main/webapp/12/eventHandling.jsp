<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/eventHandling.jsp</title>
</head>
<body>
	<h4>JSP 스펙에서 서버사이드 이벤트 처리 방법</h4>
	<pre>
		// 새로운 요청이 발생하면(request의 lifecycle 생성 - 1, 2단계), 클라이언트의 ip를 콘솔에 출력
		1.target 선별 : context(web application)
		2. 이벤트 종류 : request, session, servletContext
			- lifecycle : 생성, 소멸의 이벤트
			- scope : 새로운 attribute의 add(저장), remove(삭제), replace(대체)
		3. 이벤트 핸들러 : listener의 구현체
		4. 이벤트 핸들러를 target에 대해 listening할 수 있도록 부착함 : web.xml -> listener
	</pre>
	<button id="sampleBtn">샘플버튼</button>
	<!-- 버튼을 누르는 건 클라이언트 -->
	<!-- 버튼을 클릭했을 때, alert메시지 띄우기 -->
	<script type="text/javascript">
		// 1. target 선별
		let sampleBtn = document.getElementById("sampleBtn");
		// 2. 이벤트 종류, 3. 이벤트 핸들러, 4. 이벤트 핸들러를 target에 대해 listening할 수 있도록 부착함
		let eventHandler = function(event) {
			// 모든 이벤트는 자신을 가리키는 target을 갖고 있음
			alert(event.target.innerHTML);
		}
		sampleBtn.onclick = eventHandler;
	</script>
</body>
</html>