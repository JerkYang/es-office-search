/**
 * 索引检索js
 */

/**
 * 获取url中的参数
 */
$(function () {
	vm.keyword = url('keyword');
	vm.page.keyword = vm.keyword;
});
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)
		return decodeURI(r[2]);
	return null;
}
var vm = new Vue({
	el:'#dpLTE',
	data : {
		keyword : null,
		page: {
			keyword : null,
			count : '',
			curr : 1,
			index : '',
			limit : 10,
			pages : '',
			first : 1
		},
		file:{
			fileId: 0,
			fileName: null,
			filePath: null,
			fileLevelId: 0,
			fileLevelName: null,
			status: null
		},
		fileList:[],
		menu: '../../index.html'
	},
	beforeCreate: function(){
		if(self != top){
			top.location.href = self.location.href;
		}
	},
	/*watch: {
		keyword(val){
			if(null != val) {
				console.log(val)
			}
		}
	},*/
	methods: {
		download : function(data) {
	        console.log(data);
	        var url = "../../sys/offices/onedown";
	        var fileId = data;
	        var form = $("<form></form>").attr("action", url).attr("method", "post");
	        form.append($("<input></input>").attr("type", "hidden").attr("name", "id").attr("value", fileId));
	        form.appendTo('body').submit().remove();
		},
		search: function (event) {
			$.ajax({
	            //几个参数需要注意一下
                type: "post",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: '../../sys/index/search?_' + $.now(),//url
                data: JSON.stringify(vm.page),
        		contentType : 'application/json',
                success: function (result) {
                	vm.page.count = result.total;
    				vm.page.curr = result.pageNo;
    				vm.page.limit = result.pageSize;
    				vm.page.pages = result.totalPages;
                    vm.fileList = result.rows;
                    layui.use([ 'laypage', 'layer' ], function() {
        				var laypage = layui.laypage,
        					layer = layui.layer;
        				//完整功能
        				laypage.render({
        					elem : 'paging',
        					count : vm.page.count,
        					curr : vm.page.curr,
        					index : vm.page.index,
        					first : vm.page.first,
        					limit : vm.page.limit,
        					layout : [ 'count', 'prev', 'page', 'next', 'limit', 'skip' ],
        					jump : function(obj,first) {
        						if(!first) {
        							var curr = obj.curr;
        							vm.page.curr = obj.curr;
        							vm.page.limit = obj.limit;
        							vm.search();
        						}
        					}
        				});
        			});
                },
                error : function() {
                	top.layer.open({
                        title: '系统提示',
                        area: '338px',
                        icon: 3,
                        move: false,
                        anim: -1,
                        isOutAnim: false,
                        content: '注：登录超时,请稍后重新登录.',
                        btn: ['立即退出'],
                        btnAlign: 'c',
                        yes: function(){
                            toUrl('sys/logout');
                        }
                    });
                    setTimeout(function(){
                        toUrl("sys/logout");
                    }, 2000);
                }
            });
		}
	}
});