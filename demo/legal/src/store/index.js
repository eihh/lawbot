import Vue from 'vue'
import Vuex from 'vuex'
import mobile from "./mobile";

Vue.use(Vuex)

export default new Vuex.Store({
  namespaced: true,
  state: {
    name: {},

  },
  getters: {
  },
  mutations: {
    storageLoginedData(state, e) {
      state.logins = e
    },
    as(state, e) {
      state.a = e

    }
  },
  actions: {
  },

  modules: {
    mobile
  }


})
