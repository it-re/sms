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
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>

			<%-- 画面設計書の②～⑦のフォーム --%>
<form action="ClassListUpdateExecute.action" method="get">




				<%-- 画面設計書④ --%>
<div>
<label for="name">クラス名</label><br>

					<%-- 画面設計書⑤ --%>
<input class="form-control" type="text" id="num" name="num"
					 value="${num}" required maxlength="3"/>
</div>

<%-- エラーメッセージ① --%>
<div class="mt-2 text-warning">${errors.get("1")}</div>
<br>

<div class="mx-auto py-2">
<%-- 画面設計書⑥ --%>
<button class="btn btn-primary" id="change" name="change">変更</button>
</div>
</form>


			<%-- 画面設計書⑦ --%>
<a href="ClassList.action">戻る</a>

		</section>
</c:param>
</c:import>