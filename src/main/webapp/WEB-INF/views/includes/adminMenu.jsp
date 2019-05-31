<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ul class="admin-menu">
<!-- 
	<li class="selected">기본설정</li>
	<li><a href="">카테고리</a></li>
	<li><a href="">글작성</a></li> -->
	<c:choose>
		<c:when test='${param.menu== "main" }'>
			<li class="selected"><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/basic">기본설정</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/category">카테고리</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/write">글작성</a></li>
		</c:when>
		<c:when test='${param.menu== "category" }'>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/basic">기본설정</a></li>
			<li class="selected"><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/category">카테고리</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/write">글작성</a></li>
		</c:when>
		<c:when test='${param.menu== "write" }'>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/basic">기본설정</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/category">카테고리</a></li>
			<li class="selected"><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/write">글작성</a></li>
		</c:when>
		<c:otherwise>
			<li class="selected"><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/basic">기본설정</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/category">카테고리</a></li>
			<li><a href="${pageContext.servletContext.contextPath }/${authUser.userid}/admin/write">글작성</a></li>
		</c:otherwise>
	
	</c:choose>
</ul>