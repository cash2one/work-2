Ext.namespace("com.zz91.zzbook.bookinfo");

var BOOKINFO = new function(){
	this.BOOKINFO_GRID = "bookinfogrid";
	this.BOOKINFO_WIN = "bookinfowindows";
	this.BOOKINFO_FORM="bookinfoform";
}

com.zz91.zzbook.bookinfo.Field=[
	{name:"id",mapping:"book.id"},
	{name:"name",mapping:"book.name"},
	{name:"author",mapping:"book.author"},
	{name:"type",mapping:"book.type"},
	{name:"press",mapping:"book.press"},
	{name:"donatePerson",mapping:"book.donatePerson"},
	{name:"donateDepart",mapping:"book.donateDepart"},
	{name:"donateTime",mapping:"book.donateTime"},
	{name:"picCover",mapping:"picCover"}
];

com.zz91.zzbook.bookinfo.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:com.zz91.zzbook.bookinfo.Field,
			url:Context.ROOT +  "/book/query.htm",
			autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		var _cm=new Ext.grid.ColumnModel( [_sm,{
				header :"id",
				sortable : false,
				hidden:true,
				dataIndex:"id"
			},{
				header :"书名",
				sortable : false,
				dataIndex:"name"
			},{
				header :"作者",
				sortable : false,
				dataIndex:"author"
			},{
				header :"图书类别",
				sortable : false,
				dataIndex:"type"
			},{
				header :"出版社",
				sortable : false,
				dataIndex:"press"
			},{
				header :"图书捐赠人",
				sortable : false,
				dataIndex:"donatePerson"
			},{
				header :"图书捐赠部门",
				sortable : false,
				dataIndex:"donateDepart"
			},{
				header : "捐赠时间",
				sortable : true,
				dataIndex :"donateTime",
				width:150,
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}else{
						return "";
					}
				}
			}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"start",limit:"limit"}
			})
		};
		
		com.zz91.zzbook.bookinfo.Grid.superclass.constructor.call(this,c);
		
	},
	mytoolbar:[{
		text : '新增图书',
		iconCls : 'add16',
		handler : function(btn){
			com.zz91.zzbook.bookinfo.addOrEdit(0);
		}
	},{
		text : '编辑',
		iconCls : 'edit16',
		handler : function(btn){
			var row = Ext.getCmp(BOOKINFO.BOOKINFO_GRID).getSelectionModel().getSelected();
			if(row!=null){
				com.zz91.zzbook.bookinfo.addOrEdit(row.get("id"));
			}
		}
	}
//	,{
//		text : '正文',
//		tooltip : '正文',
//		iconCls : 'edit16',
//		handler : function(btn){
//			var row = Ext.getCmp(BOOKINFO.BOOKINFO_GRID).getSelectionModel().getSelected();
//			if(row!=null){
//				var win = new Ext.Window({
//					id:BOOKINFO.BOOKINFO_WIN,
//					title:"查看邮件内容",
//					width:700,
//					autoHeight:true,
//					modal:true,
//					items:[{
//						region:"center",
//						height:500,
//						autoLoad:Context.ROOT+"/book/details.htm?id="+row.get("id")
//					},{
//						buttons:[
//							{
//							text:"关闭",
//							handler:function(btn){
//							Ext.getCmp(BOOKINFO.BOOKINFO_WIN).close();
//							}
//						}]
//					}]
//				});
//				win.show();
//			}
//		}
//	},{
//		text : '发送邮件',
//		iconCls : 'book16',
//		handler:function(btn){
//			com.zz91.zzbook.bookinfo.sendMail();
//		}
//	}
	,"->",
	{
		xtype : "textfield",
		name : "name",
		emptyText:"书名检索",
		listeners:{
			"blur":function(field){
				var _store=Ext.getCmp(BOOKINFO.BOOKINFO_GRID).getStore();
				if(field.getValue()!=""){
					_store.baseParams["name"]= field.getValue();
				}else{
					_store.baseParams["name"]=null;
				}
				_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
			}
		}
	}
//	,{
//		xtype : "datefield",
//		format:"Y-m-d",
//		name:"to",
//		emptyText:"计划结束",
//		listeners:{
//			"blur":function(field){
//				var _store=Ext.getCmp(BOOKINFO.BOOKINFO_GRID).getStore();
//				if(field.getValue()!=""){
//					_store.baseParams["to"]= Ext.util.Format.date(field.getValue(), 'Y-m-d H:m:s');
//				}else{
//					_store.baseParams["to"]=null;
//				}
//				_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
//			}
//		}
//	}
	]
});

// 新增表单
com.zz91.zzbook.bookinfo.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
				region:"center",
				layout:"form",
				bodyStyle:'padding:5px 0 0',
				frame:true,
				labelAlign : "right",
				labelWidth : 80,
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
			},
			items:[
			{
				xtype : "hidden",
				name : "id",
				dataIndex : "id"
			},{
				fieldLabel : "书名",
				itemCls :"required",
				name : "name",
				allowBlank : false
			},{
				fieldLabel : "作者",
				itemCls :"required",
				name : "author",
				allowBlank : false
			},{
				fieldLabel : "出版社",
				itemCls :"required",
				name:"press",
				allowBlank : false
			},{
				fieldLabel : "图书类别",
				itemCls :"required",
				name:"type",
				allowBlank : false
			},{
				fieldLabel : "索书号",
//				itemCls :"required",
				name:"code",
				id:"relateCode",
				allowBlank : false
			},{
				fieldLabel : "捐赠人",
				name:"donatePerson",
				allowBlank : false
			},{
				fieldLabel : "捐赠部门",
				name:"donateDepart",
				allowBlank : false
			},{
				xtype:'datefield',
				fieldLabel : "捐赠时间",
				name:"donateTimeStr",
				id:"donateTime",
				allowBlank : false,
				format:"Y-m-d"
			}],
			buttons:[{
				text:"添加",
				scope:this,
				handler:function(btn){
					var url=this.resendUrl;
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
							com.zz91.zzwork.utils.Msg("","添加成功");
								Ext.getCmp(BOOKINFO.BOOKINFO_GRID).getStore().reload();
								Ext.getCmp(BOOKINFO.BOOKINFO_WIN).close();
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.submitFailure,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"取消",
				handler:function(btn){
					Ext.getCmp(BOOKINFO.BOOKINFO_WIN).close();
				}
			}]
		};
		
		com.zz91.zzbook.bookinfo.Form.superclass.constructor.call(this,c);
	},
	loadOneRecord:function(id){
		var reader=[
			{name:"id",mapping:"id"},			
			{name:"name",mapping:"book.name"},
			{name:"author",mapping:"book.author"},
			{name:"type",mapping:"book.type"},
			{name:"press",mapping:"book.press"},
			{name:"donatePerson",mapping:"book.donatePerson"},
			{name:"donateDepart",mapping:"book.donateDepart"},
			{name:"donateTime",mapping:"book.donateTime"},
			];
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			url : Context.ROOT+ "/book/queryOne.htm",
			root : "records",
			fields : reader,
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record != null) {
						form.getForm().loadRecord(record);
						if(record.get("donateTime")!=null){
							form.findById("donateTime").setValue(Ext.util.Format.date(new Date(record.get("donateTime").time),'Y-m-d'));
						}
					}
				}
			}
		});
	},
});


com.zz91.zzbook.bookinfo.addOrEdit=function(id){
	var form = new com.zz91.zzbook.bookinfo.Form({
		id:BOOKINFO.BOOKINFO_FORM,
		resendUrl:Context.ROOT +  "/book/doAddOrUpdate.htm",
		region:"center"
	});
	
	form.loadOneRecord(id);
	if(id>0){
		Ext.getCmp("relateCode").hide();
	}
	
	var win = new Ext.Window({
			id:BOOKINFO.BOOKINFO_WIN,
			title:"邮件信息",
			width:500,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

////发送表单
//com.zz91.zzbook.bookinfo.addOrEditForm = Ext.extend(Ext.form.FormPanel,{
//	constructor:function(config){
//		config = config||{};
//		Ext.apply(this,config);
//		
//		var c={
//				region:"center",
//				layout:"form",
//				bodyStyle:'padding:5px 0 0',
//				frame:true,
//				labelAlign : "right",
//				labelWidth : 80,
//				defaults:{
//					anchor:"100%",
//					xtype:"textfield",
//					labelSeparator:""
//			},
//			items:[
//			{
//				xtype : "hidden",
//				name : "id",
//				dataIndex : "id"
//			},{
//				fieldLabel : "书名",
//				itemCls :"required",
//				name : "title",
//				allowBlank : false
//			},{
//				fieldLabel : "作者",
//				itemCls :"required",
//				name : "author",
//				allowBlank : false
//			},{
//				fieldLabel : "出版社",
//				itemCls :"required",
//				name:"press",
//				allowBlank : false
//			},{
//				fieldLabel : "图书类别",
//				itemCls :"required",
//				name:"type",
//				allowBlank : false
//			},{
//				fieldLabel : "索书号",
//				itemCls :"required",
//				name:"code",
//				allowBlank : false
//			}],
//			buttons:[{
//				text:"确定",
//				scope:this,
//				handler:function(btn){
//					if (this.getForm().isValid()) {
//						this.getForm().submit({
//							url : Context.ROOT +  "/book/edit.htm",
//							method : "post",
//							type:"json",
//							success : function(_form,_action){
//								com.zz91.utils.Msg("","邮件正在发送");
//								Ext.getCmp(BOOKINFO.BOOKINFO_GRID).getStore().reload();
//								Ext.getCmp(BOOKINFO.BOOKINFO_WIN).close();
//							},
//							failure : function(_form,_action){
//								Ext.MessageBox.show({
//									title:MESSAGE.title,
//									msg : MESSAGE.saveFailure,
//									buttons:Ext.MessageBox.OK,
//									icon:Ext.MessageBox.ERROR
//								});
//							}
//						});
//					} else {
//						Ext.MessageBox.show({
//							title:MESSAGE.title,
//							msg : MESSAGE.submitFailure,
//							buttons:Ext.MessageBox.OK,
//							icon:Ext.MessageBox.ERROR
//						});
//					}
//					
//				}
//			},{
//				text:"取消",
//				handler:function(btn){
//					Ext.getCmp(BOOKINFO.BOOKINFO_WIN).close();
//				}
//			}]
//		};
//		
//		com.zz91.zzbook.bookinfo.addOrEditForm.superclass.constructor.call(this,c);
//	},
//});
	
//com.zz91.zzbook.bookinfo.addNewBook=function(){
//	var form = new com.zz91.zzbook.bookinfo.addOrEditForm({
//		id:BOOKINFO.BOOKINFO_FORM,
//		sendUrl:Context.ROOT +  "/book/edit.htm",
//		region:"center"
//	});
//	var win = new Ext.Window({
//			id:BOOKINFO.BOOKINFO_WIN,
//			title:"新增图书或者新增图书库存",
//			width:700,
//			autoHeight:true,
//			modal:true,
//			items:[form]
//	});
//	win.show();
//}
