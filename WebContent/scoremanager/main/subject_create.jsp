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
		<section>

			<%-- ① --%>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			<form action="SubjectCreateExecute.action" method="get">


				<%-- エラーメッセージ① --%>
				<div class="mt-2 text-warning">${errors.get("1")}</div>

				<%-- エラーメッセージ② --%>
				<div class="mt-2 text-warning">${errors.get("2")}</div>

				<%-- ② --%>
				<div>
					<label for="cd">科目コード</label>
					<%-- ③ --%>
					<input class="form-control" type="text" id="cd" name="cd" value="${cd}" required maxlength="3" placeholder="科目コードを入力してください" />
				</div>

				<%-- ④ --%>
				<div>
					<label for="name">科目名</label>
					<%-- ⑤ --%>
					<input class="form-control" type="text" id="name" name="name" value="${name}" required maxlength="20" placeholder="科目名を入力してください" />
				</div>
				<br>

				<%-- 担当教師追加 --%>
				<div class="col-4">
					<label class="form-label" for="student-f1-select">担当教師</label>
					<select class="form-select" id="student-f1-select" name="teacher">
						<option value="">--------</option>
					<c:forEach var="teacher" items="${teacherlist }">
						<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
						<option value="${teacher.id }" >${teacher.name }</option>
					</c:forEach>
					</select>
				</div>

				<input type="hidden" value="${chargeteacher.id}" name="oldteacher">


				<%-- ⑥ --%>
				<div class="mx-auto py-2">
					<button class="btn btn-secondary" id="create-button" name="end">登録</button>
				</div>

			</form>

			<%-- ⑦ --%>
			<a href="SubjectList.action">戻る</a>

		</section>
	</c:param>
</c:import>