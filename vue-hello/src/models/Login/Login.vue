<template>
  <div class="all">
    <a-card id="logincard" style="width: 360px">
      <a-form-model
          id="components-form-demo-normal-login"
          :form="form"
          class="login-form"
          @submit="handleSubmit"
      >
        <a-form-model-item>
          <a-input
              v-decorator="[
          'userName',
          { rules: [{ required: true, message: 'Please input your username!' }] },
        ]"
              placeholder="Username"
              v-model="formMess.account"
          >
            <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-input
              v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Please input your Password!' }] },
        ]"
              type="password"
              placeholder="Password"
              v-model="formMess.password"
          >
            <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-checkbox
              v-decorator="[
          'remember',
          {
            valuePropName: 'checked',
            initialValue: true,
          },
        ]"
          >
            Remember me
          </a-checkbox>
          <a class="login-form-forgot" href="">
            Forgot password
          </a>
          <a-button type="primary" html-type="submit" class="login-form-button">
            Log in
          </a-button>
          Or
          <a href="">
            register now!
          </a>
        </a-form-model-item>
      </a-form-model>
    </a-card>

  </div>
</template>

<script>
export default {
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: 'normal_login' });
  },
name: "Login",
  data(){
  return{
    msg: 'denglu',
    formMess: {
      "account": "",
      "password": ""
    }
  }
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err) => {
        if (!err) {
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
                const authorization = res.data.Authorization;
                localStorage.token = authorization;
              })
              .catch(error => {
                console.log("登录失败！");
                console.log(error);
                this.msg = error;
              });
        }
      });
    },
  },
}
</script>

<style scoped>
.all{
  background: url("../../assets/back1.jpg");
  height: 745px;
  display: flex;
  justify-content: center;
  position: relative;

}
#components-form-demo-normal-login .login-form {
  max-width: 200px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
#logincard {
  height: 300px;
  position: absolute;
  top: 50%;
  margin-top: -150px;;
}
</style>
