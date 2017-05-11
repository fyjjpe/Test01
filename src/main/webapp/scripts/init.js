/*
 * 初始化脚本 
 */
//自定义常量，不是JS支持的，一般大写，我们默认的
var SUCCESS=0;
var ERROR=1;

//页面中的数据模型,用来储存页面中显示的数据,
//已经封装更新视图的方法
var model={};

$(function(){
//	console.log('init OK');
	//加载笔记本列表信息
	loadNotebooksAction();
	
	//监听笔记本的点击事件
	//.on(事件名称,事件源选择器,事件方法):适合绑定带传播的事务
	$('#notebooks').on('click','.notebook',listNotesAction);
	
	//监听点击more按钮的事件
	$('#notebooks').on('click','.more',loadNotebooksAction);
	
	//在note.js中定义listNotesAction方法
	
	//在note.js中定义getNoteAction方法
	$('#notes').on('click','li',getNoteAction);
	
	//在note.js中定义saveNoteAction方法
	$('#save_note').click(saveNoteAction);
	
	//监听关闭窗口事件
	$('#can').on('click','.close, .cancel',closeAction);
	
	//显示增加笔记本对话框动作
	$('#add_notebook').click(showNotebookDialogAction);
	
	//定义打开增加笔记窗口事件
	$('#add_note').click(showNoteDialogAction);
	
	//点击按钮显示下拉框
	$('#notes').on('click','.btn_slide_down',showNoteMenuAction);
	//点击整个屏幕都将笔记的下拉子菜单隐藏
	$('body').click(function(){
		$('#notes .note_menu').hide();
	});
	
	//监听 删除按钮被点击了
	$('#notes').on('click','.btn_delete',showDeleteNoteDialogAction);
	
});

//显示删除笔记对话框事件
function showDeleteNoteDialogAction(){
	$('#can').load('alert/alert_delete_note.html',function(){
		$('.opacity_bg').show();
		$('#can .sure').click(deleteNoteAction);//绑定删除笔记事件
	});
	
}

//点击按钮显示下拉框事件
function showNoteMenuAction(){
	$(this).parent().next().toggle();
	return false;
}

//打开增加笔记对话框动作
function showNoteDialogAction(){
	//为了避免为选定笔记本，就进行笔记添加功能，做成如下判断
	if(!model.notebooks[model.notebookIndex]){//未选定笔记本，就直接返回
		return;
	}
	$('#can').load('alert/alert_note.html',function(){
		$('.opacity_bg').show();
		$('#can .sure').click(addNoteAction);
	});
}


//显示增加笔记本对话框动作
function showNotebookDialogAction(){
	//加载成功后执行function函数
	//显示对话框:load()函数为edit内部定义的js原生alert函数
	$('#can').load('alert/alert_notebook.html',function(){
		$('.opacity_bg').show();
		//在事件内部进行事件对象的捕捉
		$('#can .sure').click(addNotebookAction);
	});
}

//定义窗口弹出的关闭功能
function closeAction(){
	//将窗口html清除
	$('#can').empty();
	//将背景隐藏
	$('.opacity_bg').hide();
}







