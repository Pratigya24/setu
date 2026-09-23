<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Post a Requirement</h1>
        <p class="subtitle">Let donors know exactly what your charitable home needs right now</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <div class="form-wrapper" style="margin:0; max-width:620px;">
            <form action="${pageContext.request.contextPath}/ngo/post-requirement" method="post">

                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" placeholder="e.g. Winter Blankets Needed" required>
                </div>

                <div class="form-group">
                    <label for="category">Category</label>
                    <select id="category" name="categoryId" required>
                        <option value="" disabled selected>Select a category</option>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.id}">
                                <c:choose>
                                    <c:when test="${cat.name == 'Fruits & Vegetables'}">&#127822; </c:when>
                                    <c:when test="${cat.name == 'Food & Groceries'}">&#127834; </c:when>
                                    <c:when test="${cat.name == 'Medicine'}">&#128138; </c:when>
                                    <c:when test="${cat.name == 'Clothes'}">&#128085; </c:when>
                                    <c:when test="${cat.name == 'Books'}">&#128218; </c:when>
                                    <c:when test="${cat.name == 'Toys'}">&#129528; </c:when>
                                    <c:when test="${cat.name == 'Furniture'}">&#128715; </c:when>
                                    <c:when test="${cat.name == 'Electronics'}">&#128268; </c:when>
                                    <c:otherwise>&#128230; </c:otherwise>
                                </c:choose>${cat.name}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${empty categoryList}">
                        <p style="font-size:0.78rem; color:#b45309; margin-top:0.4rem;">
                            No categories found. Ask your admin to add donation categories.
                        </p>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="3" placeholder="Describe what's needed and why" required></textarea>
                </div>

                <div class="form-group">
                    <label for="quantity">Quantity Needed</label>
                    <input type="number" id="quantity" name="quantity" placeholder="50" min="1" required>
                </div>

                <div class="form-group">
                    <label>Urgency</label>
                    <div class="role-select" style="grid-template-columns:repeat(3,1fr);">
                        <input type="radio" id="urgencyLow" name="urgency" value="Low" checked>
                        <label for="urgencyLow">
                            <span class="role-select__icon">&#128994;</span>
                            Low
                        </label>

                        <input type="radio" id="urgencyMedium" name="urgency" value="Medium">
                        <label for="urgencyMedium">
                            <span class="role-select__icon">&#128992;</span>
                            Medium
                        </label>

                        <input type="radio" id="urgencyHigh" name="urgency" value="High">
                        <label for="urgencyHigh">
                            <span class="role-select__icon">&#128308;</span>
                            High
                        </label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Post Requirement</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
