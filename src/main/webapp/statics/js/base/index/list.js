/**
 * 索引管理js
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
		url : '../../sys/index/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.indexname = vm.keyword;
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
		keyword : null,
	},
	file:{
		fileId: 0,
		fileName: null,
		filePath: null,
		fileLevelId: 0,
		fileLevelName: null,
		status: null
	},
	methods : {
		load : function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		view : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if (checkedRow(ck)) {
				dialogOpen({
					title : '编辑文件',
					url : 'base/index/view.html?_' + $.now(),
					width : '500px',
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
		remove : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.fileId;
				});
				$.RemoveForm({
					url : '../../sys/index/remove?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
						/*window.setTimeout(function() {
							
						}, 3000)();*/
						//document.location.reload();
					}
				});
			}
		}
	}
})