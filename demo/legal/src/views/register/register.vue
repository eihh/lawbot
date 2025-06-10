<template>
    <div class="register-container">
      <div class="register-card">
        <h1 class="register-title">创建账户</h1>
        <p class="register-subtitle">加入我们，开始您的旅程</p>
        
        <form @submit.prevent="handleSubmit" class="register-form">
          <div class="form-group">
            <label for="username">用户名</label>
            <input 
              type="text" 
              id="username" 
              v-model="form.username" 
              placeholder="输入用户名"
              required
            >
            <span class="input-icon">
              <i class="fas fa-user"></i>
            </span>
          </div>
          

          <div class="form-group">
            <label for="password">密码</label>
            <input 
              :type="showPassword ? 'text' : 'password'" 
              id="password" 
              v-model="form.password" 
              placeholder="输入密码"
              required
            >
            <span class="input-icon">
              <i class="fas fa-lock"></i>
            </span>
            <span class="password-toggle" @click="showPassword = !showPassword">
              <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
            </span>
          </div>
          
          <div class="form-group">
            <label for="confirmPassword">确认密码</label>
            <input 
              :type="showConfirmPassword ? 'text' : 'password'" 
              id="confirmPassword" 
              v-model="form.confirmPassword" 
              placeholder="再次输入密码"
              required
            >
            <span class="input-icon">
              <i class="fas fa-lock"></i>
            </span>
            <span class="password-toggle" @click="showConfirmPassword = !showConfirmPassword">
              <i :class="showConfirmPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
            </span>
          </div>
          
          <div class="form-actions">
            <button type="submit" class="register-btn" :disabled="isSubmitting">
              <span v-if="!isSubmitting">注册</span>
              <span v-else class="loading">
                <i class="fas fa-spinner fa-spin"></i> 处理中...
              </span>
            </button>
          </div>
        </form>
        
        <div class="login-link">
          已有账户? <router-link to="/">登录</router-link>
        </div>
        


      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'RegisterPage',
    data() {
      return {
        form: {
          username: '',
          password: '',
          confirmPassword: ''
        },
        showPassword: false,
        showConfirmPassword: false,
        isSubmitting: false
      }
    },
    methods: {
      async handleSubmit() {
        if (this.form.password !== this.form.confirmPassword) {
          alert('两次输入的密码不匹配')
          return
        }
        
        this.isSubmitting = true
        
        try {
          // 这里替换为实际的注册API调用
          // await this.$api.register(this.form)
          console.log('注册信息:', this.form)
          
          // 模拟API延迟
          await new Promise(resolve => setTimeout(resolve, 1500))
          
          alert('注册成功!')
          this.$router.push('/login')
        } catch (error) {
          console.error('注册失败:', error)
          alert('注册失败: ' + (error.message || '请稍后再试'))
        } finally {
          this.isSubmitting = false
        }
      }
    }
  }
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');
  @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');
  
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 20px;
  }
  
  .register-card {
    background: white;
    border-radius: 16px;
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 450px;
    padding: 40px;
    animation: fadeIn 0.5s ease-in-out;
  }
  
  @keyframes fadeIn {
    from { opacity: 0; transform: translateY(20px); }
    to { opacity: 1; transform: translateY(0); }
  }
  
  .register-title {
    font-size: 28px;
    font-weight: 600;
    color: #333;
    margin-bottom: 8px;
    text-align: center;
  }
  
  .register-subtitle {
    font-size: 14px;
    color: #666;
    text-align: center;
    margin-bottom: 30px;
  }
  
  .register-form {
    margin-top: 20px;
  }
  
  .form-group {
    position: relative;
    margin-bottom: 20px;
  }
  
  .form-group label {
    display: block;
    font-size: 14px;
    color: #555;
    margin-bottom: 8px;
    font-weight: 500;
  }
  
  .form-group input {
    width: 88%;
    padding: 12px 40px 12px 15px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 14px;
    transition: all 0.3s ease;
  }
  
  .form-group input:focus {
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
    outline: none;
  }
  
  .input-icon {
    position: absolute;
    right: 15px;
    top: 38px;
    color: #999;
    font-size: 14px;
  }
  
  .password-toggle {
    position: absolute;
    right: 40px;
    top: 38px;
    color: #999;
    font-size: 14px;
    cursor: pointer;
    transition: color 0.3s;
  }
  
  .password-toggle:hover {
    color: #667eea;
  }
  
  .form-actions {
    margin-top: 30px;
  }
  
  .register-btn {
    width: 100%;
    padding: 14px;
    background: linear-gradient(to right, #667eea, #764ba2);
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .register-btn:hover {
    background: linear-gradient(to right, #5a6fd1, #6a4295);
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
  }
  
  .register-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
    transform: none;
    box-shadow: none;
  }
  
  .loading {
    display: inline-block;
  }
  
  .fa-spin {
    animation: fa-spin 1s infinite linear;
  }
  
  @keyframes fa-spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
  
  .login-link {
    text-align: center;
    margin-top: 20px;
    font-size: 14px;
    color: #666;
  }
  
  .login-link a {
    color: #667eea;
    text-decoration: none;
    font-weight: 500;
    transition: color 0.3s;
  }
  
  .login-link a:hover {
    color: #764ba2;
    text-decoration: underline;
  }
  
  .social-login {
    margin-top: 30px;
  }
  
  .divider {
    display: flex;
    align-items: center;
    color: #999;
    font-size: 12px;
    margin-bottom: 20px;
  }
  
  .divider::before, .divider::after {
    content: "";
    flex: 1;
    border-bottom: 1px solid #eee;
  }
  
  .divider::before {
    margin-right: 10px;
  }
  
  .divider::after {
    margin-left: 10px;
  }
  
  .social-icons {
    display: flex;
    justify-content: center;
    gap: 15px;
  }
  
  .social-btn {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    border: none;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .social-btn:hover {
    transform: translateY(-3px);
  }
  
  .social-btn.google {
    background: #db4437;
    color: white;
  }
  
  .social-btn.facebook {
    background: #4267B2;
    color: white;
  }
  
  .social-btn.twitter {
    background: #1DA1F2;
    color: white;
  }
  
  /* 响应式设计 */
  @media (max-width: 480px) {
    .register-card {
      padding: 30px 20px;
    }
    
    .register-title {
      font-size: 24px;
    }
  }
  </style>