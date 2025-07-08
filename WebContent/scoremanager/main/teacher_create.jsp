<%-- 教師情報登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts">
	<%-- パスワード表示の仕方を変更 --%>
	<script>
    document.addEventListener("DOMContentLoaded", function() {
        const checkbox = document.getElementById("showPasswordReg");
        const pwInput = document.getElementById("pw");
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

			<%-- ① --%>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教師情報登録</h2>
			<form action="TeacherCreateExecute.action" method="get">


				<%-- エラーメッセージ① --%>
				<div class="mt-2 text-warning">${errors.get("1")}</div>

				<%-- ② --%>
				<div>
					<label for="id">教師ID</label>
					<%-- ③ --%>
					<input class="form-control" type="text" id="id" name="id" value="${id}" required maxlength="10" placeholder="IDを入力してください" />
				</div>
				<br>

				<div>
					<label for="name">教師名</label>
					<%-- ⑤ --%>
					<input class="form-control" type="text" id="name" name="name" value="${name}" required maxlength="10" placeholder="氏名を入力してください" />
				</div>
				<br>

				<%-- ④ --%>
				<div>
					<label for="pw">パスワード</label>
					<%-- ⑤ --%>
					<input class="form-control" type="password" id="pw" name="pw" value="" required maxlength="30" placeholder="パスワードを入力してください" />
					<%-- パスワード表示部分 --%>
					<input type="checkbox" id="showPasswordReg">
    				<label for="showPasswordReg">パスワードを表示</label>

				</div>

				<div class="mx-auto py-2">
					<label for="is_admin">管理者権限</label>
					<input type="checkbox" id="is_admin" name="is_admin" <c:if test="${is_admin}">checked</c:if>/>
				</div>

				<%-- ⑥ --%>
				<div class="mx-auto py-2">
					<button class="btn btn-secondary" id="create-button" name="end">登録</button>
				</div>

			</form>

			<%-- ⑦ --%>
			<a href="TeacherList.action">戻る</a>

		</section>
	</c:param>
</c:import>