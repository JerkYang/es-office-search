/**
 * 拓展词管理js
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
		url : '../../sys/ikword/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.ikWordName = vm.keyword;
			return params;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "ikWordId",
			title : "编号",
			align: 'center',
			valign: 'middle',
			width : "50px"
		}, {
			field : "ikWordName",
			title : "拓展词",
			align: 'center',
			valign: 'middle',
			width : "200px"
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
				title : '新增拓展词',
				url : 'base/ikword/add.html?_' + $.now(),
				width : '400px',
				height : '210px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections');
			if (checkedRow(ck)) {
				dialogOpen({
					title : '编辑拓展词',
					url : 'base/ikword/edit.html?_' + $.now(),
					width : '400px',
					height : '210px',
					scroll : true,
					success : function(iframeId) {
						top.frames[iframeId].vm.ikword.ikWordId = ck[0].ikWordId;
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
					ids[idx] = item.ikWordId;
				});
				$.RemoveForm({
					url : '../../sys/ikword/remove?_' + $.now(),
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
					ids[idx] = item.ikWordId;
				});
				$.ConfirmForm({
					msg : '您是否要禁用所选拓展词吗？',
					url : '../../sys/ikword/disable?_' + $.now(),
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
					ids[idx] = item.ikWordId;
				});
				$.ConfirmForm({
					msg : '您是否要启用所选拓展词吗？',
					url : '../../sys/ikword/enable?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});
			}
		},
	}
})