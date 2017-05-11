package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Notebook;

public interface NotebookDao {
	/**
	 * ͨ��userId��ѯȫ���ʼǱ�
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> findNotebooksByUserId(String userId);
	
	Notebook findNotebookById(String id);
	
	/**
	 * ����һ���ʼǱ�
	 * @param notebook
	 * @return
	 */
	int addNotebook(Notebook notebook);
	
	/**
	 * ����idɾ���ʼǱ�
	 * @param id
	 * @return
	 */
	int deleteNoteBookById(String id);
	
	List<Map<String,Object>> findNotebooksByParam(
			Map<String,Object> param);
	
}
