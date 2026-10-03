<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <jsp:include page="donor-sidebar.jsp">
        <jsp:param name="activePage" value="homes" />
    </jsp:include>

    <div class="dashboard-content">
        <h1>Verified Charitable Homes</h1>
        <p class="subtitle">Review the information verified by the SETU admin team before donating or booking an occasion.</p>

        <c:forEach var="ngo" items="${ngoList}">
            <div class="item-card" style="align-items:flex-start;">
                <div class="item-card__info">
                    <h3>${ngo.name} <span class="verified-tag yes">&#10003; Verified</span></h3>
                    <c:if test="${not empty ngo.description}">
                        <p>${ngo.description}</p>
                    </c:if>
                    <div class="item-card__meta">
                        <c:if test="${not empty ngo.registrationNumber}">
                            <span class="pill pill-low">Registration No: ${ngo.registrationNumber}</span>
                        </c:if>
                        <c:if test="${not empty ngo.capacity}">
                            <span class="pill pill-low">Capacity: ${ngo.capacity}</span>
                        </c:if>
                    </div>
                    <p style="margin-top:0.75rem;">
                        <strong>Address:</strong> ${ngo.fullAddress}<br>
                        <strong>Phone:</strong> ${ngo.phone}<br>
                        <strong>Email:</strong> ${ngo.email}
                    </p>
                    <div style="display:flex; gap:0.7rem; flex-wrap:wrap; margin-top:0.75rem;">
                        <c:if test="${not empty ngo.homePhotoPath}">
                            <a href="${pageContext.request.contextPath}/donor/view-document?path=${ngo.homePhotoPath}"
                               target="_blank" class="btn btn-outline">View Home Photo</a>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/donor/book-occasion?ngoId=${ngo.id}"
                           class="btn btn-primary">&#127881; Book an Occasion</a>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty ngoList}">
            <div class="empty-state">No verified charitable homes are available yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />
