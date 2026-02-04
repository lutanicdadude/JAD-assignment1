package adminControllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceStatus;
import java.io.IOException;
import dao.ServiceDAO;

@WebServlet("/admin/AddService")
public class AddService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddService() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get data from form
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String description = request.getParameter("description");

            double price = Double.parseDouble(request.getParameter("price"));
            int duration = Integer.parseInt(request.getParameter("duration"));

            boolean active = request.getParameter("active") != null;

            // DB call
            ServiceDAO dao = new ServiceDAO();
            ServiceStatus result = dao.addService(name, category, description, price, duration, active);

            // Check if success or error
            if (result.isSuccess()) {
                // SUCCESS → redirect
                response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
            } else {
                // ERROR → show modal again with message
                request.setAttribute("modalToOpen", "createServiceModal");
                request.setAttribute("errorMessage", result.getMessage());
                request.getRequestDispatcher("/admin/DisplayAdminDetails").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // Handle invalid number input
            request.setAttribute("errorMessage", "Invalid price or duration value.");
            request.getRequestDispatcher("/admin/DisplayAdminDetails").forward(request, response);
        }
    }
}
