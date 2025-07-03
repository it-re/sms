<%-- 科目一覧JSP --%>
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

			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			<div class="my-2 text-end px-4">
			<%-- マスター権限で新規登録などをできるかを判定 --%>
			<c:if test="${isAdmin}">
				<a href="SubjectCreate.action">新規登録</a>
			</c:if>
			</div>

				<table class="table table-hover">
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
						<th>担当教師</th>
						<c:if test="${isAdmin}">
							<th></th>
							<th></th>
						</c:if>

					</tr>

					<c:forEach var="subject" items="${subjects}">
						<tr>
							<td>${subject.cd}</td>
							<td>${subject.name}</td>
						<td>
						<c:choose>
							<c:when test="${subject.teacher.name != null}">
								${subject.teacher.name}
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
						</td>
							<c:if test="${isAdmin}">
								<td><a href="SubjectUpdate.action?cd=${subject.cd}">変更</a></td>
								<td><a href="SubjectDelete.action?cd=${subject.cd}">削除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>

				<div>
					<%-- 画面設計書④ --%>
					<a href="TeacherSubjectList.action">教師ごとの担当科目はこちら</a>
				</div>

		</section>
	</c:param>
</c:import>