<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>NGO Profile</h1>
        <p class="subtitle">Update your organization details</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>

        <div class="form-wrapper" style="margin: 0;">
            <form action="${pageContext.request.contextPath}/ngo/profile" method="post">
                <div class="form-group">
                    <label for="name">Organization Name</label>
                    <input type="text" id="name" name="name" value="${ngoProfile.name}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email (cannot be changed)</label>
                    <input type="email" id="email" value="${ngoProfile.email}" disabled
                           style="background-color:#f1f5f9; cursor:not-allowed;">
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${ngoProfile.phone}" required>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${ngoProfile.address}">
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="3">${ngoProfile.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="registrationNumber">Registration Number (cannot be changed)</label>
                    <input type="text" id="registrationNumber" value="${ngoProfile.registrationNumber}" disabled
                           style="background-color:#f1f5f9; cursor:not-allowed;">
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>

        <div class="panel" style="margin-top:1.5rem;">
            <p class="panel__title">Verification Documents</p>
            <p style="font-size:0.85rem; color:#64748b; margin-bottom:1rem;">
                These were submitted at registration and reviewed by the admin team.
            </p>
            <div style="display:flex; gap:0.7rem; flex-wrap:wrap;">
                <c:if test="${not empty ngoProfile.verificationDocumentPath}">
                    <a href="${pageContext.request.contextPath}/ngo/view-document?path=${ngoProfile.verificationDocumentPath}" target="_blank" class="btn btn-outline">View Verification Document</a>
                </c:if>
                <c:if test="${not empty ngoProfile.homePhotoPath}">
                    <a href="${pageContext.request.contextPath}/ngo/view-document?path=${ngoProfile.homePhotoPath}" target="_blank" class="btn btn-outline">View Home Photo</a>
                </c:if>
                <c:if test="${empty ngoProfile.verificationDocumentPath && empty ngoProfile.homePhotoPath}">
                    <span style="font-size:0.85rem; color:#94a3b8;">No documents on file.</span>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
