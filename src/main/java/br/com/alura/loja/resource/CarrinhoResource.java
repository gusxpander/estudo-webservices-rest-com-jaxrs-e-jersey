package br.com.alura.loja.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Carrinho busca(@PathParam("id") long id) {

		Carrinho carrinho = new CarrinhoDAO().busca(id);
		System.out.println(carrinho);
		return carrinho;

	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(Carrinho conteudo) throws URISyntaxException {

		Carrinho carrinho = conteudo;
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = new URI("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();

	}
	
	@DELETE
	@Path("{id}/produtos/{produtoId}")
	public Response deletarProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
		
	}
	
	
	
	@Path("{id}/produtos/{produtoId}/quantidade")
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	public Response atualizaQuantidade(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto){

		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto novoProduto = produto;
		carrinho.trocaQuantidade(novoProduto);
		return Response.ok().build();

	}

}