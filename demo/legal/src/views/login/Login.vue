<template>
  <div>
    <main class="container">
      <div class="loginback">
        <img src="@/assets/login/xs.png" width="100%" height="100%" />
      </div>

      <div class="operator">
        <div class="userlogin"></div>
        <div class="user">
          <div class="user-left">
            <img class="useruser" src="@/assets/login/user.png" alt="" />
            <img class="userl" src="@/assets/login/l.png" alt="" />
          </div>
          <div class="user-right">
            <el-input
              v-model="ruleForm.username"
              placeholder="请输入账号"
            ></el-input>
          </div>
        </div>
        <div class="user">
          <div class="user-left">
            <img class="useruser" src="@/assets/login/password.png" alt="" />
            <img class="userl" src="@/assets/login/l.png" alt="" />
          </div>
          <div class="user-right">
            <el-input
              placeholder="请输入密码"
              v-model="ruleForm.password"
              show-password
            ></el-input>
          </div>
        </div>
        <div class="user">
          <div class="user-left">
            <img class="useruser" src="@/assets/login/code.png" alt="" />
            <img class="userl" src="@/assets/login/l.png" alt="" />
          </div>
          <div class="user-code-right">
            <el-input
              v-model="ruleForm.auth"
              placeholder="请输入验证码"
              @keydown.enter.native="login"
            ></el-input>
          </div>
          <div class="user-code" @click="changeCode">
            <login-auth :identifyCode="identifyCode"></login-auth>
          </div>
        </div>
        <div class="user-login" @click="login">
          <span><strong>登录</strong></span>
        </div>
      </div>
      <div
        v-if="hihhens == true"
        style="
          width: 550px;
          height: 450px;
          background-color: #ffffff;
          position: absolute;
          left: 20%;
          top: 28%;
        "
        class="lis"
      >
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="150px"
          style="margin-top: 20%"
        >
          <el-form-item label="旧密码:" prop="a">
            <div class="form-item">
              <div style="width: 10px"></div>
              <div class="item">
                <i class="iconfont icon-mima"></i>
              </div>
              <el-input
                type="password"
                show-password
                v-model="form.a"
              ></el-input>
            </div>
          </el-form-item>
          <el-form-item label="新密码:" label-width="150px" prop="b">
            <div class="form-item">
              <div style="width: 10px"></div>
              <div class="item">
                <i class="iconfont icon-mima"></i>
              </div>
              <el-input
                type="password"
                show-password
                v-model="form.b"
              ></el-input>
            </div>
          </el-form-item>
          <el-form-item label="确认密码:" label-width="150px" prop="c">
            <div class="form-item">
              <div style="width: 10px"></div>
              <div class="item">
                <i class="iconfont icon-mima"></i>
              </div>
              <el-input
                type="password"
                show-password
                v-model="form.c"
              ></el-input>
            </div>
          </el-form-item>
        </el-form>

        <el-button
          @click="fanhui"
          type="primary"
          style="
            margin-left: 52%;
            margin-top: 80px;
            background: none;
            color: #3981ee;
          "
          >返回登录</el-button
        >
        <el-button type="primary" @click="loginchange">确认</el-button>
      </div>
    </main>
  </div>
</template>






<script>
import { jwtDecode } from "jwt-decode";
import LoginAuth from "@/components/login/Login_Auth.vue"; //**引入验证码组件**
import http from "@/axios/awaitapi.js";
import api from "@/axios/awaitapi.js";

export default {
  name: "LoginS",
  data() {
    return {
      mes: [],
      res1: [],
      hihhens: false,

      form: {
        a: "",
        b: "",
        c: "",
      },
      usernames: "",

      code: "",
      identifyCode: "",
      identifyCodes: "abcdefghijklmnopqrstuvwxyz",

      ruleForm: {
        username: "",
        password: "",
        auth: "",
      },
    };
  },
  mounted() {
    this.getIdentifyingCode();
  },

  methods: {
    fanhui() {
      this.hihhens = false;
    },

    async loginchange() {
      let temp = true;
      const h = this.$createElement;
      this.$refs.form.validate((valid) => {
        if (!valid) {
          temp = false;
          this.$notify({
            title: "提示",
            message: h("i", { style: "color: teal" }, "请检查错误!"),
          });
        }
      });
    },

    changeCode: function () {
      this.getIdentifyingCode();
    },
    getIdentifyingCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode +=
          this.identifyCodes[this.randomNum(0, this.identifyCodes.length)];
      }
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },

    async login() {
      const h = this.$createElement;

      if (this.ruleForm.username === "" || this.ruleForm.password === "") {
        this.$notify({
          title: "",
          message: h(
            "p",
            { style: "color:#333" },
            "用户名或密码不能为空，请重新输入！"
          ),
        });
        return;
      }

      if (
        this.ruleForm.auth.toLowerCase() !== this.identifyCode.toLowerCase()
      ) {
        this.$notify({
          title: "",
          message: h("p", { style: "color:#333" }, "验证码错误，请重新输入！"),
        });
        this.getIdentifyingCode();
        return;
      }

      console.log(this.ruleForm);

      // 发送登录请求
      this.$router.push({ path: "/home" });
      // try {
      //   this.request.post("/login", this.ruleForm).then(res => {
      //     //打印返回结果
      //     console.log(res);
      //     //状态码
      //     const code = res.code;
      //     //状态码为成功(200)
      //     if (code === 200) {

      //       this.$router.push({path: "/home"});
      //       this.$message({type:'success', message: res.msg});
      //     }
      //     if (code === 500) {
      //       this.$message({type: 'error', message: res.msg});
      //     }

      //   })

      //   /*const response = await test.post("/login", {
      //     username: this.ruleForm.username,
      //     password: this.ruleForm.password,
      //   });

      //   if (response.data.success) {
      //     this.$router.push("/home");
      //   } else {
      //     this.$notify({
      //       title: "登录失败",
      //       message: response.data.message || "用户名或密码错误",
      //     });
      //     this.getIdentifyingCode();
      //   }*/
      // } catch (error) {
      //   this.$notify({
      //     title: "错误",
      //     message: "请求失败：" + error.message,
      //   });
      //   this.getIdentifyingCode();
      // }
    },
  },
  components: {
    LoginAuth: LoginAuth,
  },
};
</script>



















<style scoped>
#app {
  margin: 0;
}
.container {
  width: 99vw;
  /* height: 100vh; */
  position: relative;
  /* background: #111c24 url("@/assets/login/back.jpg") no-repeat center; */
}

.operator {
  width: 5px;
  height: 5px;
  background: #ffffff;
  position: absolute;
  top: 25%;
  bottom: 0;
  left: 39%;
  right: 0;
  border-radius: 2%;
  /* background: url("@/assets/login/operatorback.png") no-repeat center; */
}

.logo {
  width: 695px;
  height: 75px;
  position: absolute;
  top: 8%;
  left: 0;
  right: 0;
  margin-left: 50px;
  /* background: url("@/assets/login/logo.png") no-repeat center; */
}

.userlogin {
  width: 128px;
  height: 100px;
  margin-top: 50px;
  margin-left: 60%;
  line-height: 100px;
  /* background: url("@/assets/login/userlogin.png") no-repeat center; */
}

.user {
  width: 400px;
  height: 50px;
  line-height: 50px;
  margin-left: 45%;
  margin-top: 5px;
  border-radius: 3%;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.user-left {
  width: 40px;

  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

.user-left .useruser {
  width: 21px;
  height: 19px;
  margin-top: 15px !important;
}

.user-left .userl {
  width: 1px;
  height: 24px;
  margin-top: 13px !important;
}

.user-right {
  width: 355px;
}

.user-code {
  width: 100px;
  margin-top: 8px !important;
}

.user-code-right {
  width: 250px;
}

.user-login {
  width: 311px;
  height: 50px;
  line-height: 50px;
  margin-left: 50px !important;
  margin-top: 30px !important;
  cursor: pointer;
  background-color: #5c82ff;
  border: 3px solid #5c82ff;
  border-radius: 5px;
  text-align: center;
  color: white;
  transition: 0.3s ease-in;
}

.user-login:hover {
  color: #5c82ff;
  background-color: white;
}

.lis >>> .el-input {
  width: 300px;
  border-radius: 0;
}

.lis >>> .el-input .el-input__inner {
  border-radius: 0;
  border-top-right-radius: 5px !important;
  border-bottom-right-radius: 5px !important;

  border-left: none;
}

.lis >>> .el-form .el-form-item.is-required .el-form-item__label {
  padding: 0 !important;
  outline: none;
}

.container >>> .form-item {
  display: flex;
}

.container >>> .form-item .item {
  width: 30px;
  border: 1px solid #dcdfe6;
  height: 40px;
  text-align: center;
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
  box-sizing: border-box;
}

.container >>> .form-item .el-input__inner {
  display: block;
}
.loginback {
  width: 100%;

  height: 99.5vh;
}
</style>