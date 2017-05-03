Vue.component('c-switch', {
    template: "<span> \
                    <div data-uk-button-radio class='uk-button-group'> \
                          <button type='button' v-for='(value,key) in switchData' \
                               class='uk-button' :class='[bindData==value?\"uk-active\":\"\",theme]' \
                               @click='clickOn(value)'>{{key}}</input> \
                    </div> \
               </span>",
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
        clickOn: function(value){
            if ((typeof value) == "string") {
                value = "\"" + value + "\"";
            }
            eval("this.$parent." + this.bind + "=" + value);
            this.$emit('click')
        }
    }
})