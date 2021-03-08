var imgUploadVue = new Vue({
    el: "#imgVue",
    data: {
        isImgUploaded: false
    },
    methods: {
        uploadImg: function(e) {
            let field = document.getElementById("f-upload");
            field.click();
        },
        //上傳照片後處理預覽
        previewCover: function(e) {
            if(e.target.files && e.target.files[0]) {
                imgUploadVue.isImgUploaded = true;
                let reader = new FileReader();
                reader.addEventListener('load', function(e){
                    let img = document.getElementById("previewCover");
                    img.setAttribute("src", e.target.result);
                    img.addEventListener("click", imgUploadVue.uploadImg, {useCapture:false, once:true});
                }, false);

                reader.readAsDataURL(e.target.files[0]);
            }
        }
    }
});