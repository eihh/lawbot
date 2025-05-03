<template>
  <div class="ai-assistant">
    <header>
      <h1>法律文书摘要助手</h1>
    </header>

    <div class="chat-container" ref="chatContainer">
      <div
          v-for="(message, index) in messages"
          :key="index"
          :class="['message', `${message.sender}-message`]"
      >

        <template v-if="message.type === 'text'">
          <div v-html="message.content"></div>
        </template>

        <template v-else-if="message.type === 'file'">
          <div class="file-card">
            <div class="file-icon">{{ fileIcon(message.file) }}</div>
            <div class="file-name">{{ message.file.name }}</div>
            <div class="file-size">{{ formatFileSize(message.file.size) }}</div>

            <img
                v-if="isImage(message.file)"
                :src="message.preview"
                class="image-preview"
                @click="openImage(message.preview)"
            />

            <a
                class="download-btn"
                :href="message.url"
                download
                target="_blank"
            >
              下载文件
            </a>
          </div>
        </template>

        <span v-if="message.typing" class="typing-indicator">
          <span></span><span></span><span></span>
        </span>
      </div>
    </div>

    <div class="input-area">
      <!--      <textarea
              v-model="userInput"
              placeholder="输入您的问题..."
              rows="1"
              @input="adjustTextareaHeight"
              @keypress.enter.exact.prevent="sendMessage"
            ></textarea>
      -->


      <div
          style="margin-left: 500px"

          class="drop-zone"
          @dragover.prevent
          @drop.prevent="handleDrop"
      >
        拖拽文件到这里，或点击 📎 选择文件
      </div>

      <!-- 文件选择按钮 -->
      <label for="fileUpload" class="file-label" title="上传文件"> 📎 </label>
      <input
          type="file"
          id="fileUpload"
          class="file-input"
          multiple
          @change="handleFileChange"
      />


      <span class="file-info" v-if="files.length > 0">
        {{ files.length }}个文件 ({{ totalFileSize }})
      </span>


      <!-- 手动触发上传按钮 -->
      <button class="send-btn" @click="sendMessage" title="发送">↑</button>


    </div>
  </div>
</template>

<script>
import request from "@/axios/request";

export default {
  name: "AIAssistant",
  data() {
    return {
      userInput: "",
      files: [],

      messages: [
        {
          sender: "ai",
          type: "text",
          content:
              "您好！我是AI助手，请问有什么可以帮您的吗？您可以发送文字或上传文件。",
        },
      ],
      isTyping: false,
    };
  },
  computed: {
    totalFileSize() {
      const totalBytes = this.files.reduce((sum, file) => sum + file.size, 0);
      return this.formatFileSize(totalBytes);
    },


  },

  methods: {
    adjustTextareaHeight(event) {
      const textarea = event.target;
      textarea.style.height = "auto";
      textarea.style.height =
          (textarea.scrollHeight > 120 ? 120 : textarea.scrollHeight) + "px";
    },

    handleChange(event) {
      this.files = Array.from(event.target.files);

    },


    async uploadFile(file) {
      const formData = new FormData();
      formData.append("file", file);


      //将返回的json处理成正常字段
      function formattedSummary(msg) {
        const parsed = JSON.parse(msg);
        return parsed.summary.replace(/\n/g, '<br/>');
      }

      try {
        const response = await request.post("/wenshu/upload", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          onUploadProgress: (progressEvent) => {

          },
        });

        console.log("文件上传成功：", response);

        // 添加用户文本消息

        this.addMessage({
          sender: "ai",
          type: "text",
          content: formattedSummary(response.msg),
        });


      } catch (error) {
        console.error("文件上传失败：", error);
      }
    },


    async sendMessage() {
      const message = this.userInput.trim();

      if (message || this.files.length > 0) {
        // 添加用户文本消息
        if (message) {
          this.addMessage({
            sender: "user",
            type: "text",
            content: message,
          });
        }

        // 添加用户文件消息 + 发送到后端
        for (const file of this.files) {
          this.addFileMessage(file);
          await this.uploadFile(file); //  添加上传逻辑
        }

        // 清空输入
        this.userInput = "";
        this.files = [];
        document.getElementById("fileUpload").value = "";

        // 模拟AI回复
        this.simulateAIResponse();
      }
    },


    /*addMessage(message) {
      this.messages.push(message);
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },*/
    addMessage(message) {
      if (message.type === 'text') {
        message.content = message.content.replace(/\n/g, '<br/>');
      }
      this.messages.push(message);
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },

    async addFileMessage(file) {
      const message = {
        sender: "user",
        type: "file",
        file: file,
        url: URL.createObjectURL(file),
        preview: null,
      };

      if (this.isImage(file)) {
        message.preview = await this.readFileAsDataURL(file);
      }

      this.addMessage(message);
    },

    readFileAsDataURL(file) {
      return new Promise((resolve) => {
        const reader = new FileReader();
        reader.onload = (e) => resolve(e.target.result);
        reader.readAsDataURL(file);
      });
    },

    simulateAIResponse() {
      // 显示"正在输入"指示器
      this.isTyping = true;
      this.addMessage({
        sender: "ai",
        type: "text",
        content: "",
        typing: true,
      });

      // 模拟AI思考时间
      setTimeout(() => {
        // 移除"正在输入"指示器
        this.messages = this.messages.filter((msg) => !msg.typing);
        this.isTyping = false;

        // 添加AI回复
        const responses = [
          "我理解您的问题了，让我帮您分析一下...",
          "这是一个很好的问题！根据我的分析...",
          "感谢您的提问。关于这个问题...",
          "我已收到您上传的文件，正在处理中...",
          "根据您提供的信息，我的建议是...",
        ];

        const randomResponse =
            responses[Math.floor(Math.random() * responses.length)];
        this.addMessage({
          sender: "ai",
          type: "text",
          content: randomResponse,
        });
      }, 1500 + Math.random() * 2000);
    },

    handleFileChange(event) {
      this.files = Array.from(event.target.files);
    },

    handleDrop(event) {
      const droppedFiles = Array.from(event.dataTransfer.files);
      this.files = [...this.files, ...droppedFiles];
    },


    formatFileSize(bytes) {
      if (bytes === 0) return "0 Bytes";
      const k = 1024;
      const sizes = ["Bytes", "KB", "MB", "GB"];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return parseFloat((bytes / Math.pow(k, i)).toFixed(2) + " " + sizes[i]);
    },

    isImage(file) {
      return file.type.split("/")[0] === "image";
    },

    fileIcon(file) {
      const fileExt = file.name.split(".").pop().toLowerCase();
      const icons = {
        pdf: "📕",
        doc: "📄",
        docx: "📄",
        xls: "📊",
        xlsx: "📊",
        ppt: "📑",
        pptx: "📑",
        zip: "🗜️",
        rar: "🗜️",
        "7z": "🗜️",
        txt: "📝",
        mp3: "🎵",
        wav: "🎵",
        mp4: "🎬",
        mov: "🎬",
        avi: "🎬",
      };
      return icons[fileExt] || "📁";
    },

    openImage(src) {
      window.open(src, "_blank");
    },

    scrollToBottom() {
      const container = this.$refs.chatContainer;
      container.scrollTop = container.scrollHeight;
    },
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-family: "Arial", sans-serif;
}

.ai-assistant {
  background-color: #f5f5f5;
  color: #333;
  display: flex;
  flex-direction: column;
  height: 100vh;
}

header {
  background-color: #4a6fa5;
  color: white;
  padding: 15px 20px;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.chat-container {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  height: 60%;
}

.message {
  max-width: 80%;
  margin-bottom: 15px;
  padding: 12px 16px;
  border-radius: 18px;
  line-height: 1.4;
  position: relative;
}

.user-message {
  align-self: flex-end;
  background-color: #4a6fa5;
  color: white;
  border-bottom-right-radius: 5px;
}

.ai-message {
  align-self: flex-start;
  background-color: #e9e9e9;
  border-bottom-left-radius: 5px;
}

.input-area {
  padding: 15px;
  background-color: white;
  border-top: 1px solid #ddd;
  display: flex;
  align-items: center;
}

textarea {
  flex: 1;
  border: 1px solid #ddd;
  border-radius: 20px;
  padding: 12px 15px;
  resize: none;
  height: 45px;
  max-height: 120px;
  outline: none;
  font-size: 16px;
}

.file-input {
  display: none;
}

.file-label,
.send-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 10px;
  cursor: pointer;
  background-color: #f0f0f0;
  color: #4a6fa5;
  border: none;
  font-size: 18px;
}

.send-btn {
  background-color: #4a6fa5;
  color: white;
}

.file-label:hover,
.send-btn:hover {
  opacity: 0.9;
}

.file-info {
  font-size: 12px;
  color: #666;
  margin-left: 10px;
}

.typing-indicator {
  display: inline-block;
  padding-left: 5px;
}

.typing-indicator span {
  display: inline-block;
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #999;
  margin-right: 3px;
  animation: typing 1s infinite ease-in-out;
}

.typing-indicator span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-indicator span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

/* 文件卡片样式 */
.file-card {
  background-color: white;
  border-radius: 10px;
  padding: 12px;
  margin-top: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  max-width: 300px;
}

.file-icon {
  font-size: 40px;
  text-align: center;
  margin-bottom: 8px;
}

.file-name {
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-size {
  font-size: 12px;
  color: #666;
  margin: 4px 0;
}

.download-btn {
  display: block;
  background-color: #4a6fa5;
  color: white;
  text-align: center;
  padding: 6px 12px;
  border-radius: 5px;
  text-decoration: none;
  font-size: 14px;
  margin-top: 8px;
}

.download-btn:hover {
  opacity: 0.9;
}

/* 图片预览样式 */
.image-preview {
  max-width: 100%;
  max-height: 200px;
  border-radius: 8px;
  margin-top: 8px;
  cursor: pointer;
}

.drop-zone {
  border: 2px dashed #ccc;
  padding: 30px;
  text-align: center;
  margin-bottom: 10px;
  border-radius: 8px;
  background-color: #f9f9f9;
  cursor: pointer;
}


</style>