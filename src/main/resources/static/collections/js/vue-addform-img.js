var imgUploadVue = new Vue({
    el: "#imgVue",
    data: {
        isImgUploaded: false,
        uploadfailed: false,
        msg: ''
    },
    mounted: function() {
        let base64Img = document.querySelector("input[name=imgTmp]").value;
        if(base64Img != "/null") {
            this.isImgUploaded = true;
            this.uploadfailed = false;
        }
    },
    methods: {
        uploadImg: function(e) {
            let field = document.getElementById("f-upload");
            field.click();
        },
        //上傳照片後處理預覽
        previewCover: function(e) {
            let postTo = e.target.getAttribute("post-to");
            if(e.target.files && e.target.files[0]) {
                let form = new FormData();
                form.append("file", e.target.files[0]);
                axios.post(postTo, form, {headers: {"Content-Type": "multipart/form-data"}})
                    .then(res => {
                    	let status = res.data.status == "true";
                        //
                        // 上傳成功便預覽畫面
                        if(status) {
                            imgUploadVue.isImgUploaded = true;
                            imgUploadVue.uploadfailed = false;
                            //
                            // 定義讀取事件發生時，替換預覽元素
                            let reader = new FileReader();
                            reader.addEventListener('load', function(e){
                                let img = document.getElementById("previewCover");
                                img.setAttribute("src", e.target.result);
                                document.querySelector("input[name=imgTmp]").value = e.target.result;
                                img.addEventListener("click", imgUploadVue.uploadImg, {capture:false});
                            }, false);

                            //
                            // 讀取檔案
                            reader.readAsDataURL(e.target.files[0]);
                        } else {
                            document.querySelector("input[name=imgTmp").value = "";
                        	imgUploadVue.isImgUploaded = false;
                        	imgUploadVue.uploadfailed = true;
                        }

                        return res.data.message;
                    }).then( msg => {
                    	imgUploadVue.msg = '';
                    	if(imgUploadVue.uploadfailed) {
                    		imgUploadVue.msg = msg;
                    	}
                    });
                
            }
        }
    }
});