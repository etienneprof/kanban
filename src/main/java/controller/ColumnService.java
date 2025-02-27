package controller;

import java.util.List;

import bll.ColumnBLL;
import bo.Column;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/columns")
@Produces(MediaType.APPLICATION_JSON)
public class ColumnService {
	private ColumnBLL cbll;
	
	public ColumnService() {
		cbll = new ColumnBLL();
	}
	
	@GET
	public List<Column> get() {
		return cbll.selectAll();
	}
	
	@GET @Path("/{id}")
	public Column get(@PathParam("id") int id) {
		return cbll.selectById(id);
	}
	
	@POST
	public Column insert(Column column) {
		cbll.insert(column);
		return column;
	}
	
	@PUT @Path("/{id}")
	public Column update(@PathParam("id") int id, Column column) {
		column.setId(id);
		cbll.update(column);
		return column;
	}
	
	@DELETE @Path("/{id}")
	public Column delete(@PathParam("id") int id) {
		Column column = cbll.selectById(id);
		cbll.delete(column);
		return column;
	}
}
