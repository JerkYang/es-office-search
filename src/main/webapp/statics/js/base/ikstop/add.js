/**
 * 新增-用户管理js
 */
var doublebox = null;

var vm = new Vue({
	el:'#dpLTE',
	data: {
		ikstop:{
			ikStopName: null,
			status: 1
		}
	},
	methods : {
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
		    $.SaveForm({
		    	url: '../../sys/ikstop/save?_' + $.now(),
		    	param: vm.ikstop,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	},
	created : function() {
	}
})
