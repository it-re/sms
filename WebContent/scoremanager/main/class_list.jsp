<%-- クラス一覧JSP --%>
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

			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>

				<table class="table table-hover">
					<tr>
						<th>クラスコード</th>
						<th></th>
					</tr>

					<c:forEach var="item" items="${classlist}">
						<tr>
							<td>${item}</td>
							<td><a href="ClassUpdate.action?cd=${item}">変更</a></td>
							</tr>
					</c:forEach>
				</table>

		</section>
	</c:param>
</c:import>