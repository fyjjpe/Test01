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
	//spring-aop������ע�⣬֧������ع���ֻ����������ִ�е�����û�쳣����ִ����ɲ��ύ
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException {
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("NotebookId ���ܿ�");
		}
		
		Notebook notebook = notebookDao.findNotebookById(notebookId);
		if(notebook==null){
			throw new NotebookNotFoundException("�ʼǱ�������");
		}
		return noteDao.findNotesByNotebookId(notebookId);
	}

	@Transactional(readOnly=true)
	public Note getNote(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("NoteId ���ܿ�");
		}	
		Note note = noteDao.findNoteById(noteId);
		if(note==null){
			throw new NoteNotFoundException("�ʼǲ�����");
		}
		return note;
	}
	
	@Transactional
	public boolean saveNote(String id, String title, String body) throws NoteNotFoundException {
		if(id==null || id.trim().isEmpty()){
			throw new NoteNotFoundException("id������");
		}
		if(title==null || title.trim().isEmpty()){
			//��ȡbody�е����ݣ����ʼǱ���title
			String reg = "<p>[^<>]+</p>";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(body);
			if(m.find()){
				String str = m.group();
				title = str.substring(3, str.length()>17?13:str.length()-4).trim();
			}else{
				//���body�������ݣ�����Ϊ�ޱ���
				title="�ޱ���";
			}
		}
		if(body== null){
			//���bodyΪ��ʱ����body����Ϊ���ַ���
			body="";
		}
		Map<String,Object> note = new HashMap<String, Object>();
		note.put("id", id);
		note.put("title", title);
		note.put("body", body);
		note.put("lastModifyTime", System.currentTimeMillis());
		int n = noteDao.updateNote(note);
		//���³ɹ�,n=1,���򲻳ɹ�
		return n==1;
	}

	@Transactional
	public Note addNote(String notebookId, String userId, String title) 
			throws NotebookNotFoundException,UserNotFoundException {
		if(notebookId == null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("NotebookId ���ܿ�");
		}
		if(userId == null || userId.trim().isEmpty()){
			throw new UserNotFoundException("UserId ���ܿ�");
		}
		if(title==null || title.trim().isEmpty()){
			title="�ޱ���";
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
			throw new RuntimeException("����ʧ��");
		}
		return note;
	}

	@Transactional
	public List<Map<String,Object>> deleteNote(String noteId) throws NoteNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new NoteNotFoundException("NoteId ���ܿ�");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null){
			throw new NoteNotFoundException("�ʼǲ�����");
		}
		Map<String,Object> newNote = new HashMap<String, Object>();
		newNote.put("id",note.getId());
		//ɾ���ʼǾ��ǽ��ʼ�statusId״̬��Ϊ2
		newNote.put("statusId", "2");
		int n = noteDao.updateNote(newNote);
		if(n!=1){
			throw new RuntimeException("ɾ��ʧ��");
		}
		String notebookId = note.getNotebookId();
		//���ظ���ɾ���ʼǵıʼǱ���Ӧ�����бʼ�
		List<Map<String,Object>> notes = noteDao.findNotesByNotebookId(notebookId);
		return notes;
	}
	
	@Transactional//spring-aop������ע�⣬֧������ع���ֻ����������ִ�е�����û�쳣����ִ����ɲ��ύ
	public int deleteNotes(String... ary) {
		int n = 0;
		for (String id : ary) {
			Note note = noteDao.findNoteById(id);
			if(note==null){
				throw new NoteNotFoundException("id ������"+id);
			}
			n+=noteDao.deleteNoteById(id);
		}
		return n;
	}

}
