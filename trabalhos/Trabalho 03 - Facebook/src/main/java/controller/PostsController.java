
package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {
    "/posts",
    "/posts/new",
    "/posts/save",
    "/posts/edit",
    "/posts/delete"
})
public class PostsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {

                case "/posts":
                    loadPosts(req);
                    req.getRequestDispatcher("/posts.jsp").forward(req, resp);
                    break;

                case "/posts/new":
                    loadUsers(req);
                    req.setAttribute("post", new Post());
                    req.getRequestDispatcher("/posts_form.jsp").forward(req, resp);
                    break;

                case "/posts/edit":
                    loadPost(req);
                    loadUsers(req);
                    req.getRequestDispatcher("/posts_form.jsp").forward(req, resp);
                    break;

                case "/posts/delete":
                    deletePost(req);
                    resp.sendRedirect(req.getContextPath() + "/posts");
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }

        } catch (ModelException e) {
            e.printStackTrace();
            throw new ServletException("Erro ao acessar os posts.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            saveOrUpdatePost(req);
            resp.sendRedirect(req.getContextPath() + "/posts");

        } catch (ModelException e) {
            e.printStackTrace();
            throw new ServletException("Erro ao salvar o post.", e);
        }
    }

    private void loadPosts(HttpServletRequest req) throws ModelException {

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        req.setAttribute("posts_list", dao.listAll());
    }

    private void loadUsers(HttpServletRequest req) throws ModelException {

        UserDAO dao = DAOFactory.createDAO(UserDAO.class);

        req.setAttribute("users_list", dao.listAll());
    }

    private void loadPost(HttpServletRequest req) throws ModelException {

        String idStr = req.getParameter("postId");

        if (idStr == null || idStr.isBlank()) {
            throw new IllegalArgumentException("ID do post não informado.");
        }

        int id = Integer.parseInt(idStr);

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        Post post = dao.findById(id);

        if (post == null) {
            throw new IllegalArgumentException("Post não encontrado: " + id);
        }

        req.setAttribute("post", post);
    }

    private void saveOrUpdatePost(HttpServletRequest req) throws ModelException {

        String idStr = req.getParameter("post_id");
        String content = req.getParameter("post_content");
        String userIdStr = req.getParameter("post_user");

        if (content == null || content.isBlank()
                || userIdStr == null || userIdStr.isBlank()) {

            throw new IllegalArgumentException(
                    "Preencha o conteúdo e selecione um usuário.");
        }

        int userId = Integer.parseInt(userIdStr);

        UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
        User user = userDAO.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado: " + userId);
        }

        boolean updating = idStr != null
                && !idStr.isBlank()
                && !idStr.equals("0");
        
        Post post = updating
                ? new Post(Integer.parseInt(idStr))
                : new Post();

        post.setContent(content.trim());
        post.setUser(user);

        System.out.println("=== TENTANDO SALVAR POST ===");
        System.out.println("Conteúdo: " + post.getContent());
        System.out.println("Usuário ID: " + post.getUser().getId());
        System.out.println("Operação: " + (updating ? "EDIÇÃO" : "CADASTRO"));

        PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);

        boolean success;

        if (updating) {
            success = postDAO.update(post);
        } else {
            success = postDAO.save(post);
        }

        System.out.println("Resultado da operação: " + success);

        if (!success) {
            throw new ModelException(
                    "Não foi possível salvar o post. Verifique o console do Eclipse.");
        }
    }

    private void deletePost(HttpServletRequest req) throws ModelException {

        String idStr = req.getParameter("postId");

        if (idStr == null || idStr.isBlank()) {
            throw new IllegalArgumentException("ID do post não informado.");
        }

        int id = Integer.parseInt(idStr);

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        Post post = dao.findById(id);

        if (post != null) {
            boolean deleted = dao.delete(post);
            System.out.println("Post excluído: " + deleted);
        }
    }
}
