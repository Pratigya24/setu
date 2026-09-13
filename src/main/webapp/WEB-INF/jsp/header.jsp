<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SETU - Bridging Donors & NGOs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar__logo">SETU</a>
    <div class="navbar__links">
        <a href="${pageContext.request.contextPath}/">Home</a>

        <c:choose>
            <c:when test="${sessionScope.role == 'DONOR'}">
                <a href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a>
                <span class="role-badge">Donor</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
            </c:when>
            <c:when test="${sessionScope.role == 'NGO'}">
                <a href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
                <span class="role-badge">NGO</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
            </c:when>
            <c:when test="${sessionScope.role == 'VOLUNTEER'}">
                <a href="${pageContext.request.contextPath}/volunteer/dashboard">Dashboard</a>
                <span class="role-badge">Volunteer</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
            </c:when>
            <c:when test="${sessionScope.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <span class="role-badge">Admin</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>