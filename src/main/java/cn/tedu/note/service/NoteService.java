package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;
import cn.tedu.note.exception.NoteNotFoundException;
import cn.tedu.note.exception.NotebookNotFoundException;
import cn.tedu.note.exception.UserNotFoundException;

public interface NoteService {
	List<Map<String,Object>> listNotes(String notebookId) throws NotebookNotFoundException;
	
	Note getNote(String noteId) throws NoteNotFoundException;
	
	boolean saveNote(String id,String title,String body) throws NoteNotFoundException;
	
	Note addNote(String notebookId,String userId,String title) 
			throws NotebookNotFoundException,UserNotFoundException;
	
	//ɾ������(αɾ��)
	List<Map<String,Object>> deleteNote(String noteId) throws NoteNotFoundException;
	
	//�����String... ����String[]����˼��
	//String...��ʾ���Ǳ䳤����
	int deleteNotes(String... id) ;
	
}
