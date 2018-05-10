/**
 * 新增-拓展词管理js
 */

var vm = new Vue({
	el:'#dpLTE',
	data: {
		ikword:{
			ikWordName: null,
			status: 1
		}
	},
	methods : {
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
		    $.SaveForm({
		    	url: '../../sys/ikword/save?_' + $.now(),
		    	param: vm.ikword,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	},
	created : function() {
	}
})
