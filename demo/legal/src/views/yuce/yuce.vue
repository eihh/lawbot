<template>
  <div class="app-container">

    <!-- 主体内容区域 -->
    <main class="main-content">

      <!-- 问答容器 -->
      <div class="ai-qa-container">
        <!-- 问答历史区域 -->
        <div class="qa-history">
          <div v-for="(item, index) in history" :key="index" class="qa-item">
            <!-- 用户问题（右侧） -->
            <div v-if="item.question !== ''" class="user-question">
              <div class="question-header">
                <span class="user-icon">
                  <svg class="icon" viewBox="0 0 1024 1024" width="16" height="16">
                    <path d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z" fill="#3B82F6"></path>
                    <path d="M512 256c70.7 0 128 57.3 128 128s-57.3 128-128 128-128-57.3-128-128 57.3-128 128-128z m192 320c0 35.3-28.7 64-64 64H384c-35.3 0-64-28.7-64-64v-64c0-70.7 57.3-128 128-128h128c70.7 0 128 57.3 128 128v64z" fill="#FFFFFF"></path>
                  </svg>
                  我的提问
                </span>
                <span class="time">{{ item.time }}</span>
              </div>
              <div class="question-content" >
                <vue-markdown>
                  {{ item.question }}
                </vue-markdown>

              </div>
              <div class="corner-decoration"></div>
            </div>
            <!-- AI回答（左侧） -->
            <div class="ai-answer">
              <div class="answer-header">
                <span class="ai-icon">
                  <svg class="icon" viewBox="0 0 1024 1024" width="16" height="16">
                    <path d="M512 512m-448 0a448 448 0 1 0 896 0 448 448 0 1 0-896 0Z" fill="#10B981"></path>
                    <path d="M512 384c35.3 0 64 28.7 64 64s-28.7 64-64 64-64-28.7-64-64 28.7-64 64-64z m0 320c53 0 96-43 96-96h-192c0 53 43 96 96 96z" fill="#10B981"></path>
                  </svg>
                  AI助理
                </span>
                <div class="actions">

                  <button class="export-btn" @click="exportSingleAnswer(item)">
                    <svg class="icon" viewBox="0 0 1024 1024" width="14" height="14">
                      <path d="M832 832H192V192h320V64H192C121.6 64 64 121.6 64 192v640c0 70.4 57.6 128 128 128h640c70.4 0 128-57.6 128-128V512h-128v320z" fill="#FFFFFF"></path>
                      <path d="M576 64v256h256L576 64z" fill="#FFFFFF"></path>
                    </svg>
                    导出PDF
                  </button>


                </div>
              </div>
              <div class="answer-content">
                <div v-html="item.answer"></div>
                <div class="keywords" v-if="item.keywords && item.keywords.length">
                  <span class="keyword-tag" v-for="(word, wIndex) in item.keywords" :key="wIndex">
                    #{{ word }}
                  </span>
                </div>
              </div>
              <div class="corner-decoration"></div>
            </div>
          </div>
        </div>

        <!-- 输入区域 -->
        <div class="input-area">
          <div class="input-wrapper">
            <textarea
              v-model="inputQuestion"
              placeholder="输入您的问题..."
              @keydown.enter.exact.prevent="submitQuestion"
            ></textarea>
            <div class="input-decoration"></div>
          </div>

          <div class="upload-wrapper">
            <el-upload
              class="upload-demo"
              :headers="uploadHeaders"
              drag
              action="http://localhost:9090/yuce/upload"
              auto-upload
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
            >
              <div class="upload-content">
                <svg class="upload-icon" viewBox="0 0 1024 1024" width="48" height="48">
                  <path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zm0 820c-205.4 0-372-166.6-372-372s166.6-372 372-372 372 166.6 372 372-166.6 372-372 372z" fill="#3B82F6"></path>
                  <path d="M518.3 360.3a8 8 0 0 0-12.6 0l-112 141.7c-4.2 5.3-0.4 13 6.3 13h73.9V656c0 4.4 3.6 8 8 8h60c4.4 0 8-3.6 8-8V515h74.9c6.7 0 10.5-7.7 6.3-13l-112-141.7z" fill="#3B82F6"></path>
                </svg>
                <div class="el-upload__text">
                  拖拽文件到此处或<em>点击上传</em>提取文字
                </div>
              </div>
            </el-upload>
          </div>

          <button
            class="submit-btn"
            @click="submitQuestion"
            :disabled="isLoading"
            style="color: black;background-color: antiquewhite;"
          >
            <template v-if="!isLoading" >
              <svg class="send-icon" viewBox="0 0 1024 1024" width="18" height="18">
                <path d="M931.4 498.9L94.9 79.5c-3.4-1.7-7.3-2.1-11-1.2-8.5 2.1-13.8 10.7-11.7 19.3l86.2 352.2c1.3 5.3 5.2 9.6 10.4 11.3l147.7 50.7-147.6 50.7c-5.2 1.8-9.1 6-10.3 11.3L72.2 926.5c-0.9 3.7-0.5 7.6 1.2 10.9 3.9 7.9 13.5 11.1 21.5 7.2l836.5-417c3.1-1.5 5.6-4.1 7.2-7.1 3.9-8 0.7-17.6-7.2-21.6zM170.8 826.3l50.3-205.6 295.2-101.3c2.3-0.8 4.2-2.6 5-5 1.4-4.2-0.8-8.7-5-10.2L221.1 403 171 198.2l628 314.9-628.2 313.2z" fill="#000000"/>
              </svg>
              发送问题
            </template>
            <span v-else class="loading-text">
              <span class="dot-animation">
                <span class="dot">.</span><span class="dot">.</span><span class="dot">.</span>
              </span>
            </span>
          </button>
        </div>
      </div>
    </main>

    <!-- 新增的页脚 -->
    <footer class="app-footer" style="color: blue;">

    </footer>
  </div>
</template>

<script>
import request from "@/axios/request";
import jsPDF from "jspdf";
import VueMarkdown from "vue-markdown";

export default {
  name: "AbA",
  components: {
    // eslint-disable-next-line vue/no-unused-components
    'vue-markdown': VueMarkdown
  },
  data() {
    return {
      uploadHeaders: {
        jwtToken: localStorage.getItem("jwtToken")
      },
      inputQuestion: "",
      history: [],
      isLoading: false,

      // 模拟数据
      mockResponse: {
        answer:
          "Vue.js 是一个渐进式框架，建议学习路线：\n1. 基础语法\n2. 组件开发\n3. Vue Router\n4. 状态管理",
        keywords: ["Vue", "学习路线", "前端"],
        timestamp: new Date().toLocaleString(),
      },
    };
  },

  async created() {

    this.history = [
      {
      question: "",
      time: new Date().toLocaleTimeString(),
      answer: "正在挂载模型,请稍后........",
      keywords: [],
      timestamp: "",
      }
    ]

    //请求挂载模型
    const reload_response = await request.post("/reload", {content: "predict"})
    console.log("reload")
    console.log(reload_response)

    this.history = [
      {
        question: "",
        time: new Date().toLocaleTimeString(),
        answer: "请上传法律文书或者输入相关信息进行法律预测",
        keywords: [],
        timestamp: "",
      }
    ]

  },

  computed: {
    totalFileSize() {
      const totalBytes = this.files.reduce((sum, file) => sum + file.size, 0);
      return this.formatFileSize(totalBytes);
    },
  },

  methods: {
    //发送信息
    async submitQuestion() {
      if (!this.inputQuestion.trim() || this.isLoading) return;
      this.isLoading = true;

      //格式化回答
      function formattedPrediction(msg) {
        const parsed = JSON.parse(msg);
        return parsed.prediction.replace(/\n/g, "<br/>");
      }

      // 添加用户问题
      const newQ = {
        question: this.inputQuestion,
        time: new Date().toLocaleTimeString(),
        answer: "",
        keywords: [],
        timestamp: "",
      };

      try {

        // this.history.push( {
        //   question: this.inputQuestion,
        //   time: new Date().toLocaleTimeString(),
        //   answer: "123456inputQuestioninputQuestioninputQuestioninputQuestioninputQuestioninputQuestioninputQuestioninputQuestioninputQuestion",
        //   keywords: [],
        //   timestamp: "",
        // });





        // 向后端发送请求



        const response = await request.post("/yuce/send", this.inputQuestion);
        console.log(response);

        if (response.code == 200) {
          const newQ = {
            question: this.inputQuestion,
            time: new Date().toLocaleTimeString(),
            answer: formattedPrediction(response.msg),
            //answer: response.msg,
            keywords: [],
            timestamp: "",
          };


          this.history.push(newQ);



        }
      } finally {
        this.isLoading = false;
        this.inputQuestion = "";
      }
    },

    exportSingleAnswer(item) {
      const doc = new jsPDF();

      doc.setFont("msyh"); // 微软雅黑或其他支持中文的字体

      // 设置文档样式
      doc.setFontSize(18);
      doc.setTextColor(59, 130, 246);
      doc.text("AI问答报告", 20, 20);

      // 问题部分
      doc.setFontSize(12);
      doc.setTextColor(100);
      doc.text("问题：", 20, 35);
      doc.setFontSize(14);
      doc.setTextColor(0);
      doc.text(item.question, 40, 35);

      // 回答内容
      doc.setFontSize(12);
      doc.setTextColor(100);
      doc.text("回答：", 20, 50);
      doc.setFontSize(14);
      doc.setTextColor(0);
      const splitText = doc.splitTextToSize(item.answer, 160);
      doc.text(splitText, 40, 50);

      // 添加装饰线
      doc.setDrawColor(200);
      doc.setLineWidth(0.5);
      doc.line(20, 25, 60, 25);

      // 元信息
      doc.setFontSize(10);
      doc.setTextColor(100);
      doc.text(`生成时间：${item.timestamp}`, 20, 140);
      if (item.keywords && item.keywords.length) {
        doc.text(`关键词：${item.keywords.join(", ")}`, 20, 145);
      }

      // 添加页脚
      doc.setFontSize(8);
      doc.text("AI问答系统生成", 180, 290, null, null, "right");

      doc.save(`AI问答报告_${new Date().getTime()}.pdf`);
    },

    // 处理文件上传成功的事件
    handleUploadSuccess(response, files, fileList) {
      console.log("文件上传成功，后端响应:", response);
      if (response.code == 200) {
        this.inputQuestion = response.msg;
        this.$message({
          type: "success",
          message: "文件上传成功，文字已提取！",
          customClass: "upload-message",
        });
      } else {
        this.$message({
          type: "error",
          message: response.msg,
          customClass: "upload-message",
        });
      }
    },

    // 处理文件上传失败的事件
    handleUploadError(err, file, fileList) {
      console.error("文件上传失败:", err);
      this.$message.error({
        message: "文件上传失败，请重试！",
        customClass: "upload-message",
      });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300;400;500;700&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap");

:root {
  --primary-color: #3b82f6;
  --primary-light: #93c5fd;
  --primary-dark: #1d4ed8;
  --success-color: #10b981;
  --success-light: #6ee7b7;
  --warning-color: #f59e0b;
  --error-color: #ef4444;
  --text-color: #1e293b;
  --text-light: #64748b;
  --bg-color: #f8fafc;
  --card-bg: #c6f8fc;
  --header-bg: rgba(255, 255, 255, 0.9);
  --footer-bg: #1e293b;
  --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06);
  --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1),
    0 4px 6px -2px rgba(0, 0, 0, 0.05);
  --shadow-xl: 0 20px 25px -5px rgba(0, 0, 0, 0.1),
    0 10px 10px -5px rgba(0, 0, 0, 0.04);
  --radius-sm: 0.375rem;
  --radius-md: 0.5rem;
  --radius-lg: 0.75rem;
  --radius-xl: 1rem;
  --transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  --gradient-primary: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  --gradient-success: linear-gradient(135deg, var(--success-color) 0%, #059669 100%);
}

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: "Noto Sans SC", "Poppins", -apple-system, BlinkMacSystemFont, "Segoe UI",
    Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
  line-height: 1.6;
  color: var(--text-color);
  background-color: var(--bg-color);
  height: 80vh;
}

.app-container {
  height: 92vh;
  background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 50%, #f8fafc 100%);
  display: flex;
  flex-direction: column;
}

/* 头部样式 */
.app-header {
  background: var(--header-bg);
  backdrop-filter: blur(10px);
  box-shadow: var(--shadow-sm);
  position: sticky;
  top: 0;
  z-index: 100;
  border-bottom: 1px solid rgba(203, 213, 225, 0.3);
  height: 100px;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--primary-dark);
}

.logo-icon {
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.nav-links {
  display: flex;
  gap: 2rem;
}

.nav-link {
  color: var(--text-light);
  text-decoration: none;
  font-weight: 500;
  transition: var(--transition);
  position: relative;
  padding: 0.5rem 0;
}

.nav-link:hover {
  color: var(--primary-color);
}

.nav-link.active {
  color: var(--primary-color);
  font-weight: 600;
}

.nav-link.active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: var(--primary-color);
  border-radius: 2px;
  animation: underline 0.3s ease-out;
}

@keyframes underline {
  from {
    transform: scaleX(0);
  }
  to {
    transform: scaleX(1);
  }
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.upgrade-btn {
  background: linear-gradient(135deg, var(--warning-color) 0%, #d97706 100%);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: var(--radius-md);
  font-weight: 500;
  transition: var(--transition);
  border: none;
  cursor: pointer;
  box-shadow: var(--shadow-sm);
}

.upgrade-btn:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.user-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: var(--primary-light);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: var(--transition);
}

.user-avatar:hover {
  transform: scale(1.1);
}

/* 主体内容样式 */
.main-content {
  flex: 1;
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  width: 100%;
  height: 100%;
}





.welcome-content h1 {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: var(--primary-dark);
  background: linear-gradient(90deg, var(--primary-color), var(--success-color));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  display: inline-block;
}

.welcome-content p {
  color: var(--text-light);
  margin-bottom: 1.5rem;
  max-width: 600px;
}

.welcome-tips {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.tip-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: rgba(255, 255, 255, 0.8);
  padding: 0.75rem 1.25rem;
  border-radius: var(--radius-lg);
  font-size: 0.9rem;
  font-weight: 500;
  box-shadow: var(--shadow-sm);
  transition: var(--transition);
}

.tip-item:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-md);
}

.welcome-decoration {
  position: relative;
  width: 200px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.1;
  animation: pulse 4s infinite ease-in-out;
}

.decoration-circle:nth-child(1) {
  width: 150px;
  height: 150px;
  background: var(--primary-color);
  animation-delay: 0s;
}

.decoration-circle:nth-child(2) {
  width: 120px;
  height: 120px;
  background: var(--success-color);
  animation-delay: 0.5s;
}

.decoration-circle:nth-child(3) {
  width: 90px;
  height: 90px;
  background: var(--warning-color);
  animation-delay: 1s;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.1;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.2;
  }
}

/* 问答容器样式 */
.ai-qa-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  height: 100%;
  max-height: 100vh;      /* 限制高度，最大为视口高度的100% */
  overflow-y: auto;      /* 添加垂直滚动条 */

}

.qa-history {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-lg);
  border: 1px solid rgba(255, 255, 255, 0.3);
  scrollbar-width: thin;
  scrollbar-color: var(--primary-light) transparent;
  color: black !important;
}

.qa-history::-webkit-scrollbar {
  width: 8px;
}

.qa-history::-webkit-scrollbar-track {
  background: transparent;
}

.qa-history::-webkit-scrollbar-thumb {
  background-color: var(--primary-light);
  border-radius: 4px;
}

.qa-item {
  display: flex;
  flex-direction: column;
  margin-bottom: 2rem;
  position: relative;
}

.ai-answer {
  align-self: flex-start;
  background: var(--card-bg);
  width: 80%;

  color: #282727;

  padding: 1.5rem;
  margin: 0.5rem 0;
  box-shadow: var(--shadow-md);
  position: relative;

  transition: var(--transition);
  animation: fadeIn 0.5s ease-out;

  word-wrap: break-word;  /* 确保长单词或链接能够自动换行 */
  white-space: normal;    /* 确保正常换行 */

  border: 2px solid #3B82F6;
  border-radius: 20px 20px 20px 20px; /* 上左，右上，下右，左下 */



}

.ai-answer:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.ai-answer::before {
  content: "";
  position: absolute;
  left: -10px;
  top: 20px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: var(--card-bg);
  filter: drop-shadow(-2px 0 2px rgba(0, 0, 0, 0.1));
  z-index: 1;
}

.user-question {
  align-self: flex-end;
  background: linear-gradient(
    135deg,
    var(--primary-color) 0%,
    var(--primary-dark) 100%
  );
  color: #282727;
  width: 80%;

  padding: 1.5rem;
  margin: 0.5rem 0;
  position: relative;
  box-shadow: var(--shadow-md);
  transition: var(--transition);
  animation: fadeIn 0.5s ease-out;


  word-wrap: break-word;  /* 确保长单词或链接能够自动换行 */
  white-space: normal;    /* 确保正常换行 */

  /* 添加边框 */
  border: 2px solid #332e2e;  /* 你可以根据需要调整颜色和宽度 */
  border-radius: 20px 20px 20px 20px; /* 上左，右上，下右，左下 */

}

.user-question:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.user-question::before {
  content: "";
  position: absolute;
  right: -10px;
  top: 20px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-left-color: var(--primary-color);
  filter: drop-shadow(2px 0 2px rgba(0, 0, 0, 0.1));
  z-index: 1;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.corner-decoration {
  position: absolute;
  width: 20px;
  height: 20px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 0 0 var(--radius-sm) 0;
  top: 0;
  left: 0;
}

.user-question .corner-decoration {
  left: auto;
  right: 0;
  border-radius: 0 0 0 var(--radius-sm);
}

.question-header,
.answer-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  font-size: 0.9em;
}

.user-icon,
.ai-icon {
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.icon {
  margin-right: 0.5rem;
}

.time {
  opacity: 0.8;
  font-size: 0.85em;
  color: rgba(197, 15, 15, 0.8);
}

.ai-answer .time {
  color: var(--text-light);
}

.answer-content {
  line-height: 1.7;
  color: var(--text-color);
}

.question-content {
  line-height: 1.7;
}

.keywords {
  margin-top: 1.5rem;
  padding-top: 1rem;
  border-top: 1px dashed rgba(203, 213, 225, 0.5);
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.keyword-tag {
  display: inline-block;
  background: linear-gradient(135deg, #e0f2fe 0%, #bae6fd 100%);
  color: #0369a1;
  padding: 0.5rem 1rem;
  border-radius: 1rem;
  font-size: 0.85em;
  font-weight: 500;
  box-shadow: var(--shadow-sm);
  transition: var(--transition);
}

.keyword-tag:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.input-area {
  display: flex;
  gap: 1.5rem;
  align-items: flex-end;
  position: relative;
}

.input-wrapper {
  flex: 1;
  position: relative;
}

.input-decoration {
  position: absolute;
  bottom: -5px;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(
    90deg,
    var(--primary-color),
    var(--success-color)
  );
  border-radius: 2px;
  transform: scaleX(0);
  transform-origin: left;
  transition: var(--transition);
}

.input-wrapper:focus-within .input-decoration {
  transform: scaleX(1);
}

textarea {
  width: 100%;
  min-height: 120px;
  padding: 1.25rem;
  border: 1px solid rgba(203, 213, 225, 0.5);
  border-radius: var(--radius-lg);
  resize: none;
  font-size: 1rem;
  transition: var(--transition);
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(5px);
  box-shadow: var(--shadow-sm);
  font-family: inherit;
}

textarea:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

.upload-wrapper {
  flex: 1;
}

.upload-demo {
  height: 100%;
}

.upload-demo :deep(.el-upload) {
  width: 100%;
  height: 100%;
}

.upload-demo :deep(.el-upload-dragger) {
  width: 100%;
  height: 120px;
  padding: 1.5rem;
  border-radius: var(--radius-lg);
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(5px);
  border: 1px dashed var(--primary-light);
  transition: var(--transition);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.upload-demo :deep(.el-upload-dragger:hover) {
  border-color: var(--primary-color);
  background: rgba(255, 255, 255, 0.9);
}

.upload-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.upload-icon {
  margin-bottom: 0.5rem;
  animation: bounce 2s infinite;
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.el-upload__text {
  font-size: 0.9rem;
  color: var(--text-light);
  text-align: center;
}

.el-upload__text em {
  color: var(--primary-color);
  font-style: normal;
  font-weight: 500;
}

.submit-btn {
  background: linear-gradient(
    135deg,
    var(--primary-color) 0%,
    var(--primary-dark) 100%
  );
  color: white;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: var(--radius-lg);
  cursor: pointer;
  font-weight: 500;
  transition: var(--transition);
  box-shadow: var(--shadow-md);
  display: flex;
  align-items: center;
  gap: 0.5rem;
  height: fit-content;
  min-width: 120px;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.submit-btn::before {
  content: "";
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: linear-gradient(
    to bottom right,
    rgba(255, 255, 255, 0.3),
    rgba(255, 255, 255, 0)
  );
  transform: rotate(30deg);
  transition: var(--transition);
}

.submit-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.submit-btn:hover:not(:disabled)::before {
  left: 100%;
}

.submit-btn:disabled {
  background: linear-gradient(135deg, #94a3b8 0%, #64748b 100%);
  cursor: not-allowed;
}

.send-icon {
  transition: var(--transition);
}

.submit-btn:hover:not(:disabled) .send-icon {
  transform: translateX(2px);
}

.export-btn {
  color: #1e293b;
  background: linear-gradient(135deg, var(--success-color) 0%, #059669 100%);

  padding: 0.5rem 1rem;
  border-radius: var(--radius-sm);
  font-size: 0.85em;
  transition: var(--transition);
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  box-shadow: var(--shadow-sm);
}

.export-btn:hover {
  color: rgba(26, 168, 211, 0.7);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.loading-text {
  display: flex;
  align-items: center;
}

.dot-animation {
  display: inline-flex;
}

.dot {
  animation: dot-pulse 1.5s infinite ease-in-out;
  opacity: 0;
}

.dot:nth-child(1) {
  animation-delay: 0.1s;
}

.dot:nth-child(2) {
  animation-delay: 0.3s;
}

.dot:nth-child(3) {
  animation-delay: 0.5s;
}

@keyframes dot-pulse {
  0%,
  100% {
    opacity: 0.2;
  }
  50% {
    opacity: 1;
  }
}

/* 页脚样式 */
.app-footer {
  background: var(--footer-bg);
  color: white;
  padding: 2rem 0;
  margin-top: auto;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.footer-links {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  justify-content: center;
}

.footer-link {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: var(--transition);
  position: relative;
}

.footer-link:hover {
  color: white;
}

.footer-link::after {
  content: "";
  position: absolute;
  bottom: -2px;
  left: 0;
  right: 0;
  height: 1px;
  background: white;
  transform: scaleX(0);
  transition: var(--transition);
}

.footer-link:hover::after {
  transform: scaleX(1);
}

.footer-copyright {
  text-align: center;
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.9rem;
}

.footer-social {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.social-icon {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: var(--transition);
}

.social-icon:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
}

@media (max-width: 768px) {
  .header-content {
    padding: 1rem;
    flex-direction: column;
    gap: 1rem;
  }
  
  .nav-links {
    gap: 1rem;
  }
  
  .user-actions {
    gap: 1rem;
  }
  
  .main-content {
    padding: 1rem;
    height: 100%;
  }
  

  

  

  
  .input-area {
    flex-direction: column;
    gap: 1rem;
    height: 20%;
  }
  
  .ai-answer,
  .user-question {
    width: 90%;
  }
  
  .ai-answer::before,
  .user-question::before {
    display: none;
  }
  
  .qa-history {
    padding: 1rem;
  }
  
  .footer-links {
    gap: 1rem;
  }
}

/* 自定义消息通知样式 */
:deep(.upload-message) {
  border-radius: var(--radius-lg) !important;
  box-shadow: var(--shadow-lg) !important;
  backdrop-filter: blur(5px) !important;
  background: rgba(255, 255, 255, 0.9) !important;
  border: 1px solid rgba(255, 255, 255, 0.3) !important;
}
</style>