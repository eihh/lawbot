<template>
  <div class="chat-container">
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
          {{ message.content }}
        </div>

        <div v-if="!message.isAi" class="avatar user-avatar">
          <i class="fas fa-user"></i>
        </div>
      </div>
    </div>

    <div class="input-area">
      <input
        v-model="userInput"
        @keyup.enter="sendMessage"
        placeholder="输入你的问题..."
        class="message-input"
      />
      <button 
        @click="sendMessage"
        :disabled="isSending"
        class="send-button"
      >
        {{ isSending ? '发送中...' : '发送' }}
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name:"AbA",
  data() {
    return {
      userInput: '',
      isSending: false,
      messages: [
        { id: 1, content: '你好！我是AI助手，有什么可以帮您的？', isAi: true }
      ],
      messageId: 2
    }
  },


  methods: {
    async sendMessage() {
      if (!this.userInput.trim() || this.isSending) return

      const userMessage = {
        id: this.messageId++,
        content: this.userInput.trim(),
        isAi: false
      }
      this.messages.push(userMessage)
      
      this.userInput = ''
      this.isSending = true
      
      // 模拟AI回复
      await this.mockApiCall()
      
      // 添加AI回复
      const aiResponse = {
        id: this.messageId++,
        content: `关于 "${userMessage.content}" 的回复示例...`,
        isAi: true
      }
      this.messages.push(aiResponse)
      
      this.isSending = false
      this.scrollToBottom()
    },
    mockApiCall() {
      return new Promise(resolve => setTimeout(resolve, 800))
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messagesContainer
        container.scrollTo({
          top: container.scrollHeight,
          behavior: 'smooth'
        })
      })
    }
  }
}
</script>

<style scoped>
.chat-container {
  max-width: 800px;
  margin: 20px auto;
  height: 90vh;
  display: flex;
  flex-direction: column;
  background: #f5f7fb;
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', sans-serif;
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message-wrapper {
  max-width: 75%;
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.ai-message {
  align-self: flex-start;
  flex-direction: row;
}

.user-message {
  align-self: flex-end;
  flex-direction: row;
}

.message-bubble {
  padding: 12px 18px;
  border-radius: 20px;
  line-height: 1.5;
  word-break: break-word;
  font-size: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.ai-message .message-bubble {
  background: white;
  color: #333;
  border: 1px solid #e5e7eb;
  border-radius: 20px 20px 20px 5px;
  order: 1;
}

.user-message .message-bubble {
  background: #3b82f6;
  color: white;
  border-radius: 20px 20px 5px 20px;
  order: 2;
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
}

.ai-avatar {
  background: #3b82f6;
  color: white;
  order: 0;
}

.user-avatar {
  background: #10b981;
  color: white;
  order: 3; /* 用户头像放在最右侧 */
}

.input-area {
  padding: 20px;
  background: white;
  border-top: 1px solid #e5e7eb;
  display: flex;
  gap: 12px;
  border-radius: 0 0 15px 15px;
}

.message-input {
  flex: 1;
  padding: 12px 18px;
  border: 1px solid #e5e7eb;
  border-radius: 25px;
  outline: none;
  font-size: 15px;
  transition: all 0.3s;
}

.message-input:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.send-button {
  padding: 12px 25px;
  background: #3b82f6;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s;
  font-weight: 500;
}

.chat-messages::-webkit-scrollbar {
  width: 8px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 4px;
}
</style>
