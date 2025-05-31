package com.transperfumes.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class PedidoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String identificacion = request.getParameter("identificacion");
        String direccion = request.getParameter("direccion");
        String ciudad = request.getParameter("ciudad");
        String pais = request.getParameter("pais");
        String[] perfumes = request.getParameterValues("perfumes");
        System.out.println("[PedidoServlet] Recibido: nombre=" + nombre + ", identificacion=" + identificacion + ", direccion=" + direccion + ", ciudad=" + ciudad + ", pais=" + pais + ", perfumes=" + (perfumes != null ? String.join(",", perfumes) : "null"));
        if (perfumes == null) {
            perfumes = new String[0];
        }

        // Recoger datos del formulario manualmente (x-www-form-urlencoded)
        StringBuilder postData = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            postData.append(line);
        }
        String[] params = postData.toString().split("&");
        for (String param : params) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                System.out.println("[PedidoServlet] Param: " + pair[0] + " = " + java.net.URLDecoder.decode(pair[1], "UTF-8"));
            }
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://transperfumes_mysql:3306/transperfumes", "root", "rootpass");
            System.out.println("[PedidoServlet] Conexión a la base de datos exitosa");

            PreparedStatement ps = con.prepareStatement("INSERT INTO pedidos(nombre, identificacion, direccion, ciudad, pais, perfumes) VALUES(?,?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, identificacion);
            ps.setString(3, direccion);
            ps.setString(4, ciudad);
            ps.setString(5, pais);
            ps.setString(6, String.join(",", perfumes));

            ps.executeUpdate();
            System.out.println("[PedidoServlet] Pedido almacenado correctamente en la base de datos");
            con.close();

            response.setContentType("text/plain");
            response.getWriter().write("OK");

        } catch (Exception e) {
            System.out.println("[PedidoServlet] Error al conectar o almacenar en la base de datos: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
