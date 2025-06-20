<%-- 学生一覧JSP --%>
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
				<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
				<div class="row border mx-3 mb-3 py-2 ">
					<form action="TestListSubjectExecute.action" method="get">
						<div class="row align-items-center rounded pb-2 mb-3 border-bottom " id="filter">
							<div class="col-2 text-center ">科目情報</div>
							<div class="col-2">
								<label class="form-label" for="f1">入学年度</label>
								<select class="form-select" id="f1" name="f1">
									<option value="0">--------</option>
									<c:forEach var="year" items="${ent_year_set }">
										<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
										<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-2">
								<label class="form-label" for="f2">クラス</label>
								<select class="form-select" id="f2" name="f2">
									<option value="0">--------</option>
									<c:forEach var="num" items="${class_num_set }">
										<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
										<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-4">
								<label class="form-label " for="f3" >科目</label>
								<select class="form-select" id="f3" name="f3">
									<option value="0">--------</option>
									<c:forEach var="subject" items="${subjects}">
										<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
										<option value="${subject.cd}" <c:if test="${subject.cd == f3 }">selected</c:if>>${subject.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-2 mt-3 text-center">
								<button class="btn btn-secondary" id="filter-button">検索</button>
							</div>

						</div>
					</form>

					<br>

					<form action="TestListStudentExecute.action" method="get">
						<div class="row align-items-center rounded" id="filter">
								<div class="col-2 text-center">学生情報</div>
								<div class="col-4">
									<label class="form-label" for="f4">学生番号</label>
									<input class="form-control" type="text" id="f4" name="f4">
								</div>
								<div class="col-2 text-center">
									<button class="btn btn-secondary" id="filter-button">検索</button>
								</div>
						</div>
					</form>
				</div>

			<c:choose>
				<c:when test="${list.size()>0 }">
					<div>科目：${subject.name}</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>

						</tr>
						<c:forEach var="testListSubject" items="${list }">
							<tr>
								<td>${testlistsubject.entYear }</td>
								<td>${testlistsubject.classNum }</td>
								<td>${testlistsubject.studentNo }</td>
								<td>${testlistsubject.studentName }</td>
								<td>${testlistsubject.points.1 }</td>
								<td>${testlistsubject.points.2}</td>


							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>成績情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>