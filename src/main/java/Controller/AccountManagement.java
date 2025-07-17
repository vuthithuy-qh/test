package Controller;

import dao.account.AccountDAO;
import dao.account.AccountDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.Account;
import util.JPAUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import service.account.AccountService;

public class AccountManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDAO dao = new AccountDAOImpl();
        
        String action = Optional.ofNullable(request.getParameter("action")).orElse("");

        switch (action) {
            case "new":
                request.getRequestDispatcher("createAccount.jsp").forward(request, response);
                return;
            case "edit": {
                int id = Integer.parseInt(request.getParameter("id"));
                Account account = dao.findById(id);
                request.setAttribute("account", account);
                request.getRequestDispatcher("updateAccount.jsp").forward(request, response);
                return;
            }
            case "delete": {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                break;
            }
        }

        List<Account> list = dao.findAll();
        request.setAttribute("accountList", list);
        request.getRequestDispatcher("accountList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDAOImpl dao = new AccountDAOImpl();
        AccountService ser = new AccountService();
        EntityManager em = JPAUtil.getEntityManager();
        String action = Optional.ofNullable(request.getParameter("action")).orElse("");

        try {
            switch (action) {
                case "create": {
                    Account account = new Account();
                    account.setUsername(request.getParameter("username"));
                    account.setPasswordHash(request.getParameter("passwordHash"));
                    account.setEmail(request.getParameter("email"));
                    account.setRole(request.getParameter("role"));
                    account.setCreateDate(LocalDate.now());

                    if (dao.isDuplicate(account)) {
                        request.setAttribute("error", "Tài khoản đã tồn tại.");
                        request.setAttribute("account", account);
                        request.getRequestDispatcher("createAccount.jsp").forward(request, response);
                        return;
                    }

                    boolean created = ser.registerNewUser(account.getUsername(), account.getPasswordHash(), account.getEmail());
                    if (created) {
                        request.setAttribute("success", "Thêm tài khoản thành công.");
                    } else {
                        request.setAttribute("error", "Không thể thêm tài khoản.");
                    }
                    break;
                }

                case "update": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Account account = new Account();
                    account.setId(id);
                    account.setUsername(request.getParameter("username"));
                    account.setPasswordHash(request.getParameter("passwordHash"));
                    account.setEmail(request.getParameter("email"));
                    account.setRole(request.getParameter("role"));
                    account.setUpdateDate(LocalDate.now());

                    if (dao.isDuplicateExcludeId(account)) {
                        request.setAttribute("error", "Tài khoản đã tồn tại.");
                        request.setAttribute("account", account);
                        request.getRequestDispatcher("updateAccount.jsp").forward(request, response);
                        return;
                    }

                    boolean updated = ser.updateAccount(account);
                    if (updated) {
                        request.setAttribute("success", "Cập nhật tài khoản thành công.");
                    } else {
                        request.setAttribute("error", "Không thể cập nhật tài khoản.");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
        } finally {
            em.close();
        }

        List<Account> list = dao.findAll();
        request.setAttribute("accountList", list);
        request.getRequestDispatcher("accountList.jsp").forward(request, response);
    }
}