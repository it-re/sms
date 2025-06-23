<%-- 成績管理JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

			<form action="TestRegist.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-md-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_cd_set}">
								<%-- 現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject.cd}" <c:if test="${subject.cd ==f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-2">

						<label class="form-label">回数</label>
						<select class="form-select" id="student-f4-select" name="f4">
							<option value="0">--------</option>
							<option value="1" <c:if test="${1==f4}">selected</c:if>>1</option>
							<option value="2" <c:if test="${2==f4}">selected</c:if>>2</option>
						</select>

					</div>




					<div class="col-md-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>

			<form action="TestRegistExecute.action" method="get">

			<!-- 必要なパラメータを hidden で渡す -->
			<input type="hidden" name="f1" value="${f1}" />
			<input type="hidden" name="f2" value="${f2}" />
			<input type="hidden" name="f3" value="${f3}" />
			<input type="hidden" name="f4" value="${f4}" />

			<c:choose>
				<c:when test="${test.size()>0 }">
					<div>科目：${subject_name }(${test_no}回)</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>氏名</th>
							<th>学生番号</th>
							<th>点数</th>
						</tr>
						<c:forEach var="test" items="${test}">
							<tr>
								<td>${test.student.entYear}</td>
								<td>${test.student.class_num}</td>
								<td>${test.student.name}</td>
								<td>${test.student.no}</td>
								<td>
								<input type="text" id="point_${test.student.no}" name="point_${test.student.no}" value="${test.point}">
								</td>
							</tr>
						</c:forEach>
					</table>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">登録して終了</button>
					</div>
				</c:when>
				<c:otherwise>
					<div class="mt-2 text-warning">${errors.get("e1") }></div>
				</c:otherwise>
			</c:choose>
			</form>
		</section>
	</c:param>
</c:import>