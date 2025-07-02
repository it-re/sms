<%-- 担当教師ごとの科目検索JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">担当教師ごとの科目検索画面</h2>

			<form action="TeacherSubjectList.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-md-4">
						<label class="form-label" for="student-f1-select">教師名</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="teacher_set" items="${teacher_set}">
								<%-- 現在のsubjectと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${teacher.id}" <c:if test="${teacher.name ==f1}">selected</c:if>>${teacher.name}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
	 				<div class="mt-2 text-warning">${errors.get("e1") }</div>
				</div>
			</form>

			<form action="TeacherSubjectListExecute.action" method="get">

			<!-- 必要なパラメータを hidden で渡す -->
			<input type="hidden" name="f1" value="${f1}" />

			<c:choose>
				<c:when test="${teacherSubject > 0}">
					<div>科目：${teacher.name}</div>
					<table class="table table-hover">
						<tr>
							<th>科目</th>
						</tr>
						<c:forEach var="teacherSubject" items="${teacherSubject}">
							<tr>
								<td>${teacher.name}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
			</c:choose>
			</form>
		</section>
	</c:param>
</c:import>