
var TechLabel = {
    props: ["componentid", "techname"],
    template: `
        <span :id="componentid" class="label-tech">
            <input type="text" :value="techname" placeholder="type skill" />
            <input type="button" @click="removeLabel(componentid)" class="btn-del btn-delete-label" value="X" />
        </span>
    `,
    methods: {
        'removeLabel': function(id) {
            console.log("call parent")
            this.$emit('callOutRemoveLabel', id);
        }
    }
};
console.log(TechLabel);
//Vue.component("techlabel", TechLabel);
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
        let labels = document.getElementsByTagName("tech-label");
        for(let i=0; i<labels.length; i++) {
            this.labels.push({
                id:labels[i].getAttribute("componentid"),
                name: labels[i].getAttribute("techname")
            });
        }
        this.countLabel = this.labels.length;
    },
    methods: {
        'removeLabelData': function(id) {
            console.log("removing" +id);
            const index = this.labels.findIndex(f => f.id === id)
            this.labels.splice(index,1)
        },
        "appendLabel": function(e) {
            //e.target.insertBefore()
            let data = {
                id:"tech" + (this.countLabel++),
                name: ""
            }
            this.labels.push(data);
            //this.countLabel +=1;
        }
    }
});