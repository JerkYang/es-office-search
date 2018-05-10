/**
 * 数据权限js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		TreeGrid.table.resetHeight({height: $(window).height()-100});
	});
}

function getGrid() {
	var colunms = TreeGrid.initColumn();
    var table = new TreeTable(TreeGrid.id, '../../sys/data/list?_' + $.now(), colunms);
    table.setExpandColumn(2);
    table.setIdField("dataId");
    table.setCodeField("dataId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.setHeight($(window).height()-100);
    table.init();
    TreeGrid.table = table;
}

var vm = new Vue({
	el:'#dpLTE',
	methods : {
		load: function() {
			TreeGrid.table.refresh();
		},
		save: function() {
			dialogOpen({
				title: '新增数据权限',
				url: 'base/data/add.html?_' + $.now(),
				width: '500px',
				height: '315px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function() {
			var ck = TreeGrid.table.getSelectedRow();
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑数据权限',
					url: 'base/data/edit.html?_' + $.now(),
					width: '500px',
					height: '315px',
					success: function(iframeId){
						top.frames[iframeId].vm.db.dataId = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		},
		remove: function() {
			var ck = TreeGrid.table.getSelectedRow(), ids = [];
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: '../../sys/data/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		}
	}
})

var TreeGrid = {
    id: "dataGrid",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TreeGrid.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '编号', field: 'dataId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '数据权限编码', field: 'code', align: 'center', valign: 'middle', width: '200px'},
        {title: '上级数据权限', field: 'parentName', align: 'center', valign: 'middle', width: '300px'},
        {title: '可用', field: 'status', align: 'center', valign: 'middle', width: '60px', formatter: function(item, index){
        	if(item.status === 0){
                return '<i class="fa fa-toggle-off"></i>';
            }
            if(item.status === 1){
                return '<i class="fa fa-toggle-on"></i>';
            }
        }},
        {title: '排序', field: 'orderNum', align: 'center', valign: 'middle', width: '80px'}]
    return columns;
};
