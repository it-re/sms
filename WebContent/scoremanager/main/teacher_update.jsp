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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教師情報変更</h2>
			<form action="TeacherUpdateExecute.action" method="get">

				<div class="mx-auto py-2">
					<label class="mx-auto py-2" for="id">教師ID</label><br>
					<input class="border border-0 ps-3" type="text" id="id" value="${id}" name="id" readonly />
				</div>
				<div class="mx-auto py-2">
					<label for="name">教師名</label><br>
					<input class="form-control" type="text" id="name" name="name" value="${name}" required maxlength="30" />
				</div>
				<div class="mx-auto py-2">
					<label for="password">パスワード</label><br>
					<input class="form-control" type="password" id="password" name="password" value="${password}" name="password" required />
					<%-- パスワード表示部分 --%>
					<input type="checkbox" id="showPasswordUpdate">
    				<label for="showPasswordUpdate">パスワードを表示</label>

				</div>
				<div class="mx-auto py-2">
					<label for="is_attend">登録者権限</label>
					<input type="checkbox" id="is_attend" name="is_admin" <c:if test="${is_admin}">checked</c:if> />
				</div>
				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="login" value="変更"/>
				</div>
			</form>
			<a href="TeacherList.action">戻る</a>
		</section>
	</c:param>
</c:import>