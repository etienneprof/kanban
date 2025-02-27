package controller;

import java.util.List;

import bll.IssueBLL;
import bo.Issue;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import vo.IssueVO;

@Path("/issues")
@Produces(MediaType.APPLICATION_JSON)
public class IssueService {
	private IssueBLL ibll;
	
	public IssueService() {
		ibll = new IssueBLL();
	}
	
	@GET
	public List<Issue> get() {
		return ibll.selectAll();
	}
	
	@GET @Path("/{id}")
	public Issue get(@PathParam("id") int id) {
		return ibll.selectById(id);
	}
	
	@POST
	public Issue insert(IssueVO issue) {
		return ibll.insert(issue.getColumnId(), issue.getTitle(), issue.getOrder());
	}
	
	@PUT @Path("/{id}")
	public Issue update(@PathParam("id") int id, Issue issue) {
		issue.setId(id);
		ibll.update(issue);
		return issue;
	}
	
	@PUT @Path("/{id}/{idcolumn}")
	public Issue update(@PathParam("id") int id, @PathParam("idcolumn") int idColumn) {
		Issue issue = ibll.selectById(id);
		ibll.updateColumn(id, idColumn);
		return issue;
	}
	
	@DELETE @Path("/{id}")
	public Issue delete(@PathParam("id") int id) {
		Issue issue = ibll.selectById(id);
		ibll.delete(issue);
		return issue;
	}
}
