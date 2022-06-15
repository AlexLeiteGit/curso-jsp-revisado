package servlets;

import java.io.IOException;

import dao.DaoLoginRepository;
import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/*
 * As Servlet também são chamadas de Controller e a classe poderia ser chamada de ServletLoginController
 */
@WebServlet(urlPatterns = { "/ServletLogin", "/principal/ServletLogin" }) /* Mapeamento da url que vem da tela */
public class ServletLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoLoginRepository daoLoginRepository = new DaoLoginRepository();
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	public ServletLoginController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");//parâmetro enviado pelo href do navbar.jsp ou navbarMyMenu.jsp
		
		if(acao != null && acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			
			System.out.println("Logout realizado com sucesso!!!!!");
			request.getSession().invalidate();//invalida a sessão
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
			
		} else {
		
		System.out.println("Voltando para o doPost() através do retorno do métdo doGet()");
		doPost(request, response);
		
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				if (daoLoginRepository.validarAutenticacao(modelLogin)) {
					
					modelLogin = daoUsuarioRepository.consultaUsuarioLogado(login);

					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());

					if (url == null || url.equals("null")) {
						url = "/principal/principal.jsp";
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {

					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente");
					redirecionar.forward(request, response);

				}

				System.out.println("classe SERVLETLOGIN funcionando perfeitamente!!!!");

			} else {

				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e senha corretamente");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}
