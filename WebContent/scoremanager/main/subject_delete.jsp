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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<%-- 画面設計書の②～④のフォーム --%>
			<form action="SubjectUpdateExecute.action" method="get">

				<div>
					<%-- 画面設計書② --%>
					<p>
						<label>
							「${name}(${cd})」を削除してもよろしいですか
						</label>
					</p>

					<%-- 画面設計書③ --%>
					<button class="btn btn-danger" id="delete" name="delete" value="delete">削除</button>

					<br>
					<br>
					<br>
					<br>

					<%-- 画面設計書④ --%>
					<a href="subject_list.jsp">戻る</a>



				</div>
			</form>
		</section>
	</c:param>
</c:import>