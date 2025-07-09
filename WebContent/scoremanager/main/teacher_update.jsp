<%-- 教師情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts">
	<%-- パスワード表示の仕方を変更 --%>
	<script>
    document.addEventListener("DOMContentLoaded", function() {
        const checkbox = document.getElementById("showPasswordUpdate");
        const pwInput = document.getElementById("password");
        if (checkbox) {
            checkbox.addEventListener("change", function() {
                pwInput.type = this.checked ? "text" : "password";
            });
        }
    });
	</script>
	</c:param>

	<c:param name="content">
		<section>
			<%-- 見出し --%>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教師情報変更</h2>
			<form action="TeacherUpdateExecute.action" method="get">

				<%-- 教師ID　変更不可，表示のみ --%>
				<div class="mx-auto py-2">
					<label class="mx-auto py-2" for="id">教師ID</label><br>
					<input class="border border-0 ps-3" type="text" id="id" value="${id}" name="id" readonly />
				</div>

				<%-- 教師名　変更可能，既存の名前を表示 --%>
				<div class="mx-auto py-2">
					<label for="name">教師名</label><br>
					<input class="form-control" type="text" id="name" name="name" value="${name}" required maxlength="30" />
				</div>

				<%-- パスワード　入力必須 --%>
				<div class="mx-auto py-2">
					<label for="password">パスワード</label><br>
					<input class="form-control" type="password" id="password" name="password" value="${password}" name="password" required />
					<%-- パスワード表示部分 --%>
					<input type="checkbox" id="showPasswordUpdate">
    				<label for="showPasswordUpdate">パスワードを表示</label>

				</div>

				<%-- 管理者権限　チェックボックス
					ログインユーザーと変更ページのIDが同じ教師だった場合，管理者権限チェックボックスは表示しない --%>
				<c:if test="${LoginId ne id}">
				<div class="mx-auto py-2">
					<label for="is_attend">管理者権限</label>
					<input type="checkbox" id="is_admin" name="is_admin" <c:if test="${is_admin}">checked</c:if> />
				</div>
				</c:if>

				<%-- 管理者権限を表示しない場合，is_adminに入れるデータ --%>
				<c:if test="${LoginId == id}">
					<input type="hidden" name="is_admin" value="is_admin" />
				</c:if>

				<%-- 変更ボタン --%>
				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="login" value="変更"/>
				</div>
			</form>

			<%-- 戻るボタン，教師リストへ偏移する --%>
			<a href="TeacherList.action">戻る</a>
		</section>
	</c:param>
</c:import>