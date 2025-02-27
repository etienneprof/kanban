package bll;

import java.util.List;

import bo.Column;
import dal.ColumnDAO;

public class ColumnBLL {
	private ColumnDAO cdao;
	
	public ColumnBLL() {
		cdao = new ColumnDAO();
	}
	
	public List<Column> selectAll() {
		return cdao.select();
	}
	
	public Column selectById(int id) {
		return cdao.selectById(id);
	}
	
	public void insert(Column column) {
		cdao.insert(column);
	}
	
	public void update(Column column) {
		cdao.update(column);
	}
	
	public void delete(Column column) {
		cdao.delete(column);
	}
}
