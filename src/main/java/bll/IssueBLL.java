package bll;

import java.util.List;

import bo.Issue;
import dal.IssueDAO;

public class IssueBLL {
	private IssueDAO idao;
	
	public IssueBLL() {
		idao = new IssueDAO();
	}
	
	public List<Issue> selectAll() {
		return idao.select();
	}
	
	public Issue selectById(int id) {
		return idao.selectById(id);
	}
	
	public void update(Issue issue) {
		idao.update(issue);
	}

	public void updateColumn(int idIssue, int idColumn) {
		idao.updateColumn(idIssue, idColumn);
	}
	
	public void delete(Issue issue) {
		idao.delete(issue);
	}

	public Issue insert(int columnId, String title, int order) {
		Issue issue = new Issue();
		issue.setTitle(title);
		issue.setOrder(order);
		idao.insert(issue);
		idao.updateColumn(issue.getId(), columnId);
		return issue;
	}
}
