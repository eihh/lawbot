const mobile = {
    namespaced: true,
    state: {
        name:"",
    },
    getters: {},
    mutations: {
        setname(state,e){
            console.log(e);
            state.name=e.name
        }
    },
    actions: {}
}  
export default mobile;
