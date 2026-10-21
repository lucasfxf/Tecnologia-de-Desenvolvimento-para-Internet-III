
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Novo Comentário</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h1>Cadastrar Comentário</h1>

    <form action="${pageContext.request.contextPath}/comments/save"
          method="post">

        <div class="mb-3">
            <label for="content" class="form-label">Comentário</label>
            <textarea id="content" name="content" class="form-control"
                      rows="3" maxlength="500" required></textarea>
        </div>

        <div class="mb-3">
            <label for="post_id" class="form-label">Post relacionado</label>
            <select id="post_id" name="post_id" class="form-select" required>
                <option value="">Selecione um post</option>
                <c:forEach var="post" items="${posts_list}">
                    <option value="${post.id}">
                        <c:out value="${post.content}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Salvar</button>
        <a href="${pageContext.request.contextPath}/comments"
           class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>
