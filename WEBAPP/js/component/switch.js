Vue.component('c-switch', {
    template:   "<span>"+
    "<div data-uk-button-radio class='uk-button-group'>"+
    "      <a href='#' v-for='(value,key) in switchData' " +
    "           class='uk-button' :class='[bindData==value?\"uk-active\":\"\",theme]'"+
    "           @click='clickOn(key)' >{{key}}</a>"+
    "</div>" +
    "</span>",
    props: ['bind','theme','switch'],
    computed: {
        switchData: function() {
            if (this.switch instanceof Object) {
                return this.switch;
            } else {
                return eval("k001 = "+this.switch)
            }
        },
        bindData : function() {
            return eval("this.$parent."+this.bind);
        }
    },
    methods: {
        clickOn: function(index){
            var result = this.switchData[index];
            if((typeof result) == "string"){
                result = "\""+result+"\"";
            }
            eval("this.$parent."+this.bind+"="+result);
        }
    }
})