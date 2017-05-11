package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NoteDao {
	/**
	 * 通过笔记本Id查询全部笔记
	 * @param notobookId
	 * @return
	 */
	List<Map<String,Object>> findNotesByNotebookId(String notebookId);
	
	/**
	 * 通过ID查询笔记
	 * @param noteId
	 * @return
	 */
	Note findNoteById(String noteId);
	/**
	 * 动态Note属性更新
	 * 必须的参数为:
	 * 			id:笔记ID
	 * 可选参数:
	 * 			notebookId:笔记本ID
	 * 			title:标题
	 * 			body:笔记内容
	 * 			userId:作者ID
	 * 			lastModifyTime:最后编辑时间
	 * 			statusId:笔记状态ID
	 *          typeId:笔记类型ID
	 *          createTime:创建时间
	 * @param note中包含了更新数据
	 * @return 更新数量
	 */
	int updateNote(Map<String,Object> note);
	
	/**
	 * 新增一个笔记
	 * @param note
	 * @return
	 */
	int addNote(Note note);
	
	/**
	 * 根据id删除一条Note
	 * @param id
	 * @return
	 */
	int deleteNoteById(String id);
	
	
	//动态参数查询
	List<Map<String,Object>> findNotesByParam(Map<String,Object> param);
	
	
	//批量删除方法
	int deleteNotes(Map<String,Object> param); 
	
	
	//分页查询
	List<Map<String,Object>> findNotesByLimit(Map<String,Object> param);
	
	
}
