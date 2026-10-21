
package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Post;
import model.dao.PostDAO;
import model.Comment;
import model.ModelException;
import model.dao.CommentDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {
	    "/comments", "/comments/new", "/comments/save", "/comments/delete"
	})

public class CommentsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        try {
        	if (action.equals("/comments/new")) {
        	    PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);
        	    req.setAttribute("posts_list", postDAO.listAll());
        	    req.getRequestDispatcher("/comments_form.jsp").forward(req, resp);
        	

            } else if (action.equals("/comments")) {
                CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);
                req.setAttribute("comments_list", dao.listAll());
                req.getRequestDispatcher("/comments.jsp").forward(req, resp);

            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (ModelException e) {
            throw new ServletException("Erro ao acessar comentários.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        

        String action = req.getServletPath();

        if (action.equals("/comments/delete")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));

                Comment comment = new Comment();
                comment.setId(id);

                CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);
                dao.delete(comment);

                resp.sendRedirect(req.getContextPath() + "/comments");
            } catch (ModelException | NumberFormatException e) {
                throw new ServletException("Erro ao excluir comentário.", e);
            }
            return;
        }


        try {
            String content = req.getParameter("content");
            String postId = req.getParameter("post_id");

            if (content == null || content.isBlank()
                    || postId == null || postId.isBlank()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Preencha o comentário e selecione um post.");
                return;
            }

            Comment comment = new Comment();
            comment.setContent(content.trim());
            comment.setPostId(Integer.parseInt(postId));

            CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);

            if (dao.save(comment)) {
                resp.sendRedirect(req.getContextPath() + "/comments");
            } else {
                throw new ServletException("O comentário não foi salvo.");
            }

        } catch (ModelException | NumberFormatException e) {
            throw new ServletException("Erro ao salvar comentário.", e);
        }
    }
}
