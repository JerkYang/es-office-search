/**
 * 编辑-数据权限管理js
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
		setForm: function() {
			$.SetForm({
				url: '../../sys/data/info?_' + $.now(),
		    	param: vm.db.dataId,
		    	success: function(data) {
		    		vm.db = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../sys/data/update?_' + $.now(),
		    	param: vm.db,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
