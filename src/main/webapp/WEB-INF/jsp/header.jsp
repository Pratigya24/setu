<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SETU - Bridging Donors &amp; Charitable Homes</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/setu-logo.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700;800&family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar__logo">
<img src="${pageContext.request.contextPath}/images/setu-logo.png" alt="SETU" width="157" height="157">        
    </a>
    <div class="navbar__links">
        <a href="${pageContext.request.contextPath}/">Home</a>

        <c:choose>
            <c:when test="${sessionScope.role == 'DONOR'}">
                <a href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/donor/browse-ngos">Browse Homes</a>
                <span class="role-badge">Donor</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
            </c:when>
            <c:when test="${sessionScope.role == 'NGO'}">
                <a href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
                <span class="role-badge">Charitable Home</span>
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