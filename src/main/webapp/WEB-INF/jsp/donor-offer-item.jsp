<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/offer-item">Offer an Item</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Offer an Item</h1>
        <p class="subtitle">Have something useful to give? List it here and any NGO can claim it.</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>

        <div class="form-wrapper" style="margin: 0;">
            <form action="${pageContext.request.contextPath}/donor/offer-item" method="post">
                <div class="form-group">
                    <label for="title">Item Name</label>
                    <input type="text" id="title" name="title" placeholder="e.g. Old Blankets, School Books, Rice bags" required>
                </div>
                <div class="form-group">
                    <label for="category">Category</label>
                    <select id="category" name="categoryId" required>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.id}">${cat.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity</label>
                    <input type="number" id="quantity" name="quantity" placeholder="10" min="1" required>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="3" placeholder="Condition, pickup details, etc."></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Post Item</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />