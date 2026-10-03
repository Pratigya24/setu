<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <jsp:include page="ngo-sidebar.jsp">
    <jsp:param name="activePage" value="profile" />
</jsp:include>

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

                <p class="auth-divider">Detailed Address</p>
                <div class="form-group">
                    <label for="addressLine1">Address Line 1</label>
                    <input type="text" id="addressLine1" name="addressLine1" value="${ngoProfile.addressLine1}" placeholder="House / building number, street">
                </div>
                <div class="form-group">
                    <label for="addressLine2">Address Line 2</label>
                    <input type="text" id="addressLine2" name="addressLine2" value="${ngoProfile.addressLine2}" placeholder="Area, locality, ward">
                </div>
                <div class="form-group">
                    <label for="landmark">Landmark</label>
                    <input type="text" id="landmark" name="landmark" value="${ngoProfile.landmark}">
                </div>
                <div class="field-row">
                    <div class="form-group">
                        <label for="city">City / Town</label>
                        <input type="text" id="city" name="city" value="${ngoProfile.city}">
                    </div>
                    <div class="form-group">
                        <label for="state">State / Province</label>
                        <input type="text" id="state" name="state" value="${ngoProfile.state}">
                    </div>
                </div>
                <div class="field-row">
                    <div class="form-group">
                        <label for="postalCode">Postal / PIN Code</label>
                        <input type="text" id="postalCode" name="postalCode" value="${ngoProfile.postalCode}">
                    </div>
                    <div class="form-group">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country" value="${ngoProfile.country}">
                    </div>
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
