package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter("/principal/*") /* intercepta todas as requisi��es que vierem do projeto ou mapeamento */
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {

	}

	/* Encerra os processos quando o servidor estiver parado */
	public void destroy() {

		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;/* pega a requisi��o */
			HttpSession session = req.getSession();/* abre a sess�o */

			String usuarioLogado = (String) session.getAttribute("usuario");/* Pego a tributo a ser verificado */

			String urlParaAutenticar = req.getServletPath();/* URL que est� sendo acessada */

			if (usuarioLogado == null
					&& !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {/* ainda n�o est� logado */

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return;/* para a execu��o e redirecionapara o login */

			} else {

				chain.doFilter(request, response);

			}
			
			connection.commit();

			
		} catch (Exception e) {

			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
		
		System.out.println("classe FILTERAUTENTICACAO funcionando perfeitamente!!!!");

	}

	public void init(FilterConfig fConfig) throws ServletException {

		connection = SingleConnectionBanco.getConnection();
	}

}
