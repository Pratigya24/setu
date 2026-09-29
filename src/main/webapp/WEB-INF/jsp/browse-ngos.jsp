<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
	<a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse Requirements</a>
	<a class="sidebar__link" href="${pageContext.request.contextPath}/donor/charitable-homes">Verified Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">
	<h1>Browse Requirements</h1>
        <p class="subtitle">Review each request together with the charitable home requesting support.</p>

        <c:forEach var="request" items="${requestList}">
            <div class="item-card" style="align-items:flex-start;">
                <div class="item-card__info">
                    <h3>${request.title}</h3>
                    <p>${request.description}</p>
                    <div class="item-card__meta">
                        <span class="pill pill-open">${request.status}</span>
                        <c:choose>
                            <c:when test="${request.urgency == 'High'}">
                                <span class="pill pill-urgent">High Urgency</span>
                            </c:when>
                            <c:when test="${request.urgency == 'Medium'}">
                                <span class="pill pill-medium">Medium Urgency</span>
                            </c:when>
                            <c:otherwise>
                                <span class="pill pill-low">Low Urgency</span>
                            </c:otherwise>
                        </c:choose>
                        <span class="pill pill-low">Needed: ${request.quantity}</span>
                    </div>

                    <c:if test="${not empty request.ngo}">
                        <div class="panel" style="margin-top:1rem; padding:1rem;">
                            <p class="panel__title">
                                ${request.ngo.name}
                                <c:if test="${request.ngo.approved}">
                                    <span class="verified-tag yes">&#10003; Verified</span>
                                </c:if>
                            </p>
                            <c:if test="${not empty request.ngo.description}">
                                <p>${request.ngo.description}</p>
                            </c:if>
                            <p style="margin-top:0.6rem;">
                                <strong>Address:</strong> ${request.ngo.fullAddress}<br>
                                <strong>Phone:</strong> ${request.ngo.phone}<br>
                                <strong>Email:</strong> ${request.ngo.email}
                            </p>
                            <div class="item-card__meta" style="margin-top:0.6rem;">
                                <c:if test="${not empty request.ngo.registrationNumber}">
                                    <span class="pill pill-low">Registration No: ${request.ngo.registrationNumber}</span>
                                </c:if>
                                <c:if test="${not empty request.ngo.capacity}">
                                    <span class="pill pill-low">Capacity: ${request.ngo.capacity}</span>
                                </c:if>
                            </div>
                            <div style="display:flex; gap:0.7rem; flex-wrap:wrap; margin-top:0.7rem;">
                                <c:if test="${request.ngo.approved && not empty request.ngo.verificationDocumentPath}">
                                    <a href="${pageContext.request.contextPath}/donor/view-document?path=${request.ngo.verificationDocumentPath}"
                                       target="_blank" class="btn btn-outline">View Verified Document</a>
                                </c:if>
                                <c:if test="${request.ngo.approved && not empty request.ngo.homePhotoPath}">
                                    <a href="${pageContext.request.contextPath}/donor/view-document?path=${request.ngo.homePhotoPath}"
                                       target="_blank" class="btn btn-outline">View Home Photo</a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/donor/charitable-homes"
                                   class="btn btn-outline">View Full Profile</a>
                            </div>
                        </div>
                    </c:if>
                </div>
                <a href="${pageContext.request.contextPath}/donor/donate?requestId=${request.id}" class="btn btn-primary">Donate</a>
            </div>
        </c:forEach>

        <c:if test="${empty requestList}">
            <div class="empty-state">No open requirements right now. Check back soon!</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />