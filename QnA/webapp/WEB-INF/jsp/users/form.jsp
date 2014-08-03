<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>

<%@ include file="/WEB-INF/jsp/commons/_header.jspf"%>

</head>
<body>
	<%@ include file="/WEB-INF/jsp/commons/_top.jspf"%>

	<div class="container">
		<div class="row">
			<div class="span12">

				<section id="typography">
					<div class="page-header">
						<h1>회원가입</h1>
					</div>

					<form:form modelAttribute="user" cssClass="form-horizontal"
						action="/users/signup" method="post">
						<div class="control-group">
							<label class="control-label" for="userId">사용자 아이디</label>
							<div class="controls">
								<form:input path="userId" />
								<form:errors path="userId" cssClass="error" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="password">비밀번호</label>
							<div class="controls">
								<form:password path="password" />
								<form:errors path="password" cssClass="error" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="name">이름</label>
							<div class="controls">
								<form:input path="name" />
								<form:errors path="name" cssClass="error" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="email">이메일</label>
							<div class="controls">
								<form:input path="email" />
								<form:errors path="email" cssClass="error" />
							</div>
						</div>
						
						<div class="control-group">
							<div class="controls">
								<button type="submit" class="btn btn-primary">회원가입</button>
							</div>
						</div>
					</form:form>

				</section>

			</div>
		</div>
	</div>
</body>
</html>
