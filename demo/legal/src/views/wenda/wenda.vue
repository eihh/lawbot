<template>
  <div class="chat-container">
    <div class="chat-header">
      <div class="header-content">
        <div class="logo">
          <i class="fas fa-robot"></i>
          <span>AI 助手</span>
        </div>
        <div class="status">
          <span class="status-dot"></span>
          <span>在线</span>
        </div>
      </div>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div
        v-for="message in messages"
        :key="message.id"
        class="message-wrapper"
        :class="{ 'ai-message': message.isAi, 'user-message': !message.isAi }"
      >
        <div v-if="message.isAi" class="avatar ai-avatar">
          <i class="fas fa-robot"></i>
        </div>

        <div class="message-bubble">
          <div class="message-content">
            {{ message.content }}
          </div>
          <div class="message-time">
            {{ formatTime() }}
          </div>
        </div>

        <div v-if="!message.isAi" class="avatar user-avatar">
          <i class="fas fa-user"></i>
        </div>
      </div>
    </div>

    <div class="input-area">
      <div class="input-container">
        <input
          v-model="userInput"
          @keyup.enter="sendMessage"
          placeholder="输入你的问题..."
          class="message-input"
        />
        <div class="input-actions">
          <button class="action-button">
            <i class="fas fa-smile"></i>
          </button>
          <button class="action-button">
            <i class="fas fa-paperclip"></i>
          </button>
        </div>
      </div>
      <button @click="sendMessage" :disabled="isSending" class="send-button">
        <span v-if="!isSending" style="color: red;">
          发送
        </span>
        <span v-else style="color: red;">
          发送
        </span>
      </button>
    </div>
  </div>
</template>

<script>
import request from "@/axios/request";

export default {
  name: "AbA",
  data() {
    return {
      userInput: "",
      isSending: false,
      messages: [
        { id: 1, content: "你好！我是AI助手，有什么可以帮您的？", isAi: true },
      ],
      messageId: 2,
    };
  },

  methods: {
    formatTime() {
      const now = new Date();
      return `${now.getHours()}:${now.getMinutes().toString().padStart(2, '0')}`;
    },
    
    async sendMessage() {
      if (!this.userInput.trim() || this.isSending) return;

      const userMessage = {
        id: this.messageId++,
        content: this.userInput.trim(),
        isAi: false,
      };
      this.messages.push(userMessage);

      const response = await request.post("/wenda/send", {
        id: userMessage.id,
        content: userMessage.content,
      });
      console.log(response);

      this.userInput = "";
      this.isSending = true;

      // 模拟AI回复
      await this.mockApiCall();

      const aiResponse = {
        id: this.messageId++,
        content: response.msg,
        isAi: true,
      };

      this.messages.push(aiResponse);

      this.isSending = false;
      this.scrollToBottom();
    },
    
    mockApiCall() {
      return new Promise((resolve) => setTimeout(resolve, 800));
    },
    
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messagesContainer;
        container.scrollTo({
          top: container.scrollHeight,
          behavior: "smooth",
        });
      });
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

:root {
  --primary-color: #6c5ce7;
  --secondary-color: #a29bfe;
  --user-color: #00b894;
  --ai-color: #6c5ce7;
  --text-color: #2d3436;
  --light-text: #636e72;
  --bg-color: #f9f9f9;
  --message-bg: #ffffff;
  --user-message-bg: #dfe6e9;
  --shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  --border-radius: 12px;
}

* {
  box-sizing: border-box;
}

.chat-container {
  max-width: 800px;
  margin: 20px auto;
  height: 90vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  border-radius: var(--border-radius);
  box-shadow: var(--shadow);
  font-family: 'Poppins', sans-serif;
  overflow: hidden;
}

.chat-header {
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--secondary-color) 100%);
  color: white;
  padding: 15px 25px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  font-size: 18px;
  font-weight: 600;
}

.logo i {
  margin-right: 10px;
  font-size: 20px;
}

.status {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.status-dot {
  width: 8px;
  height: 8px;
  background-color: #55efc4;
  border-radius: 50%;
  margin-right: 8px;
  animation: pulse 1.5s infinite;
}

@keyframes pulse {
  0% { opacity: 1; }
  50% { opacity: 0.5; }
  100% { opacity: 1; }
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 15px;
  background: url('https://www.transparenttextures.com/patterns/light-wool.png');
}

.message-wrapper {
  max-width: 80%;
  display: flex;
  align-items: flex-end;
  gap: 12px;
  transition: all 0.3s ease;
}

.message-wrapper:hover {
  transform: translateY(-2px);
}

.ai-message {
  align-self: flex-start;
}

.user-message {
  align-self: flex-end;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: var(--border-radius);
  line-height: 1.5;
  word-break: break-word;
  font-size: 15px;
  box-shadow: var(--shadow);
  position: relative;
  transition: all 0.3s ease;
}

.ai-message .message-bubble {
  background: var(--message-bg);
  color: var(--text-color);
  border-radius: 0 var(--border-radius) var(--border-radius) var(--border-radius);
  margin-left: 10px;
}

.ai-message .message-bubble::before {
  content: '';
  position: absolute;
  left: -10px;
  bottom: 0;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: var(--message-bg);
  border-left: 0;
}

.user-message .message-bubble {
  background: linear-gradient(135deg, var(--user-color) 0%, #00cec9 100%);
  color: white;
  border-radius: var(--border-radius) 0 var(--border-radius) var(--border-radius);
  margin-right: 10px;
}

.user-message .message-bubble::before {
  content: '';
  position: absolute;
  right: -10px;
  bottom: 0;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-left-color: var(--user-color);
  border-right: 0;
}

.message-content {
  margin-bottom: 5px;
}

.message-time {
  font-size: 11px;
  opacity: 0.7;
  text-align: right;
}

.user-message .message-time {
  color: rgba(255, 255, 255, 0.7);
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  font-size: 18px;
  box-shadow: var(--shadow);
  transition: all 0.3s ease;
}

.avatar:hover {
  transform: scale(1.1);
}

.ai-avatar {
  background: linear-gradient(135deg, var(--ai-color) 0%, var(--secondary-color) 100%);
  color: white;
}

.user-avatar {
  background: linear-gradient(135deg, var(--user-color) 0%, #00cec9 100%);
  color: white;
}

.input-area {
  padding: 15px;
  background: white;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 12px;
  align-items: center;
}

.input-container {
  flex: 1;
  display: flex;
  background: #f1f3f6;
  border-radius: 25px;
  padding: 5px 15px;
  align-items: center;
}

.message-input {
  flex: 1;
  padding: 12px 0;
  border: none;
  background: transparent;
  outline: none;
  font-size: 15px;
  font-family: 'Poppins', sans-serif;
  color: var(--text-color);
}

.message-input::placeholder {
  color: var(--light-text);
  opacity: 0.7;
}

.input-actions {
  display: flex;
  gap: 8px;
}

.action-button {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: none;
  background: transparent;
  color: var(--light-text);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.action-button:hover {
  background: rgba(0, 0, 0, 0.05);
  color: var(--primary-color);
}

.send-button {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  border: none;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--secondary-color) 100%);
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  box-shadow: 0 4px 10px rgba(108, 92, 231, 0.3);
}

.send-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(108, 92, 231, 0.4);
}

.send-button:disabled {
  opacity: 0.7;
  transform: none;
  box-shadow: none;
  cursor: not-allowed;
}

/* 自定义滚动条 */
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: rgba(108, 92, 231, 0.5);
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: var(--primary-color);
}

/* 动画效果 */
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.message-wrapper {
  animation: fadeIn 0.3s ease-out forwards;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-container {
    height: 100vh;
    margin: 0;
    border-radius: 0;
  }
  
  .message-wrapper {
    max-width: 90%;
  }
}
</style>