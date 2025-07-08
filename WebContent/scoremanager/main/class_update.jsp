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
			<form action="ClassUpdateExecute.action" method="get">

				<%-- 画面設計書④ --%>
				<div>
					<label for="classNum">クラス番号</label><br>

					<%-- 画面設計書⑤ --%>
					<input class="form-control" type="text" id="classNum" name="newClassNum"
										 value=
										 <c:choose>
										 	<c:when test="${newClassNum != null}">
										 		"${newClassNum.class_num}"
										 	</c:when>
										 	<c:otherwise>
										 		"${oldClassNum.class_num}"
										 	</c:otherwise>
										 </c:choose>
										 required maxlength="3"/>
				</div>

				<input type="hidden" name="oldClassNum" value="${oldClassNum.class_num}">

				<%-- エラーメッセージ① --%>
				<div class="mt-2 text-warning">${errors.get("1")}</div>
				<br>


                <div class="mt-2 text-warning">${errors['2']}</div>


                <%-- 担任選択 --%>
				<div class="col-4">
					<label class="form-label" for="student-f1-select">担任</label>
					<select class="form-select" id="student-f1-select" name="teacher">
						<c:forEach var="teacher" items="${teacherList }">
							<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
							<option value="${teacher.id }"
								<c:choose>
								 	<c:when test="${newClassNum != null && teacher.id == newClassNum.teacher.id}">
								 		selected
								 	</c:when>
								 	<c:when test="${newClassNum == null && teacher.id == oldClassNum.teacher.id}">
								 		selected
								 	</c:when>
								</c:choose>>
									${teacher.name }
							</option>
						</c:forEach>
					</select>
				</div>

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