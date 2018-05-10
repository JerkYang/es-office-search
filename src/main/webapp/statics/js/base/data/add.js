/**
 * 新增-数据权限管理js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		db: {
			parentId: 0,
			parentName: null,
			orderNum: 0,
			status: 1,
			layer: 1
		}
	},
	methods : {
		dbTree: function() {
			dialogOpen({
				id: 'layerDBTree',
				title: '选择数据权限',
		        url: 'base/data/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../sys/data/save?_' + $.now(),
		    	param: vm.db,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
