package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NoteDao {
	/**
	 * ͨ���ʼǱ�Id��ѯȫ���ʼ�
	 * @param notobookId
	 * @return
	 */
	List<Map<String,Object>> findNotesByNotebookId(String notebookId);
	
	/**
	 * ͨ��ID��ѯ�ʼ�
	 * @param noteId
	 * @return
	 */
	Note findNoteById(String noteId);
	/**
	 * ��̬Note���Ը���
	 * ����Ĳ���Ϊ:
	 * 			id:�ʼ�ID
	 * ��ѡ����:
	 * 			notebookId:�ʼǱ�ID
	 * 			title:����
	 * 			body:�ʼ�����
	 * 			userId:����ID
	 * 			lastModifyTime:���༭ʱ��
	 * 			statusId:�ʼ�״̬ID
	 *          typeId:�ʼ�����ID
	 *          createTime:����ʱ��
	 * @param note�а����˸�������
	 * @return ��������
	 */
	int updateNote(Map<String,Object> note);
	
	/**
	 * ����һ���ʼ�
	 * @param note
	 * @return
	 */
	int addNote(Note note);
	
	/**
	 * ����idɾ��һ��Note
	 * @param id
	 * @return
	 */
	int deleteNoteById(String id);
	
	
	//��̬������ѯ
	List<Map<String,Object>> findNotesByParam(Map<String,Object> param);
	
	
	//����ɾ������
	int deleteNotes(Map<String,Object> param); 
	
	
	//��ҳ��ѯ
	List<Map<String,Object>> findNotesByLimit(Map<String,Object> param);
	
	
}
