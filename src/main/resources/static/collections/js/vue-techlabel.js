
var TechLabel = {
    props: ["componentid", "techname"],
    template: `
        <span :id="componentid" class="label-tech">
            <span class="btn label-span" role="textbox"
                v-on:focusout="checkValid($event, componentid)"
                v-on:keydown="keyBehavior"
                contenteditable nowrap v-focus>{{ techname }}</span>
            <input type="button" @click="removeLabel(componentid)" class="btn btn-del btn-delete-label" value="X" />
        </span>
    `,
    methods: {
        'removeLabel': function(id) {
            this.$emit('callOutRemoveLabel', id);
        },
        'checkValid': function(e, id) {
            let sp_ipt = e.target.innerText;

            let labels = this.$parent.labels;
            //
            // 字串是否為空
            //
            let val = sp_ipt.replace("/\s/g", "");
            if(val.length == 0) {
                this.removeLabel(id);
            } else {
                //
                // 是否重複
                //
                let index = labels.findIndex(l => l.name.replace("/\s/g", "") == val && l.id != id);
                if( index != -1) {
                    this.removeLabel(id);
                //
                // 沒有重複存入 input 中
                //
                } else {
                    console.log("are you fire twice?");
                    let index = labels.findIndex(l => l.id == id);
                    this.$parent.labels[index].name = sp_ipt;
                }
            }
        },
        'keyBehavior': function(e) {
            if(e.keyCode === 13) {
                e.preventDefault();
                document.getElementById("btn-add-label").click();
            } else if(e.key === "Escape") {
                e.preventDefault();
                e.target.blur();
            }
        }
    },
    directives: {
        focus: {
            inserted: function(el) {
                el.focus();
            }
        }
    }
};

var techLabels = new Vue({
    el: "#TechLabelVue",
    components: {
        'tech-label': TechLabel
    },
    data: function () {
        return {
            countLabel: 0,
            labels: []
        }
    },
    mounted: function () {
        let strLabels = document.querySelector("input[name=ipt-labels]").value;
        console.log("are you fire every update?");
        if(strLabels != "") {
            strLabels = strLabels.split(",");
            strLabels.forEach((t, idx) => { this.labels.push({id: "tech"+idx, name: t}) });
            this.countLabel = strLabels.length;
        }

    },
    methods: {
        'removeLabelData': function(id) {
            const index = this.labels.findIndex(f => f.id === id)
            this.labels.splice(index,1)
        },
        "appendLabel": function(e) {
            let data = {
                id:"tech" + (this.countLabel++),
                name: ""
            }
            this.labels.push(data);
//            用 custom directives 處理
//            this.$nextTick((data) => {
//                document.getElementById(data.id).firstChild.focus();
//            })
            //this.countLabel +=1;
        }
    }
});