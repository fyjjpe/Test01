/*
 * note.js 封装与note有关的脚本
*/

function listNotesAction(){
//	console.log('listNotesAction');
	//$(this) 就是li对象，就是被点击的li对象
//	console.log($(this));
	//获取显示时候绑定的序号
	var index = $(this).data('index');
//	console.log(index);
	//第七天:将选定的笔记本index保存到model中
	model.notebookIndex = index;
	
	//设置选中笔记本的效果
	$('#notebooks li a').removeClass('checked');
	$(this).find('a').addClass('checked');
	
	var url = 'note/list.do';
	var id = model.notebooks[index].id;
	
	var param = {notebookId:id};
//	console.log(param);
	$.getJSON(url,param,function(result){
		if(result.state==SUCCESS){
			var notes = result.data;
//			console.log(notes);
			model.updateNotesView(notes);
		}else{
			alert(result.message);
		}
	});
}

//更新笔记列表
model.updateNotesView=function(notes){
//	console.log('updateNotesView');
	//对传来的参数进行判断
	//给模型中增加新属性，储存当前笔记列表
	if(notes){//此处的notes的判定也可以修改成:arguments.length!=0
		this.notes = notes;
	}
	//遍历笔记数组，将笔记列表显示到界面
	var template='<li class="online">'
				+'<a>'
				+'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '
				+'#{title}'
				+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down">'
				+'<i class="fa fa-chevron-down"></i></button>'
				+'</a>'
				+'<div class="note_menu" tabindex="-1">'
				+'<dl>'
				+'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'
				+'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'
				+'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'
				+'</dl>'
				+'</div>'
				+'</li>';
	var ul = $('#notes').empty();
	for(var i=0;i<this.notes.length;i++){
		var note = this.notes[i];
		var li = $(template.replace('#{title}', note.title));
		//绑定序号到li
		li.data('index',i);
		//添加选定效果
		if(model.noteIndex==i){
			li.find('a').addClass('checked');
		}
		ul.append(li);
	}
}


function getNoteAction(){
//	console.log('getNoteAction');
	var li = $(this);
	//获取绑定的li上的序号
	var index = li.data('index');
//	console.log(index);
	
	//保存笔记的index到 model中,留作后面修改笔记时更改笔记列表的名字使用
	model.noteIndex = index;
	
	//设计笔记按钮显示选中
	$('#notes li a').removeClass('checked');
	$(this).find('a').addClass('checked');
	
	var id = model.notes[index].id;
	var url = 'note/get.do';
	var param = {noteId:id};
//	console.log(param);
	$.getJSON(url,param,function(result){
		if(result.state==SUCCESS){
			var note = result.data;
//			console.log(note);
			model.updateNote(note);
		}else{
			alert(result.message);
		}
	});
}

model.updateNote =function(note){
//	console.log('updateNote');
	//将笔记的详细信息存储到model中
	this.note = note;
	
	//将笔记的信息显示到界面中
	$('#input_note_title').val(this.note.title);
	//使用edit.html中的um控件将数据库查询到的文件内容，显示到笔记文本区域
	um.setContent(this.note.body);
}


//定义保存笔记按钮的事件
function saveNoteAction(){
	//console.log('saveNoteAction');
	var url = 'note/save.do';
	var note = model.note;
	var title = $('#input_note_title').val();
	var body = um.getContent();
	
	//如果未选定笔记，也无法提交保存
	if(!note){
		return;
	}
	
	//假如当前编辑笔记显示的笔记内容与原来的一致，即用户未修改笔记
	if(note.title==title && note.body==body){
		//未修改，则直接返回，不发起请求
		return;
	}
	var param = {
			id:model.note.id,
			title:title,
			body:body	
	};
//	console.log(param);
	
	//给用户视觉上的体验效果,例如菊花在转 。本案例将按钮设为在提交时，无法点击
	$('#save_note').attr('disabled','disabled').html('保存中...');
	
	$.post(url,param,function(result){
//		console.log(result);
		
//		//请求成功后，将按钮无法点击属性删除
//		$('#save_note').removeAttr('disabled').html('保存笔记');
		
		//以下是动感体验效果
		setTimeout(function(){
			$('#save_note').removeAttr('disabled').html('保存笔记');
		}, 800);
		
		if(result.state==SUCCESS){
//			console.log(result.data);
			if(result.data){//更改成功返回的是true
				model.note.title=title;
				model.note.body=body;
				//更改笔记本列表中的笔记title
				model.notes[model.noteIndex].title = title;
				//更新笔记本列表区域
				model.updateNotesView();
				
			}
		}else{
			alert(result.message);
		}
	});
}

//创建笔记的功能
function addNoteAction(){
//	console.log('addNoteAction');
	var url = 'note/add.do';
	
	var notebookId = model.notebooks[model.notebookIndex].id;//还可以通过cookie中进行存取
	var userId = getCookie('userId');
	var title = $('#input_note').val();
	var param = {
			notebookId:notebookId,
			userId:userId,
			title:title
	};
//	console.log(param);
	
	var btn = $(this).attr('disabled','disabled').html('创建中...');
	
	//发送Ajax请求
	$.post(url,param,function(result){
//		console.log(result);
		if(result.state==SUCCESS){//请求成功了
			setTimeout(function(){
				btn.removeAttr('disabled').html('创建');
				//关闭窗口
				closeAction();
				//第七天:更新笔记列表，让新建的笔记在第一行
				var note = result.data;
				//在数字的第一个位置插入新笔记(查js)
				//unshift() 方法可向数组的开头添加一个或更多元素，并返回新的长度。
				model.notes.unshift({id:note.id,
					title:note.title});
				model.noteIndex = 0;
				model.updateNote(note);
				model.updateNotesView();
			}, 800)
		}else{
			alert(result.message);
		}
	});
}

//删除笔记功能
function deleteNoteAction(){
//	console.log('deleteNoteAction');
	var url = 'note/delete.do';
//	var note = model.note;
//	console.log(note.id);
	var param = {
			noteId:model.note.id
	};
	var btn = $(this).attr('disabled','disabled').html('删除中...');
	$.post(url,param,function(result){
//		console.log(result);
		if(result.state==SUCCESS){
			setTimeout(function(){
				btn.removeAttr('disabled').html('删除');
				//关闭窗口
				closeAction();
				var notes = result.data;
//				console.log(notes);
				//更新笔记列表
				model.updateNotesView(notes);
			}, 800);
		}else{
			alert(result.message);
		}	
	});
} 


































