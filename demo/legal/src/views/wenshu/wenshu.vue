<template>
  <div class="ai-assistant">
    <header>
      <div class="header-content">
        <h1>法律文书摘要助手</h1>
        <p class="subtitle">AI-powered Legal Document Summarization</p>
      </div>
    </header>

    <div class="chat-container" ref="chatContainer">
      <div
          v-for="(message, index) in messages"
          :key="index"
          :class="['message', `${message.sender}-message`]"
      >
        <div class="message-content">


          <template v-if="message.type === 'text'">
            <!--收藏按钮-->
            <el-button v-if="message.title !==''"
                       :type="message.isFavorite ? 'success' : 'warning'"
                       size="small"
                       class="favorite-button"
                       @click="toggleFavorite(message, index)"
                       circle>
              <i :class="message.isFavorite ? 'el-icon-star-on' : 'el-icon-star-off'" class="favorite-icon"></i>
            </el-button>
            <!--导出pdf按钮-->
            <el-button v-if="message.title !==''"
                       type="primary"
                       size="small"
                       class="favorite-button"
                       @click="exportToPDF(message)"
                       circle>
              <i class="el-icon-download"></i>
            </el-button>

            <vue-markdown>
              {{ message.content }}
            </vue-markdown>


          </template>

          <template v-else-if="message.type === 'file'">
            <div class="file-card">
              <div class="file-icon">{{ fileIcon(message.file) }}</div>
              <div class="file-info">
                <div class="file-name">{{ message.file.name }}</div>
                <div class="file-size">{{ formatFileSize(message.file.size) }}</div>
              </div>

              <img
                  v-if="isImage(message.file)"
                  :src="message.preview"
                  class="image-preview"
                  @click="openImage(message.preview)"
              />

              <div class="file-actions">
                <a
                    class="download-btn"
                    :href="message.url"
                    download
                    target="_blank"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                       stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                    <polyline points="7 10 12 15 17 10"></polyline>
                    <line x1="12" y1="15" x2="12" y2="3"></line>
                  </svg>
                  下载
                </a>
              </div>
            </div>
          </template>

          <span v-if="message.typing" class="typing-indicator">
            <span></span><span></span><span></span>
          </span>
        </div>
        <div class="message-time">{{ formatMessageTime() }}</div>
      </div>
    </div>

    <div class="input-area">
      <div class="file-upload-container">
        <div
            class="drop-zone"
            @dragover.prevent
            @drop.prevent="handleDrop"
            @click="triggerFileInput"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
               stroke="#4a6fa5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
            <polyline points="17 8 12 3 7 8"></polyline>
            <line x1="12" y1="3" x2="12" y2="15"></line>
          </svg>
          <p>拖拽文件到这里或点击上传</p>
          <p class="hint">支持PDF、Word、TXT等文档格式</p>
        </div>

        <input
            type="file"
            id="fileUpload"
            class="file-input"
            multiple
            @change="handleFileChange"
        />
      </div>

      <div class="selected-files" v-if="files.length > 0">
        <div class="file-badge" v-for="(file, index) in files" :key="index">
          <span class="file-icon">{{ fileIcon(file) }}</span>
          <span class="file-name">{{ file.name }}</span>
          <span class="file-size">{{ formatFileSize(file.size) }}</span>
          <button class="remove-file" @click="removeFile(index)">
            <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="18" y1="6" x2="6" y2="18"></line>
              <line x1="6" y1="6" x2="18" y2="18"></line>
            </svg>
          </button>
        </div>
        <button class="send-btn" @click="sendMessage">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
               stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="22" y1="2" x2="11" y2="13"></line>
            <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/axios/request";
import VueMarkdown from "vue-markdown";
import jsPDF from "jspdf";
import '../../font/simfang-normal'
import html2canvas from "html2canvas";


export default {
  name: "AIAssistant",
  components: {
    'vue-markdown': VueMarkdown
  },


  data() {
    return {
      userInput: "",
      files: [],
      messages: [

      ],


      isTyping: false,
    };
  },

  async created() {

    this.messages = [
      {
        sender: "ai",
        title: "",
        type: "text",
        content: "正在挂载模型,请稍等......",
        isFavorite: false,
      },
    ]

    const reload_response = await request.post("/reload", {content: "summarize"})
    console.log("reload")
    console.log(reload_response)

    if (reload_response.code === 200) {
      this.messages.push({
        sender: "ai",
        title: "",
        type: "text",
        content: "您好！我是AI助手，专门处理法律文书摘要。您可以上传法律文书文件，我将为您提取关键信息并生成摘要。",
        isFavorite: false,
      })

    }


  },



  computed: {


    totalFileSize() {
      const totalBytes = this.files.reduce((sum, file) => sum + file.size, 0);
      return this.formatFileSize(totalBytes);
    },

  },


  methods: {
    async exportToPDF(message) {
      // 创建一个隐藏容器渲染 HTML
      const container = document.createElement('div');
      container.style.position = 'fixed';
      container.style.left = '-9999px';
      container.style.width = '800px';  // 宽度尽量大一点，提升分辨率
      container.style.fontFamily = '宋体, SimSun, serif';
      container.innerHTML = message.content;
      document.body.appendChild(container);

      // 转换为高分辨率 canvas
      const canvas = await html2canvas(container, { scale: 2 });
      const imgData = canvas.toDataURL('image/png');

      // A4 纸尺寸（单位 pt）
      const pdf = new jsPDF('p', 'pt', 'a4');
      const pageHeight = pdf.internal.pageSize.getHeight();
      const pageWidth = pdf.internal.pageSize.getWidth();

      const imgWidth = pageWidth;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;

      let heightLeft = imgHeight;
      let position = 0;

      // 多页处理
      while (heightLeft > 0) {
        pdf.addImage(
            imgData,
            'PNG',
            0,
            position,
            imgWidth,
            imgHeight
        );
        heightLeft -= pageHeight;

        if (heightLeft > 0) {
          pdf.addPage();
          position -= pageHeight;
        }
      }

      pdf.save('文书摘要.pdf');
      document.body.removeChild(container);
    },



    triggerFileInput() {
      document.getElementById('fileUpload').click();
    },

    removeFile(index) {
      this.files.splice(index, 1);
    },

    async toggleFavorite(message, index) {// 收藏功能
      if (message.isFavorite === true) {
        this.$message({type: 'warning', message: "已收藏,请勿重复点击."});
        return
      }


      const response = await request.post("/collection/yuce/save",
          {
            title: message.title,
            content: message.content,
            role: 0
          }
      )
      console.log(response)

      if (response.code === 200) {
        this.$message({type: 'success', message: response.msg});
        message.isFavorite = true;
      } else if (response.code === 500) {
        this.$message({type: 'error', message: response.msg});
      } else {
        this.$message({type: 'error', message: "系统异常"});
      }


    },


    async uploadFile(file) {
      const formData = new FormData();
      formData.append("file", file);

      let filename = file.name;

      function formattedSummary(msg) {
        const parsed = JSON.parse(msg);
        return parsed.summary.replace(/\n/g, '<br/>');
      }

      try {
        this.addMessage({
          sender: "ai",
          type: "text",
          title: "",
          content: "正在分析您上传的法律文书，请稍候...",
        });

        const response = await request.post("/wenshu/upload", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        console.log("文件上传成功：", response);

        this.addMessage({
          sender: "ai",
          type: "text",
          title: filename,
          //content: `<strong>文书摘要：</strong><br/>${formattedSummary(response.msg)}`,
          content: `<strong>文书摘要：</strong><br/>${response.msg}`,
          isFavorite: false,
        });

      } catch (error) {
        console.error("文件上传失败：", error);
        this.addMessage({
          sender: "ai",
          type: "text",
          title: "",
          content: "抱歉，处理文件时出现错误。请确保上传的是有效的法律文书文件并重试。",
        });
      }
    },

    async sendMessage() {
      const message = this.userInput.trim();

      if (message || this.files.length > 0) {
        if (message) {
          this.addMessage({
            sender: "user",
            type: "text",
            content: message,
          });
        }

        for (const file of this.files) {
          this.addFileMessage(file);
          await this.uploadFile(file);
        }

        this.userInput = "";
        this.files = [];
        document.getElementById("fileUpload").value = "";
      }
    },

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

    formatMessageTime() {
      const now = new Date();
      return now.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
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
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', sans-serif;
}

.ai-assistant {
  display: flex;
  flex-direction: column;
  height: 90vh;
  background-color: #f8f9fa;
}

header {
  background: linear-gradient(135deg, #4a6fa5 0%, #3a5a8a 100%);
  color: white;
  padding: 1.2rem 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
}

header h1 {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 0.2rem;
}

.subtitle {
  font-size: 0.85rem;
  opacity: 0.9;
}

.chat-container {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.message {
  max-width: 80%;
  margin-bottom: 1.2rem;
  position: relative;
}

.message-content {
  padding: 0.8rem 1.2rem;
  border-radius: 1rem;
  line-height: 1.5;
  position: relative;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.user-message {
  align-self: flex-end;
}

.user-message .message-content {
  background-color: #4a6fa5;
  color: white;
  border-bottom-right-radius: 0.3rem;
}

.ai-message {
  align-self: flex-start;
}

.ai-message .message-content {
  background-color: white;
  border: 1px solid #e1e4e8;
  border-bottom-left-radius: 0.3rem;
}

.message-time {
  font-size: 0.7rem;
  color: #6c757d;
  margin-top: 0.3rem;
  text-align: right;
}

.ai-message .message-time {
  text-align: left;
}

.input-area {
  padding: 1rem;
  background-color: white;
  border-top: 1px solid #e1e4e8;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.02);
}

.file-upload-container {
  max-width: 1200px;
  margin: 0 auto;
}

.drop-zone {
  border: 2px dashed #d1d5db;
  border-radius: 0.8rem;
  padding: 2rem;
  text-align: center;
  cursor: pointer;
  transition: all 0.2s ease;
  background-color: #f9fafb;
  margin-bottom: 1rem;
}

.drop-zone:hover {
  border-color: #4a6fa5;
  background-color: #f0f4f8;
}

.drop-zone p {
  margin-top: 0.5rem;
  color: #4a6fa5;
  font-weight: 500;
}

.hint {
  font-size: 0.8rem;
  color: #6b7280 !important;
  font-weight: normal !important;
  margin-top: 0.3rem !important;
}

.file-input {
  display: none;
}

.selected-files {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  align-items: center;
  margin-top: 0.5rem;
}

.file-badge {
  display: flex;
  align-items: center;
  background-color: #edf2f7;
  border-radius: 1rem;
  padding: 0.3rem 0.6rem;
  font-size: 0.85rem;
  gap: 0.4rem;
}

.file-icon {
  margin-right: 0.3rem;
}

.file-name {
  max-width: 150px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-size {
  color: #6b7280;
  font-size: 0.75rem;
}

.remove-file {
  background: none;
  border: none;
  color: #6b7280;
  cursor: pointer;
  padding: 0.2rem;
  display: flex;
  align-items: center;
}

.remove-file:hover {
  color: #ef4444;
}

.send-btn {
  background-color: #4a6fa5;
  color: white;
  border: none;
  border-radius: 50%;
  width: 2.5rem;
  height: 2.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s ease;
  margin-left: auto;
}

.send-btn:hover {
  background-color: #3a5a8a;
  transform: translateY(-2px);
}

/* 文件卡片样式 */
.file-card {
  background-color: white;
  border-radius: 0.8rem;
  padding: 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  border: 1px solid #e1e4e8;
  max-width: 300px;
}

.file-icon {
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 0.5rem;
}

.file-info {
  margin-bottom: 0.5rem;
}

.file-actions {
  display: flex;
  justify-content: flex-end;
}

.download-btn {
  display: flex;
  align-items: center;
  gap: 0.3rem;
  background-color: #4a6fa5;
  color: white;
  text-align: center;
  padding: 0.4rem 0.8rem;
  border-radius: 0.5rem;
  text-decoration: none;
  font-size: 0.85rem;
  transition: all 0.2s ease;
}

.download-btn:hover {
  background-color: #3a5a8a;
}

/* 图片预览样式 */
.image-preview {
  max-width: 100%;
  max-height: 200px;
  border-radius: 0.5rem;
  margin: 0.5rem 0;
  cursor: pointer;
  border: 1px solid #e1e4e8;
}

.typing-indicator {
  display: inline-block;
  padding-left: 0.5rem;
}

.typing-indicator span {
  display: inline-block;
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 50%;
  background-color: #9ca3af;
  margin-right: 0.2rem;
  animation: typing 1s infinite ease-in-out;
}

.typing-indicator span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-indicator span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%, 100% {
    transform: translateY(0);
    opacity: 0.6;
  }
  50% {
    transform: translateY(-0.2rem);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .message {
    max-width: 90%;
  }

  .file-card {
    max-width: 100%;
  }

  .drop-zone {
    padding: 1.5rem;
  }

  .favorite-icon {
    font-size: 36px; /* 控制图标大小 */
  }

  .favorite-button {
    font-size: 36px;
  }

}


</style>