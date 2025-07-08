<%-- 教師削除完了JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教師情報削除エラー</h2>

			<%-- 画面設計書の②のフォーム --%>
				<p class="text-center">
				  <label class="form-control" style="background-color:#FF0000; color: #FFFFFF; padding: 5px 16px; display: inline-block; border-radius: 0;">
				    この教師は担当クラスが存在しているため削除出来ません
				  </label>
				</p>

			<br>
			<br>
			<br>
			<br>

			<%-- 画面設計書③ --%>
			<a href="TeacherList.action">教師一覧</a>
		</section>
	</c:param>
</c:import>