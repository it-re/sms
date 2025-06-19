<%-- 科目更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- coreタグを使用できるようにする --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jspを利用 --%>
<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<%-- 画面設計書の① --%>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<%-- 画面設計書の②～⑦のフォーム --%>
			<form action="SubjectUpdateExecute.action" method="get">

				<%-- エラーメッセージ① --%>
				<div class="mt-2 text-warning">${errors.get("1")}</div>

				<div>
					<%-- 画面設計書② --%>
					<label for="cd">科目コード</label><br>

					<%-- 画面設計書③ --%>
					<input class="form-control" style="border: none;" type="text" id="cd"
					 name="cd" value="${cd}" readonly />
				</div>

				<%-- 画面設計書④ --%>
				<div>
					<label for="name">科目名</label><br>

					<%-- 画面設計書⑤ --%>
					<input class="form-control" type="text" id="name" name="name"
					 value="${name}" required maxlength="20"/>
				</div>

				<div class="mx-auto py-2">
					<%-- 画面設計書⑥ --%>
					<button class="btn btn-primary" id="change" name="change">変更</button>
				</div>
			</form>


			<%-- 画面設計書⑦ --%>
			<a href="SubjectList.action">戻る</a>

		</section>
	</c:param>
</c:import>