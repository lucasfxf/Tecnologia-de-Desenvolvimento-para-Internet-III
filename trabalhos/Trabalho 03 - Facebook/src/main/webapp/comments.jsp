
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comentários</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">

    <h1>Comentários</h1>

    <a href="${pageContext.request.contextPath}/comments/new"
       class="btn btn-primary mb-3">
        Novo Comentário
    </a>

    <a href="${pageContext.request.contextPath}/"
       class="btn btn-secondary mb-3">
        Página inicial
    </a>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Comentário</th>
                <th>ID do Post</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="comment" items="${comments_list}">
                <tr>
                    <td><c:out value="${comment.id}"/></td>
                    <td><c:out value="${comment.content}"/></td>
                    <td><c:out value="${comment.postId}"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/comments/delete"
                              method="post"
                              onsubmit="return confirm('Deseja excluir este comentário?');">
                            <input type="hidden" name="id"
                                   value="${comment.id}">
                            <button type="submit"
                                    class="btn btn-danger btn-sm">
                                Excluir
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
