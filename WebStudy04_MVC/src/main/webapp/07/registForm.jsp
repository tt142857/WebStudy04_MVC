<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Map<String,String[]> gradeMap = new LinkedHashMap<>();
	gradeMap.put("G001", new String[]{"G001", "고졸"});
	gradeMap.put("G002", new String[]{"G001", "초대졸"});
	gradeMap.put("G003", new String[]{"G001", "대졸"});
	gradeMap.put("G004", new String[]{"G001", "석사"});
	gradeMap.put("G005", new String[]{"G001", "박사"});
	
	Map<String,String[]> licenseMap = new LinkedHashMap<>();
	licenseMap.put("L001", new String[]{"L001", "정보처리산업기사"});
	licenseMap.put("L002", new String[]{"L002", "정보처리기사"});
	licenseMap.put("L003", new String[]{"L003", "정보보안산업기사"});
	licenseMap.put("L004", new String[]{"L004", "정보보안기사"});
	licenseMap.put("L005", new String[]{"L005", "SQLD"});
	licenseMap.put("L006", new String[]{"L006", "SQLP"});
	
	String message =(String) request.getAttribute("message");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
	if(StringUtils.isBlank(message)){
		%>
		<script type="text/javascript">
			alert('<%=message %>');
		</script>
		<%
	}
%>
</head>
<body>
<h4> 대덕인재 개발원 학생 등록 양식 </h4>
<!-- 학생 : 이름(필), 나이, 전화번호이름(필), 이메일, 주소이름(필), 최종학력이름(필), 학교이름(필), 학과이름(필), 졸업여부, 사진, 성별이름(필), 자격증 -->
<form action="<%=request.getContextPath() %>/07/dditProcess.do" method="post" enctype="application/x-www-form-urlencoded">
	<ul>
		<li>
			이름 : <input type="text" name="name" value="${student.name }" />
		</li>
		<li>
			나이 : <input type="number" name="age" value="${student.age }"/>
		</li>
		<li>
			전화번호 : <input type="text" name="hp"  value="${student.hp }"/>
		</li>
		<li>
			이메일 : <input type="email" name="email"  value="${student.email }"/>
		</li>
		<li>
			주소 : <input type="text" name="address" value="${student.address }"/>
		</li>
		<li>
			최종학력 : 
				<select name="grade">
					<option value>학력</option>
					<%
						for(Entry<String,String[]> entry:gradeMap.entrySet()){
							String gradeCode = entry.getKey();
							String gradeText = entry.getValue()[1];
							%>
							<option value='<%=gradeCode %>'><%=gradeText %></option>
							<%
						}
					%>
				</select>
		</li>
		<li>
			학교 : <input type="text" name="school" />
		</li>
		<li>
			학과 : <input type="text" name="subject" />
		</li>
		<li>
			졸업여부 : <input type="radio" name="gdt" value="졸업"/>여
			<input type="radio" name="gdt" value="예정" />부
 		</li>
 		<li>
 			성별 : <input type="radio" name="gender" value="F"/>여
			<input type="radio" name="gender" value="M" />남
 		</li>
 		<li>
 			자격증 : 
 				<select name="licenses" multiple size="10">
 					<%
						for(Entry<String,String[]> entry:licenseMap.entrySet()){
							String licCode = entry.getKey();
							String licText = entry.getValue()[1];
							%>
							<option value='<%=licCode %>'><%=licText %></option>
							<%
						}
					%>
 				</select>			
  		</li>
  		<li>
  			사진 : <input type="file" name="photo" />
  		</li>
  		<li>
  			<input type="submit" value="등록" />
  		</li>
	</ul>
</form>
</body>
</html>





