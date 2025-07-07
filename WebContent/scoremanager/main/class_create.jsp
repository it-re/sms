<%-- クラス登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp" >
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts">
    </c:param>

    <c:param name="content">
        <section>

            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス登録</h2>

            <form action="ClassCreateExecute.action" method="get">

                <!-- エラー表示 -->
                <c:if test="${not empty errors['1']}">
                    <div class="mt-2 text-danger">${errors['1']}</div>
                </c:if>
                <c:if test="${not empty errors['2']}">
                    <div class="mt-2 text-danger">${errors['2']}</div>
                </c:if>
               <c:if test="${not empty errors['3']}">
                    <div class="mt-2 text-danger">${errors['3']}</div>
                </c:if>

                <div>
                    <label for="class_num">クラス</label><br>
                    <input class="form-control" type="text" id="class_num" name="class_num"
                        value="${class_num}" required maxlength="3" placeholder="クラス番号を入力してください" />
                </div>
                <br>
                <c:if test="${not empty errors['4']}">
                    <div class="mt-2 text-danger">${errors['4']}</div>
                </c:if>

                <%-- 担任追加 --%>
				<div class="col-4">
					<label class="form-label" for="student-f1-select">担任</label>
					<select class="form-select" id="student-f1-select" name="teacher">
						<option value="">--------</option>
					<c:forEach var="teacher" items="${teacherlist }">
						<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
						<option value="${teacher.id }" <c:if test="${teacher.id==chargeteacher.id }">selected</c:if>>${teacher.name }</option>
					</c:forEach>
					</select>
				</div>

                <div class="mx-auto py-2">
                    <button class="btn btn-secondary" id="create-button" name="end">登録して終了</button>
                </div>
            </form>

            <a href="ClassList.action">戻る</a>
        </section>
    </c:param>
</c:import>
