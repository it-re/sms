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
							<c:forEach var="teacher" items="${teacher_set}">
								<%-- 現在の教諭と選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${teacher.id}" <c:if test="${teacher.id ==f1}">selected</c:if>>${teacher.name}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
	 				<div class="mt-2 text-warning">${errors.get("e1") }</div>
				</div>
			</form>



			<!-- 必要なパラメータを hidden で渡す -->
			<input type="hidden" name="f1" value="${f1}" />

			<c:choose>
				<c:when test="${not empty teacherSubject}">
					<table class="table table-hover">
						<tr>
							<th>科目コード</th>
							<th>科目名</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="teacher" items="${teacherSubject}">
							<tr>
								<td>${teacher.subject.cd}</td>
								<td>${teacher.subject.name}</td>
								<td><a href="SubjectUpdate.action?cd=${teacher.subject.cd}">変更</a></td>
								<td><a href="SubjectDelete.action?cd=${teacher.subject.cd}">削除</a></td>

							</tr>
						</c:forEach>
					</table>
				</c:when>
			</c:choose>
			<div>
					<%-- 画面設計書④ --%>
					<a href="SubjectList.action">戻る</a>
				</div>
		</section>
	</c:param>
</c:import>