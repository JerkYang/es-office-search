/**
 * 编辑-拓展词管理js
 */
var doublebox = null;

var vm = new Vue({
	el:'#dpLTE',
	data: {
		ikword:{
			ikWordName: null,
			status: null
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../sys/ikword/infoIKWord?_' + $.now(),
		    	param: vm.ikword.ikWordId,
		    	success: function(data) {
		    		vm.ikword = data;
		    	}
			});
		},
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
		    $.ConfirmForm({
		    	url: '../../sys/ikword/update?_' + $.now(),
		    	param: vm.ikword,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
