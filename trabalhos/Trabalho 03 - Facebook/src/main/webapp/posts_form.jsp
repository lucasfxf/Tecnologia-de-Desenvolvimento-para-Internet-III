
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Posts - Facebook CRUD</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>

<body>
<div class="container mt-4">
    <c:choose>
    <c:when test="${post.id == 0}">
        <h1>Cadastrar Post</h1>
    </c:when>
    <c:otherwise>
        <h1>Editar Post</h1>
    </c:otherwise>
</c:choose>

    <form action="${pageContext.request.contextPath}/posts/save"
          method="post">

        <input type="hidden" name="post_id" value="${post.id}">

        <div class="mb-3">
            <label for="post_content" class="form-label">Conteúdo</label>
            <textarea id="post_content" name="post_content"
                      class="form-control" rows="4"
                      required><c:out value="${post.content}"/></textarea>
        </div>

        <div class="mb-3">
            <label for="post_user" class="form-label">Usuário</label>
            <select id="post_user" name="post_user" class="form-select" required>
                <option value="">Selecione um usuário</option>
                <c:forEach var="user" items="${users_list}">
                    <option value="${user.id}"
                        ${post.user.id == user.id ? 'selected' : ''}>
                        <c:out value="${user.name}"/> -
                        <c:out value="${user.email}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Salvar</button>
        <a href="${pageContext.request.contextPath}/posts"
           class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>
