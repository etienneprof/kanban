package bo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name = "columns")
public class Column {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String label;
	
	@jakarta.persistence.Column(name = "column_order")
	private int order;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "column_id")
	private List<Issue> relatedIssues;

	public Column() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public List<Issue> getRelatedIssues() {
		return relatedIssues;
	}
	
	public void setRelatedIssues(List<Issue> relatedIssues) {
		this.relatedIssues = relatedIssues;
	}
}
