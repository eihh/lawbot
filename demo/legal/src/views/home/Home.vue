<template>
  <div class="homes">
    <div style="display: flex; background: #dbe7ee">
      <div style="background: #344353">
        <!-- <el-radio-group v-model="isCollapse">
          <el-radio-button :label="false">展开</el-radio-button>
          <el-radio-button :label="true">收起</el-radio-button>
        </el-radio-group> -->
        <div
          class="shouq"
          style="
            background: #344353;
            width: 220px;
            position: relative;
            height: 72px;
          "
          v-if="!isCollapse"
        >
          <img src="@/assets/coms/tab.png" width="100%" height="100%" />
          <img
            src="@/assets/coms/zs.png"
            width="30px"
            height="20px"
            style="position: absolute; right: 8%; top: 35%; cursor: pointer"
            @click="hiddends(1)"
          />
        </div>
        <div
          class="shouq"
          style="background: #344353; width: 220px; cursor: pointer"
          v-if="isCollapse"
        >
          <img
            src="@/assets/coms/zs.png"
            width="30px"
            height="20px"
            style="margin-left: 15px; margin-top: 8px"
            @click="hiddends(2)"
          />
        </div>
        <el-menu
          class="el-menu-vertical-demo"
          :collapse="isCollapse"
          background-color="#344353"
          text-color="#ffffff"
          :unique-opened="true"
          :collapse-transition="false"
          router
        >
          <!-- <el-submenu index="1" v-for="(item, index) in 2" :key="index">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-submenu> -->

          <el-submenu index="0+'cc'">
            <template slot="title">
              <div @click="back_home('/homeview')">
                <i class="el-icon-s-home" style="font-size: 20px"></i>
                <span slot="title">首页</span>
              </div>
            </template>
          </el-submenu>
          <el-submenu
            :index="index + ''"
            v-for="(item, index) in menu"
            :key="index"
          >
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>{{ item.pname }}</span>
            </template>

            <el-menu-item
              :index="items.path"
              v-for="(items, indexs) in item.son"
              :key="indexs"
              >{{ items.name }}</el-menu-item
            >
          </el-submenu>
        </el-menu>
      </div>

      <div style="flex: 1">
        <!-- //** 上面栏盒子*/ -->
        <div
          style="
            flex: 1;
            height: 72.5px;
            line-height: 72.5px;
            display: flex;
            background: white;
          "
        >
          <div
            style="
              width: 60%;
              text-align: right;
              color: #8291a9;
              font-family: cursive;
              font-weight: 500px;
              font-size: 25px;
              overflow: hidden;
              white-space: nowrap;
              text-overflow: ellipsis;
            "
          >
            <span style="color: #cbdcf7">A</span>
            <span style="color: #b7e1e4">I</span>
            <span style="color: #b7e1e4">法</span>
            <span style="color: #b7e1e4">律</span>
            <span style="color: #e9d4ff">文</span>
            <span style="color: #ffe0e6">书</span>
            <span style="color: #ffe0e6">系</span>
            <span style="color: #ffe0e6">统</span>
          </div>
          <div
            style="
              width: 40%;
              display: flex;
              text-align: center;
              margin: 0 auto;
            "
          >
            <div
              style="
                flex: 1;
                font-family: cursive;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                text-align: left;
              "
            >
              <span style="font-size: 18px"
                >&nbsp;--------&nbsp;<span
                  style="display: inline-block; margin-top: 8px; color: #6d41a1"
                  >欢迎你</span
                >
              </span>
              <!-- <el-input
                placeholder="请输入内容"
                prefix-icon="el-icon-search"
                v-model="input2"
              >
              </el-input> -->
            </div>
            <div style="width: 5%">
              <div
                style="
                  border-left: 1px solid;
                  height: 30px;
                  width: 1px;
                  margin-top: 20px;
                  margin-left: 100%;
                  color: #bdc5d2;
                "
              ></div>
            </div>
            <div style="width: 10%">
              <img src="@/assets/coms/rentou.png" width="20px" height="20px" />
            </div>

            <div
              style="
                width: 15%;
                font-family: KaiTi;
                font-size: 20px;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
              "
            >
              {{ name }}
            </div>
            <div style="width: 10%">
              <img src="@/assets/coms/zhong.png" width="20px" height="20px" />
            </div>
            <div style="width: 5%">
              <div
                style="
                  border-left: 1px solid;
                  height: 30px;
                  width: 1px;
                  margin-top: 20px;
                  color: #bdc5d2;
                "
              ></div>
            </div>
            <div style="width: 5%; margin-right: 10px" @click="outlogin">
              <img
                src="@/assets/coms/guanji.png"
                width="30px"
                height="30px"
                style="margin-top: 20px; cursor: pointer"
              />
            </div>
          </div>
        </div>
        <!-- //主要内容盒子 -->
        <div style="background: #dbe7ee">
          <!-- <keep-alive> -->
          <router-view />
          <!-- </keep-alive> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations, mapAction } from "vuex";
export default {
  name: "Home_H",
  data() {
    return {
      input2: "",
      arr_new: [],
      session_data: "",
      isCollapse: false,
      menu: [],
      employeeid: "",
    };
  },
  created() {},
  computed: {
    ...mapState("mobile", ["name"]),
  },
  async mounted() {
    const menu = [
      {
        pname: "用户管理",
        path: "/stu",
        son: [{ name: "用户列表", path: "/user" }],
      },
      {
        pname: "土地管理",
        path: "/grade",
        son: [{ name: "土地列表", path: "/grade" }],
      },
      {
        pname: "视图管理",
        path: "/tu",
        son: [{ name: "图示列表", path: "/tu" }],
      },
      {
        pname: "专家管理",
        path: "/zj",
        son: [{ name: "专家列表", path: "/zj" }],
      },
    ];
    const menu1 = [
      {
        pname: "法律文书",
        path: "/wenshu",
        son: [{ name: "法律文书摘要", path: "/wenshu" }],
      },
      {
        pname: "法律预测",
        path: "/yuce",
        son: [{ name: "法律预测报告", path: "/yuce" }],
      },
      {
        pname: "法律问答",
        path: "/wenda",
        son: [{ name: "问题答疑", path: "/wenda" }],
      },
    ];
    if (sessionStorage.getItem("user") == 1999) {
      this.menu = menu;
    } else {
      this.menu = menu1;
    }
    this.mu();
  },
  methods: {
    mu() {},
    outlogin() {
      this.$router.push("/");
    },
    into_view(e) {
      this.$router.push(e);
    },
    back_home(e) {
      if (this.$route.path != "/homeview") {
        this.$router.push(e);
      }
    },
  },
};
</script>

<style>
.homes .el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 220px;
  /* height: 100%; */
  height: 100vh;
}

.homes .el-menu--collapse {
  height: 100%;
}
.homes .el-menu {
  border: none !important;
}

.homes
  /* ul */
  .el-menu-vertical-demo.el-menu
  :nth-child(1)
  .el-submenu__title
  .el-submenu__icon-arrow.el-icon-arrow-down {
  display: none;
}

.el-submenu__title.is-active {
  background-color: #1c88cf !important;
  color: #fff !important;
}
</style>