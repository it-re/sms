<%-- 教師一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts">

	</c:param>

	<c:param name="content">
		<section class="me-4">

			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教師一覧</h2>
			<div class="my-2 text-end px-4">
			<%-- マスター権限で新規登録などをできるかを判定 --%>
			<c:if test="${isAdmin}">
				<a href="TeacherCreate.action">新規登録</a>
			</c:if>
			</div>

				<table class="table table-hover">
					<tr>
						<th>ID</th>
						<th>教師名</th>
						<c:if test="${isAdmin}">
							<th></th>
							<th></th>
						</c:if>

					</tr>

					<c:forEach var="teacher" items="${teacherlist}">
						<tr>
							<td>${teacher.id}</td>
							<td>${teacher.name}</td>

							<c:if test="${isAdmin}">
								<td><a href="TeacherUpdate.action?id=${teacher.id}">変更</a></td>
								<td>
									<c:if test="${login_user!=teacher.id}">
									<a href="TeacherDelete.action?id=${teacher.id}">削除</a>
								</c:if>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>

		</section>
	</c:param>
</c:import>