package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		String acao = request.getParameter("acao");/*parâmetro hidden que criamos dentro do formulário*/
		
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
			String idUser = request.getParameter("id");
			daoUsuarioRepository.deletarUsuario(idUser);
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", "usuário EXCLUÍDO COM SUCESSO!!!");
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
			
			String idUser = request.getParameter("id");
			daoUsuarioRepository.deletarUsuario(idUser);
			
			response.getWriter().write("usuário EXCLUÍDO COM SUCESSO!!!");
		
		} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuarioAjax")) {
			
			String nomeBusca = request.getParameter("nomeBusca");
			
			List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dadosJsonUser);
			
			response.getWriter().write(json);
		
		} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
			
			String id = request.getParameter("id");
			
			ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", "USUÁRIO EM EDIÇÃO");
			request.setAttribute("modelLogin", modelLogin);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			
			request.setAttribute("msg", "USUÁRIO CARREGADOS");
			request.setAttribute("modelLogins", modelLogins);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		} else {
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String msg = "OPERAÇÃO REALIZADA COM SUCESSO!!!!";

			System.out.println("Começamos a usar a ServletUsuarioController");

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String email = request.getParameter("email");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setEmail(email);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			
			if(ServletFileUpload.isMultipartContent(request)) {
				
				Part part = request.getPart("fileFoto");/*Pega a foto da tela*/
				
				if(part.getSize() > 0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream());/*converte a imagem em um array de bytes*/
					String imagembase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
					
					modelLogin.setFotouser(imagembase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
				}
			}

			if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "USUÁRIO JÁ EXISTENTE COM ESTE LOGIN, INFORME OUTRO POR FAVOR!!";
			} else {
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}

			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);// o primeiro parâmetro é o nome e o segundo o objeto
															// modelLogin que estamos usadno acima. Ele é que será
															// retornado.
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			System.out.println("Terminamos de usar a ServletUsuarioController");

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}
