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

				<c:choose>
					<c:when test="${empty user.userId}">
						<c:set var="title" value="회원가입" />
						<c:set var="method" value="post" />
						<c:set var="requestURL" value="/users/signup" />
					</c:when>
					<c:otherwise>
						<c:set var="title" value="개인정보수정" />
						<c:set var="method" value="put" />
						<c:set var="requestURL" value="/users/update" />
					</c:otherwise>
				</c:choose>
				
				<section id="typography">
					<div class="page-header">
						<h1>${title}</h1>
					</div>

					<form:form modelAttribute="user" cssClass="form-horizontal"
						action="${requestURL}" method="${method}">
						<div class="control-group">
							<label class="control-label" for="userId">사용자 아이디</label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty user.userId}">
										<form:input path="userId" />
										<form:errors path="userId" cssClass="error" />
									</c:when>
									<c:otherwise>
										<input tpye="text" value="${user.userId}" readonly />
										<form:hidden path="userId" />
									</c:otherwise>
								</c:choose>
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
								<button type="submit" class="btn btn-primary">${title}</button>
							</div>
						</div>
					</form:form>

				</section>

			</div>
		</div>
	</div>
</body>
</html>
