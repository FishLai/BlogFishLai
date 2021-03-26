
var counters = document.getElementsByClassName("container-coll");
for(let i=0; i<counters.length; i++){
    // 事件，滑鼠進入展品容器
    //
    counters[i].addEventListener("mouseenter", function(e) {
        // 取得釘選展品號碼且去除釘轉
        //
        let stuckNo = descriptionVue.stuckOn.get("no");
        document.querySelector("div[coll-no='" + stuckNo + "']").classList.remove("stuck");

        // 替換展品說明資料
        //
        let currentNo = this.getAttribute("coll-no");
        descriptionVue.current = descriptionVue.counters.get(currentNo);
    }, false);

    // 事件，滑鼠移出展品容器
    counters[i].addEventListener("mouseleave", function(e) {
        // 復原釘顯資料
        //
        descriptionVue.current = descriptionVue.stuckOn;

        // 復原釘選展品
        //
        let stuckNo = descriptionVue.stuckOn.get("no");
        document.querySelector("div[coll-no='" + stuckNo + "']").classList.add("stuck");
    }, false);

    counters[i].addEventListener("click", function(e){
        let collNo = this.getAttribute("coll-no");
        descriptionVue.stuckOn = descriptionVue.counters.get(collNo);
        descriptionVue.current = descriptionVue.stuckOn;
    }, false);
}

var descriptionVue = new Vue({
    el:"#container-description",
    beforeMount: function() {
        //
        // 記錄下頁面上展示品資料
        // 並使用展品名稱作為key
        //
        let colls = document.getElementsByClassName("container-coll");
        for(let coll of colls) {
            let collData = new Map();
            collData.set("no", coll.getAttribute("coll-no"));
            collData.set("name", coll.getAttribute("coll-name"));
            collData.set("devDate", {"startDate": coll.getAttribute("start-date"), "stopDate": coll.getAttribute("stop-date")});
            collData.set("abstract", coll.getAttribute("coll-abstract"));
            if(coll.getAttribute("list-tech") != null) {
                collData.set("tech", coll.getAttribute("list-tech").split(","));
            } else {
                collData.set("tech", "");
            }
            this.counters.set(collData.get("no"), collData);
        };

        let no = this.counters.entries().next().value[0];
        this.stuckOn = this.counters.get(no);
        this.current = this.stuckOn;
        document.querySelector("div[coll-no='" + no + "']").classList.add("stuck");

    },
    data: function () {
        return {
            isActive: false,
            counters: new Map(),
            current: null,
            stuckOn: null,
        }
    },
    methods: {
        "showCounter": function(name) {
            counter = document.querySelector("div[coll-name=" + name +"]");

        }
    }
})