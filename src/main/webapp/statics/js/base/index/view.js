/**
 * 编辑-文件管理js
 */

var vm = new Vue({
	el:'#dpLTE',
	data: {
		file:{
			fileId: 0,
			fileName: null,
			filePath: null,
			fileLevelId: 0,
			fileLevelName: null,
			status: null
		},
	},
	methods : {
		downLoad: function() {
            if (!$('#form').Validform()) {
                return false;
            }
            
            var form = document.getElementById('downLoadForm');
            form.submit();
            /*$.SetForm({
		    	url: '../../sys/index/download?_' + $.now(),
		    	param: vm.file,
		    	success: function(data) {
		    		vm.file = data;
		    	}
		    });*/
		},
		setForm: function() {
			$.SetForm({
				url: '../../sys/index/infoIndex?_' + $.now(),
		    	param: vm.file.fileId,
		    	success: function(data) {
		    		vm.file = data;
		    	}
			});
		},
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
		    $.ConfirmForm({
		    	url: '../../sys/offices/update?_' + $.now(),
		    	param: vm.file,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
