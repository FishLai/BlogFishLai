
var counters = document.getElementsByClassName("counter");
for(let i=0; i<counters.length; i++){
    counters[i].addEventListener("mouseenter", function(e) {
        descriptionVue.isActive = true;
    }, false);

    counters[i].addEventListener("mouseleave", function(e) {
        descriptionVue.isActive = false;
    }, false);
}

var descriptionVue = new Vue({
    el:"#coll-intro",
    data: {
        isActive: false
    }
})