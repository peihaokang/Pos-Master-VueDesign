<template>
  <div class="hello">
    <form action="" id="login">
      <input type="text" placeholder="昵称" v-model="formMess.account">
      <input type="password" placeholder="密码" v-model="formMess.password">
    </form>
    <a-button type="primary"><router-link to="/login">登录</router-link></a-button>
    <a-button type="primary"><router-link to="/home">首页</router-link></a-button>
    <button @click="test1()">测试1(need)</button>
    <button @click="test2()">测试2(need)</button>
    <button @click="needAdminRole()">needAdminRole(need)</button>
    <button @click="needTestRole()">needTestRole(need)</button>
    <button @click="logout()">注销登录</button>
    <hr>
    <div style="color:red;">{{msg}}</div>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      msg: "Spring Boot和Vue整合JWT认证测试",
      formMess: {
        "account": "",
        "password": ""
      }
    };
  },
  methods: {
    login() {

      this.$http
          .post("/login", {
            username: this.formMess["account"],
            password: this.formMess["password"]
          })
          .then(res => {
            // 登录成功
            console.log("登录成功！");
            console.log(res.data);
            /** 将Token保存到localStorage*/
            const authorization = res.data.HTTP2_HEADER_AUTHORIZATION;
            localStorage.token = authorization;
            this.msg = authorization;
          })
          .catch(error => {
            console.log("登录失败！");
            console.log(error);
            this.msg = error;
          });
    },
    test() {
      this.$http.get("/hello").then(res => {
        console.log(res);
        this.msg = res.data;
      });
    },
    test1() {
      this.$http.get("/test1").then(res => {
        console.log(res);
        this.msg = res.data;
      });
    },
    test2() {
      this.$http.get("/test2").then(res => {
        console.log(res);
        this.msg = res.data;
      });
    },
    needAdminRole() {
      this.$http.get("/needAdminRole").then(res => {
        console.log(res);
        this.msg = res.data;
      });
    },
    needTestRole() {
      this.$http.get("/needTestRole").then(res => {
        console.log(res);
        this.msg = res.data;
      });
    },
    logout(){
      this.$http.get("/logout").then(res => {
        console.log(res);
        localStorage.removeItem("token");
        this.msg = res.data;
      });
    }
  }
};
</script>

<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
