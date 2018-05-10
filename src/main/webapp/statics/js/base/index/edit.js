/**
 * 编辑-文件管理js
 */

var vm = new Vue({
	el:'#dpLTE',
	data: {
		/*roleList:{},
        selectedList:[],
        nonselectedList:[],*/
		file:{
			fileId: 0,
			fileName: null,
			filePath: null,
			fileLevelId: 0,
			fileLevelName: null,
			status: null
			//roleIdList:[]
		},
		/*level:{
			dataId: 0,
			parentId: 0,
			parentName: null,
			name: null,
			status: 1,
			//layer: 1
		}*/
	},
	methods : {
		levelTree: function() {
			dialogOpen({
				id: 'layerLevelTree',
				title: '选择文件级别',
				url: 'base/offices/level.html?_' + $.now(),
				scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		setForm: function() {
            //this.getRoleList();
			$.SetForm({
				url: '../../sys/offices/infoFile?_' + $.now(),
		    	param: vm.file.fileId,
		    	success: function(data) {
		    		vm.file = data;
		    	}
			});
		},
		acceptClick: function() {
            /*if(isNullOrEmpty(vm.file.fileLevelId)) {
                dialogMsg('请先选择角色！');
                return false;
            }*/
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
