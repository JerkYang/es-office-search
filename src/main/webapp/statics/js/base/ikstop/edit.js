/**
 * 编辑-停用词管理js
 */

var vm = new Vue({
	el:'#dpLTE',
	data: {
		ikstop:{
			ikStopName: null,
			status: null
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../sys/ikstop/infoIKStop?_' + $.now(),
		    	param: vm.ikstop.ikStopId,
		    	success: function(data) {
		    		vm.ikstop = data;
		    	}
			});
		},
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
		    $.ConfirmForm({
		    	url: '../../sys/ikstop/update?_' + $.now(),
		    	param: vm.ikstop,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
