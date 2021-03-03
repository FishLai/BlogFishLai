
var el = document.getElementsByClassName("counter");

for(i=0; i<el.length; i++) {
    console.log(el[i]);
    new Vue({
        el:el[i],
        data: {
            seen:true
        }
    })
}