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
	
	//删除功能(伪删除)
	List<Map<String,Object>> deleteNote(String noteId) throws NoteNotFoundException;
	
	//下面的String... 就是String[]的意思，
	//String...表示的是变长参数
	int deleteNotes(String... id) ;
	
}
