package cn.tedu.note.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;
import cn.tedu.note.exception.NoteNotFoundException;
import cn.tedu.note.exception.NotebookNotFoundException;
import cn.tedu.note.exception.UserNotFoundException;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
	
	@Resource
	private NoteDao noteDao;
	
	@Resource
	private NotebookDao notebookDao;
	
	@Transactional(readOnly=true)
	//spring-aop的事务注解，支持事务回滚，只有整个方法执行的事务没异常，才执行完成并提交
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException {
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("NotebookId 不能空");
		}
		
		Notebook notebook = notebookDao.findNotebookById(notebookId);
		if(notebook==null){
			throw new NotebookNotFoundException("笔记本不存在");
		}
		return noteDao.findNotesByNotebookId(notebookId);
	}

	@Transactional(readOnly=true)
	public Note getNote(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("NoteId 不能空");
		}	
		Note note = noteDao.findNoteById(noteId);
		if(note==null){
			throw new NoteNotFoundException("笔记不存在");
		}
		return note;
	}
	
	@Transactional
	public boolean saveNote(String id, String title, String body) throws NoteNotFoundException {
		if(id==null || id.trim().isEmpty()){
			throw new NoteNotFoundException("id不存在");
		}
		if(title==null || title.trim().isEmpty()){
			//截取body中的数据，做笔记标题title
			String reg = "<p>[^<>]+</p>";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(body);
			if(m.find()){
				String str = m.group();
				title = str.substring(3, str.length()>17?13:str.length()-4).trim();
			}else{
				//如果body中无数据，则定义为无标题
				title="无标题";
			}
		}
		if(body== null){
			//如果body为空时，将body设置为空字符串
			body="";
		}
		Map<String,Object> note = new HashMap<String, Object>();
		note.put("id", id);
		note.put("title", title);
		note.put("body", body);
		note.put("lastModifyTime", System.currentTimeMillis());
		int n = noteDao.updateNote(note);
		//更新成功,n=1,否则不成功
		return n==1;
	}

	@Transactional
	public Note addNote(String notebookId, String userId, String title) 
			throws NotebookNotFoundException,UserNotFoundException {
		if(notebookId == null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("NotebookId 不能空");
		}
		if(userId == null || userId.trim().isEmpty()){
			throw new UserNotFoundException("UserId 不能空");
		}
		if(title==null || title.trim().isEmpty()){
			title="无标题";
		}
		
		String id = UUID.randomUUID().toString();
		String statusId = "1";
		String typeId = null;
		String body = "";
		Long createTime = System.currentTimeMillis();
		Long lastModifyTime = System.currentTimeMillis();
		Note note = new Note(id, notebookId, userId, statusId, typeId, 
				title, body, createTime, lastModifyTime);
		int n = noteDao.addNote(note);
		if(n!=1){
			throw new RuntimeException("保存失败");
		}
		return note;
	}

	@Transactional
	public List<Map<String,Object>> deleteNote(String noteId) throws NoteNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new NoteNotFoundException("NoteId 不能空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null){
			throw new NoteNotFoundException("笔记不存在");
		}
		Map<String,Object> newNote = new HashMap<String, Object>();
		newNote.put("id",note.getId());
		//删除笔记就是将笔记statusId状态变为2
		newNote.put("statusId", "2");
		int n = noteDao.updateNote(newNote);
		if(n!=1){
			throw new RuntimeException("删除失败");
		}
		String notebookId = note.getNotebookId();
		//返回根据删除笔记的笔记本对应的所有笔记
		List<Map<String,Object>> notes = noteDao.findNotesByNotebookId(notebookId);
		return notes;
	}
	
	@Transactional//spring-aop的事务注解，支持事务回滚，只有整个方法执行的事务没异常，才执行完成并提交
	public int deleteNotes(String... ary) {
		int n = 0;
		for (String id : ary) {
			Note note = noteDao.findNoteById(id);
			if(note==null){
				throw new NoteNotFoundException("id 不存在"+id);
			}
			n+=noteDao.deleteNoteById(id);
		}
		return n;
	}

}
