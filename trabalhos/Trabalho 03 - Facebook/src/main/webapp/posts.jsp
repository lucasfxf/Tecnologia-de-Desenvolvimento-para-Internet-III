
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Posts - Facebook CRUD</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>Posts</h2>
            <a href="${pageContext.request.contextPath}/posts/new"
               class="btn btn-success">
                <i class="bi bi-plus-circle"></i> Novo post
            </a>
        </div>

        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Conteúdo</th>
                    <th scope="col">Data</th>
                    <th scope="col">Usuário</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="post" items="${posts_list}">
                    <tr>
                        <th scope="row"><c:out value="${post.id}"/></th>
                        <td><c:out value="${post.content}"/></td>
                        <td><c:out value="${post.postDate}"/></td>
                        <td><c:out value="${post.user.name}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/posts/edit?postId=${post.id}"
                               class="btn btn-primary btn-sm">
                                Editar
                            </a>

                            <a href="${pageContext.request.contextPath}/posts/delete?postId=${post.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Deseja realmente excluir este post?');">
                                Excluir
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/index.jsp"
           class="btn btn-secondary">
            Voltar ao início
        </a>
    </div>

    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
