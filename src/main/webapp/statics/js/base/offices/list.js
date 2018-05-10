/**
 * 用户管理js
 */

$(function() {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {
			height : $(window).height() - 56
		});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url : '../../sys/offices/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.fileName = vm.keyword;
			return params;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "fileId",
			title : "编号",
			align: 'center',
			valign: 'middle',
			width : "50px"
		}, {
			field : "fileName",
			title : "文件名",
			align: 'center',
			valign: 'middle',
			width : "300px"
		}, {
			field : "userNameCreate",
			title : "创建人",
			align: 'center',
			valign: 'middle',
			width : "100px"
		}, {
			field : "userNameUpdate",
			title : "更新人",
			align: 'center',
			valign: 'middle',
			width : "100px"
		}, {
			field : "fileLevelName",
			title : "文件级别",
			align: 'center',
			valign: 'middle',
			width : "60px",
			formatter : function(value,row,index) {
				return '<span class="label label-success">' + value + '</span>';
			}
		}, {
			field : "status",
			title : "状态",
			align: 'center',
			valign: 'middle',
			width : "60px",
			formatter : function(value, row, index) {
				if (value == '0') {
					return '<span class="label label-danger">禁用</span>';
				} else if (value == '1') {
					return '<span class="label label-success">正常</span>';
				}
			}
		}, {
			field : "gmtCreate",
			title : "创建时间",
			align: 'center',
			valign: 'middle',
			width : "180px"
		}, {
			field : "gmtModified",
			title : "更新时间",
			align: 'center',
			valign: 'middle'
		} ]
	})
}

var vm = new Vue({
	el : '#dpLTE',
	data : {
		keyword : null
	},
	methods : {
		load : function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save : function() {
			dialogOpen({
				title : '上传文件',
				url : 'base/offices/add.html?_' + $.now(),
				width : '700px',
				height : '520px',
				btn: ['完成', '返回'],
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].dialogClose();
					//top.frames[iframeId].vm.acceptClick();
					// 刷新父窗口对象
					document.location.reload();
				},
			});
		},
		/*save : function() {
			dialogOpen({
				title : '新增用户',
				url : 'base/user/add.html?_' + $.now(),
				width : '620px',
				height : '510px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},*/
		edit : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if (checkedRow(ck)) {
				dialogOpen({
					title : '编辑文件',
					url : 'base/offices/edit.html?_' + $.now(),
					width : '700px',
					height : '315px',
					scroll : true,
					success : function(iframeId) {
						top.frames[iframeId].vm.file.fileId = ck[0].fileId;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		},
		download : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.fileId;
				});
				var tempInput = document.createElement("input");
				tempInput.type = "hidden";
				tempInput.name = "ids";
				tempInput.value = ids;
				var form = document.getElementById('downLoadForm');
				form.appendChild(tempInput);
				form.submit();
				/*$.ConfirmForm({
					msg : '您是否要启用所选任务吗？',
					url : '../../sys/offices/download?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});*/
			}
		},
		remove : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.fileId;
				});
				$.RemoveForm({
					url : '../../sys/offices/remove?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});
			}
		},
		disable : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.fileId;
				});
				$.ConfirmForm({
					msg : '您是否要禁用所选文件吗？',
					url : '../../sys/offices/disable?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});
			}
		},
		enable : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.fileId;
				});
				$.ConfirmForm({
					msg : '您是否要启用所选文件吗？',
					url : '../../sys/offices/enable?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});
			}
		},
	}
})