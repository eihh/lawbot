<template>
  <div class="illegal-container">
    <!-- 导航栏 -->
    <!-- 主要内容 -->
    <main>
      <!-- 英雄区域 -->
      <section class="hero">
        <div class="container">
          <h1 class="hero-title">深度求索AI助手</h1>
          <p class="hero-subtitle">
            illegal Chat 是一款强大的 AI
            助手，能够回答你的问题、协助创作，并帮助你更高效地工作
          </p>
        </div>
        <div
          style="
            width: 200px;
            height: 100px;
            line-height: 100px;
            text-align: center;
            background-color: aqua;
            margin: 0 auto;
            border-radius: 10px;
            color: white;
            font-size: 30px;
            cursor: pointer;
          "
          @click="tt"
        >
          点击进去
        </div>
      </section>

      <!-- 特性区域 -->
      <section class="features">
        <div class="container">
          <h2 class="section-title">为什么选择 illegal</h2>
          <div class="features-grid">
            <div class="feature-card">
              <div class="feature-icon">💡</div>
              <h3 class="feature-title">强大的知识库</h3>
              <p class="feature-desc">
                基于海量数据训练，覆盖广泛领域，提供准确、全面的回答
              </p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">⚡</div>
              <h3 class="feature-title">快速响应</h3>
              <p class="feature-desc">
                毫秒级响应速度，让你的工作流程更加流畅高效
              </p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">🔒</div>
              <h3 class="feature-title">安全可靠</h3>
              <p class="feature-desc">
                严格的数据隐私保护措施，确保你的信息安全
              </p>
            </div>
          </div>
        </div>
      </section>
    </main>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-column">
            <h3>产品</h3>
            <ul class="footer-links">
              <li><a href="#">illegal Chat</a></li>
              <li><a href="#">illegal Coder</a></li>
              <li><a href="#">API</a></li>
              <li><a href="#">企业版</a></li>
            </ul>
          </div>
          <div class="footer-column">
            <h3>资源</h3>
            <ul class="footer-links">
              <li><a href="#">文档</a></li>
              <li><a href="#">博客</a></li>
              <li><a href="#">社区</a></li>
              <li><a href="#">帮助中心</a></li>
            </ul>
          </div>
          <div class="footer-column">
            <h3>公司</h3>
            <ul class="footer-links">
              <li><a href="#">关于我们</a></li>
              <li><a href="#">加入我们</a></li>
              <li><a href="#">联系我们</a></li>
            </ul>
          </div>
          <div class="footer-column">
            <h3>法律</h3>
            <ul class="footer-links">
              <li><a href="#">隐私政策</a></li>
              <li><a href="#">服务条款</a></li>
              <li><a href="#">使用政策</a></li>
            </ul>
          </div>
        </div>
        <div class="footer-bottom">
          <p>© 2024 illegal. 保留所有权利。</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "illegalPage",
  data() {
    return {
      userInput: "",
      messages: [
        {
          role: "assistant",
          content:
            "你好！我是illegal Chat，你的AI助手。有什么我可以帮助你的吗？",
        },
      ],
      isTyping: false,
    };
  },
  methods: {
    tt() {
      this.$router.push("/wenshu");
    },
    sendMessage() {
      if (!this.userInput.trim() || this.isTyping) return;

      const userMessage = {
        role: "user",
        content: this.userInput,
      };

      this.messages.push(userMessage);
      this.userInput = "";

      this.isTyping = true;

      // 模拟API响应延迟
      setTimeout(() => {
        const responses = [
          "这是一个很好的问题。illegal Chat是一个强大的AI助手，可以帮助你解决各种问题，包括编程、写作、学习等。",
          "感谢你的提问。illegal Chat基于先进的大语言模型，能够理解和生成自然语言文本。",
          "我理解你的疑问。illegal Chat可以24/7为你提供服务，随时解答你的问题。",
          "这个问题很有趣。illegal Chat不断学习和改进，以提供更准确和有用的回答。",
          "让我为你详细解释一下。illegal Chat支持多种语言，可以处理复杂的查询和任务。",
        ];

        const randomResponse =
          responses[Math.floor(Math.random() * responses.length)];

        this.messages.push({
          role: "assistant",
          content: randomResponse,
        });

        this.isTyping = false;

        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }, 1500);

      this.scrollToBottom();
    },
    clearChat() {
      this.messages = [
        {
          role: "assistant",
          content:
            "你好！我是illegal Chat，你的AI助手。有什么我可以帮助你的吗？",
        },
      ];
    },
    scrollToBottom() {
      const container = this.$refs.messagesContainer;
      container.scrollTop = container.scrollHeight;
    },
  },
};
</script>

<style scoped>
.illegal-container {
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background-color: #f9f9f9;
  color: #333;
  line-height: 1.6;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 导航栏 */
.navbar {
  background-color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  padding: 15px 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
}

.logo img {
  height: 32px;
  margin-right: 10px;
}

.logo-text {
  font-size: 20px;
  font-weight: 700;
  color: #333;
}

.nav-links {
  display: flex;
  list-style: none;
}

.nav-links li {
  margin-left: 30px;
}

.nav-links a {
  text-decoration: none;
  color: #555;
  font-weight: 500;
  font-size: 15px;
  transition: color 0.2s;
}

.nav-links a:hover {
  color: #0066ff;
}

.nav-buttons {
  display: flex;
  align-items: center;
}

.login-btn {
  background: none;
  border: none;
  color: #555;
  font-weight: 500;
  font-size: 15px;
  cursor: pointer;
  margin-right: 20px;
  padding: 8px 0;
}

.try-btn {
  background-color: #0066ff;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  font-weight: 500;
  font-size: 15px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.try-btn:hover {
  background-color: #0052cc;
}

/* 英雄区域 */
.hero {
  padding: 80px 0;
  text-align: center;
}

.hero-title {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 20px;
  line-height: 1.2;
}

.hero-subtitle {
  font-size: 18px;
  color: #666;
  max-width: 700px;
  margin: 0 auto 40px;
}

.chat-container {
  max-width: 900px;
  margin: 0 auto;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.chat-header {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chat-title {
  font-weight: 600;
  font-size: 16px;
}

.chat-actions button {
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  margin-left: 10px;
}

.chat-messages {
  height: 500px;
  overflow-y: auto;
  padding: 20px;
}

.message {
  margin-bottom: 20px;
}

.user-message {
  text-align: right;
}

.bot-message {
  text-align: left;
}

.message-content {
  display: inline-block;
  max-width: 80%;
  padding: 12px 16px;
  border-radius: 8px;
  font-size: 15px;
  line-height: 1.5;
}

.user-message .message-content {
  background-color: #0066ff;
  color: white;
}

.bot-message .message-content {
  background-color: #f5f5f5;
  color: #333;
}

.chat-input-container {
  padding: 15px;
  border-top: 1px solid #eee;
  display: flex;
}

.chat-input {
  flex: 1;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 15px;
  outline: none;
  transition: border-color 0.2s;
}

.chat-input:focus {
  border-color: #0066ff;
}

.send-btn {
  background-color: #0066ff;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0 20px;
  margin-left: 10px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.send-btn:hover {
  background-color: #0052cc;
}

/* 特性区域 */
.features {
  padding: 80px 0;
  background-color: white;
}

.section-title {
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 50px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

.feature-card {
  background: #f9f9f9;
  border-radius: 12px;
  padding: 30px;
  text-align: center;
  transition: transform 0.3s, box-shadow 0.3s;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.05);
}

.feature-icon {
  font-size: 40px;
  margin-bottom: 20px;
  color: #0066ff;
}

.feature-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 15px;
}

.feature-desc {
  color: #666;
  font-size: 15px;
}

/* 页脚 */
.footer {
  background-color: #f5f5f5;
  padding: 50px 0 30px;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 30px;
  margin-bottom: 40px;
}

.footer-column h3 {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 20px;
}

.footer-links {
  list-style: none;
}

.footer-links li {
  margin-bottom: 12px;
}

.footer-links a {
  text-decoration: none;
  color: #666;
  font-size: 14px;
  transition: color 0.2s;
}

.footer-links a:hover {
  color: #0066ff;
}

.footer-bottom {
  text-align: center;
  padding-top: 30px;
  border-top: 1px solid #eee;
  color: #999;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .nav-links {
    display: none;
  }

  .hero-title {
    font-size: 36px;
  }

  .features-grid {
    grid-template-columns: 1fr;
  }

  .footer-content {
    grid-template-columns: repeat(2, 1fr);
  }
}

/* 加载动画 */
.typing-indicator {
  display: flex;
  padding: 10px 15px;
}

.typing-dot {
  width: 8px;
  height: 8px;
  margin: 0 2px;
  background-color: #999;
  border-radius: 50%;
  opacity: 0.4;
  animation: typingAnimation 1.4s infinite ease-in-out;
}

.typing-dot:nth-child(1) {
  animation-delay: 0s;
}

.typing-dot:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-dot:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typingAnimation {
  0%,
  60%,
  100% {
    opacity: 0.4;
    transform: translateY(0);
  }
  30% {
    opacity: 1;
    transform: translateY(-5px);
  }
}
</style>