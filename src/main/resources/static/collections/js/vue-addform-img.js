console.log("js loaded");

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
        loadImg: function(e) {
            if(e.target.files && e.target.files[0]) {
                imgUploadVue.isImgUploaded = true;
                let reader = new FileReader();
                reader.addEventListener('load', function(e){
                    let img = document.getElementById("previewCover");
                    img.setAttribute("src", e.target.result);
                }, false);

                reader.readAsDataURL(e.target.files[0]);
            }
        }
    }
});