<%-- 成績参照検索JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<%-- ① --%>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>


			<div class="row border mx-3 mb-3 py-2 rounded">

				<form action="TestListSubjectExecute.action" method="get">
					<div class="row align-items-center  pb-2 mb-3 border-bottom " id="filter">
						<%-- ② --%>
						<div class="col-2 text-center ">科目情報</div>
						<div class="col-2">
							<%-- ③ --%>
							<label class="form-label" for="f1">入学年度</label>
							<%-- ⑥ --%>
							<select class="form-select" id="f1" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set }">
									<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
									<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-2">
							<%-- ④ --%>
							<label class="form-label" for="f2">クラス</label>
							<%-- ⑦ --%>
							<select class="form-select" id="f2" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set }">
									<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
									<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-4">
							<%-- ⑤ --%>
							<label class="form-label " for="f3" >科目</label>
							<%-- ⑧ --%>
							<select class="form-select" id="f3" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subjects}">
									<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
									<option value="${subject.cd}" <c:if test="${subject.cd == f3 }">selected</c:if>>${subject.name}</option>
								</c:forEach>
							</select>
						</div>

						<%-- ⑮ --%>
						<input type="hidden" name="f" value="st">

						<%-- ⑨ --%>
						<div class="col-2 mt-3 text-center">
							<button class="btn btn-secondary" id="filter-button">検索</button>
						</div>

						<%-- 科目別成績一覧エラー① --%>
						<div class="mt-2 text-warning">${errors.get("1")}</div>
					</div>
				</form>

				<%-- ⑯ --%>
				<form action="TestListStudentExecute.action" method="get">
					<div class="row align-items-center" id="filter">
							<%-- ⑩ --%>
							<div class="col-2 text-center">学生情報</div>
							<div class="col-4">
								<%-- ⑪ --%>
								<label class="form-label" for="f4">学生番号</label>
								<%-- ⑫ --%>
								<input class="form-control" type="text" id="f4" name="f4" placeholder="学生番号を入力してください" maxlength="20" value="${f4}" required>
							</div>

							<%-- ⑮ --%>
							<input type="hidden" name="f" value="sj">

							<%-- ⑬ --%>
							<div class="col-2 text-center">
								<button class="btn btn-secondary" id="filter-button">検索</button>
							</div>
					</div>
				</form>
			</div>

			<p>
				<label class="mt-2 text-info">科目情報を選択または学生情報を入力してください。</label>
			</p>
		</section>
	</c:param>
</c:import>