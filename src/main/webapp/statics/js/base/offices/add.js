/**
 * 新增-文件管理js
 */

var vm = new Vue({
	el:'#dpLTE',
	data: {
		/*roleList:{},*/
		file:{
			fileId: 0,
			fileName: null,
			filePath: null,
			fileLevelId: 0,
			fileLevelName: null,
			status: 1
			//roleIdList:[]
		}

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
		acceptClick: function() {
            /*var roles = doublebox.getSelectedOptions();
            if(isNullOrEmpty(roles)) {
                dialogMsg('请先选择角色！');
                return false;
            }*/
            if (!$('#form').Validform()) {
                return false;
            }
            //vm.user.roleIdList = [];
           /* $.each(roles.split(','), function(idx, item){
                vm.user.roleIdList.push(parseInt(item));
            });*/
		    $.SaveForm({
		    	url: '../../sys/offices/save?_' + $.now(),
		    	param: vm.file,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	},
	created : function() {
	}
})
