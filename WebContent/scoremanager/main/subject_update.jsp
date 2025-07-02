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


				<div>
<%-- 画面設計書② --%>
<label for="cd">科目コード</label><br>

					<%-- 画面設計書③ --%>
<input class="form-control" style="border: none;" type="text" id="cd"
					 name="cd" value="${cd}" readonly />
</div>

				<%-- エラーメッセージ① --%>
<div class="mt-2 text-warning">${errors.get("1")}</div>
<br>

				<%-- 画面設計書④ --%>
<div>
<label for="name">科目名</label><br>

					<%-- 画面設計書⑤ --%>
<input class="form-control" type="text" id="name" name="name"
					 value="${name}" required maxlength="20"/>
</div>
<br>



<%-- 担当教師追加 --%>
<div class="col-4">
	<label class="form-label" for="student-f1-select">担当教師</label>
	<select class="form-select" id="student-f1-select" name="teacher">
			<option value="">--------</option>
		<c:forEach var="teacher" items="${teacherlist }">
			<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
			<option value="${teacher.id }" <c:if test="${teacher.id==chargeteacher.id }">selected</c:if>>${teacher.name }</option>
		</c:forEach>
	</select>
</div>



<%-- 画面設計書⑥ --%>
<div class="mx-auto py-2">
<button class="btn btn-primary" id="change" name="change">変更</button>
</div>
</form>


			<%-- 画面設計書⑦ --%>
<a href="SubjectList.action">戻る</a>

</section>
</c:param>
</c:import>