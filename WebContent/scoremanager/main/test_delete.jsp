<%-- 科目削除JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績情報削除</h2>

			<%-- 画面設計書の②～④のフォーム --%>
			<form action="TestDeleteExecute.action" method="post">

				<c:choose>
				<c:when test="${test.no == 0}">
					<p>
						<label>
							${test.student.name}さんは${subject.name}のテストを未受験です
						</label>
					</p>
				</c:when>
				<c:otherwise>
					<p>
						<label>
							「${test.student.name}さんの${subject.name}(${test.no}回目)の成績」を削除してもよろしいですか
						</label>
					</p>

					<input type="hidden" name="entYear" value="${test.student.entYear}">
					<input type="hidden" name="classNum" value="${test.student.classNum}">
					<input type="hidden" name="student_no" value="${test.student.no}">
					<input type="hidden" name="subject_name" value="${subject.name}">
					<input type="hidden" name="test_no" value="${test.no}">

			 		<button class="btn btn-danger" id="delete" name="delete" value="delete">削除</button>
					</c:otherwise>
			</c:choose>

					<br>
					<br>
					<br>
					<br>

					<%-- 画面設計書④ --%>
					<a href="TestRegist.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>