<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="site.title"/></title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/setu-logo.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700;800&family=Inter:wght@400;500;600;700&family=Noto+Sans+Devanagari:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%-- Current page path, so the language switch keeps the user on the same page --%>
<c:set var="curPath" value="${requestScope['jakarta.servlet.forward.request_uri']}" />
<c:if test="${empty curPath}">
    <c:set var="curPath" value="${pageContext.request.contextPath}/" />
</c:if>
<c:set var="curLang" value="${pageContext.response.locale.language}" />

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar__logo">
        <img src="${pageContext.request.contextPath}/images/setu-logo.png" alt="SETU" width="157" height="157">
    </a>
    <div class="navbar__links">
        <a href="${pageContext.request.contextPath}/"><spring:message code="nav.home"/></a>

        <c:choose>
            <c:when test="${sessionScope.role == 'DONOR'}">
                <a href="${pageContext.request.contextPath}/donor/dashboard"><spring:message code="nav.dashboard"/></a>
                <a href="${pageContext.request.contextPath}/donor/browse-ngos"><spring:message code="nav.browseRequirements"/></a>
                <a href="${pageContext.request.contextPath}/donor/charitable-homes"><spring:message code="nav.verifiedHomes"/></a>
                <span class="role-badge"><spring:message code="role.donor"/></span>
            </c:when>
            <c:when test="${sessionScope.role == 'NGO'}">
                <a href="${pageContext.request.contextPath}/ngo/dashboard"><spring:message code="nav.dashboard"/></a>
                <a href="${pageContext.request.contextPath}/ngo/post-requirement"><spring:message code="nav.post"/></a>
                <a href="${pageContext.request.contextPath}/ngo/bookings"><spring:message code="nav.bookings"/></a>
                <span class="role-badge"><spring:message code="role.ngo"/></span>
            </c:when>
            <c:when test="${sessionScope.role == 'VOLUNTEER'}">
                <a href="${pageContext.request.contextPath}/volunteer/dashboard"><spring:message code="nav.dashboard"/></a>
                <span class="role-badge"><spring:message code="role.volunteer"/></span>
            </c:when>
            <c:when test="${sessionScope.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard"><spring:message code="nav.dashboard"/></a>
                <span class="role-badge"><spring:message code="role.admin"/></span>
            </c:when>
        </c:choose>

        <%-- Language switcher --%>
        <div class="lang-switch">
            <a href="${curPath}?lang=en" class="${curLang == 'en' ? 'active' : ''}">EN</a>
            <a href="${curPath}?lang=hi" class="${curLang == 'hi' ? 'active' : ''}">हिंदी</a>
        </div>

        <c:choose>
            <c:when test="${not empty sessionScope.role}">
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline"><spring:message code="nav.logout"/></a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline"><spring:message code="nav.login"/></a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary"><spring:message code="nav.register"/></a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>