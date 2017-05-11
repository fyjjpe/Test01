package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Notebook;

public interface NotebookDao {
	/**
	 * 通过userId查询全部笔记本
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> findNotebooksByUserId(String userId);
	
	Notebook findNotebookById(String id);
	
	/**
	 * 新增一个笔记本
	 * @param notebook
	 * @return
	 */
	int addNotebook(Notebook notebook);
	
	/**
	 * 根据id删除笔记本
	 * @param id
	 * @return
	 */
	int deleteNoteBookById(String id);
	
	List<Map<String,Object>> findNotebooksByParam(
			Map<String,Object> param);
	
}
