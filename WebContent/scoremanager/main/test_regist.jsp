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
					<div class="col-4">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_cd_set}">
								<%-- 現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject}" <c:if test="${subject==f3}">selected</c:if>>${subject}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label" for="student-f4-select">回数</label>
						<select class="form-select" id="student-f4-select" name="f4">
							<option value="0">--------</option>
							<c:forEach var="count" items="${count_set}">
								<%-- 現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${count}" <c:if test="${count==f4}">selected</c:if>>${count}</option>
							</c:forEach>
						</select>
					</div>






					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>


			<c:choose>
				<c:when test="${test.size()>0 }">
					<div>科目：${test}(${count})</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>氏名</th>
							<th>学生番号氏名</th>
							<th>点数</th>
						</tr>
						<c:forEach var="testRecord" items="${subject}">
							<tr>
								<td>${subject.year}</td>
								<td>${subject.class_num}</td>
								<td>${subject.student_no}</td>
								<td>${subject.student_name}</td>
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
		</section>
	</c:param>
</c:import>