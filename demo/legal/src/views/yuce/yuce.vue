<template>
  <div style="width: 100%">
    <div style="width: 90%; margin: 0 auto">
      <div class="ai-qa-container">
        <!-- 问答历史区域 -->
        <div class="qa-history">
          <div v-for="(item, index) in history" :key="index" class="qa-item">

            <!-- 用户问题（右侧） -->
            <div class="user-question">
              <div class="question-header">
                <span class="user-icon">👤 我的提问</span>
                <span class="time">{{ item.time }}</span>
              </div>

              <div class="question-content" v-html="item.question"></div>
            </div>
            <!-- AI回答（左侧） -->
            <div class="ai-answer">
              <div class="answer-header">
                <span class="ai-icon">🤖 AI助理</span>
                <div class="actions">
                  <button class="export-btn" @click="exportSingleAnswer(item)">
                    📄 导出PDF
                  </button>
                </div>
              </div>
              <div class="answer-content">
                <div  v-html="item.answer"></div>

                <div class="keywords">
                  <span
                      class="keyword-tag"
                      v-for="(word, wIndex) in item.keywords"
                      :key="wIndex"
                  >
                    #{{ word }}
                  </span>
                </div>
              </div>
            </div>

          </div>
        </div>

        <!-- 输入区域 -->

        <div class="input-area">
          <textarea
              v-model="inputQuestion"
              placeholder="输入您的问题..."
              @keydown.enter.exact.prevent="submitQuestion">
          </textarea>

          <el-upload
              class="upload-demo"
              drag
              action="http://localhost:9090/yuce/upload"
              auto-upload
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em>,提取文字</div>
          </el-upload>

          <button
              class="submit-btn"
              @click="submitQuestion"
              :disabled="isLoading"
          >
            {{ isLoading ? "思考中..." : "发送问题" }}
          </button>
        </div>


      </div>
    </div>
  </div>
</template>

<script>

import request from "@/axios/request";
import jsPDF from "jspdf";



export default {
  name: "AbA",
  data() {
    return {
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
        return parsed.prediction.replace(/\n/g, '<br/>');
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
        // 向后端发送请求
        const response = await request.post('/yuce/send', this.inputQuestion);
        console.log(response)



        if (response.code == 200) {
          const newQ = {
            question: this.inputQuestion,
            time: new Date().toLocaleTimeString(),
            answer: formattedPrediction(response.msg),
            keywords: [],
            timestamp: "",
          };
          this.history.push(newQ);
        }


        // 更新回答数据
        /*const answerItem = {
          ...this.mockResponse,
          timestamp: new Date().toLocaleString(),
        };*/





        /*this.history = this.history.map((item) => {
          return item.question === newQ.question
              ? {...item, ...answerItem}
              : item;
        });*/
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
      doc.text("AI问答报告", 20, 20);

      // 问题部分
      doc.setFontSize(12);
      doc.setDrawColor(200);
      doc.rect(15, 30, 180, 10);
      doc.text("问题：", 20, 37);
      doc.setFontSize(14);
      doc.text(item.question, 40, 37);

      // 回答内容
      doc.setFontSize(12);
      doc.text("回答：", 20, 50);
      const splitText = doc.splitTextToSize(item.answer, 160);
      doc.text(splitText, 40, 50);

      // 元信息
      doc.setFontSize(10);
      doc.setTextColor(100);
      doc.text(`生成时间：${item.timestamp}`, 20, 140);
      doc.text(`关键词：${item.keywords.join(", ")}`, 20, 145);


      doc.save(`AI问答报告_${new Date().getTime()}.pdf`);
    },


    // 处理文件上传成功的事件
    handleUploadSuccess(response, files, fileList) {
      console.log("文件上传成功，后端响应:", response);
      if (response.code == 200) {
        this.inputQuestion = response.msg
      } else {
        this.$message({type: 'error', message: response.msg})
      }


    },

    // 处理文件上传失败的事件
    handleUploadError(err, file, fileList) {
      console.error("文件上传失败:", err);
      this.$message.error("文件上传失败，请重试！");
    },


  },
};
</script>

<style scoped>
.ai-qa-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
  background: #f8fafc;
  min-height: 100vh;
}

.qa-history {
  margin-bottom: 30px;
  height: 70vh;
  overflow-y: auto;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.qa-item {
  display: flex;
  flex-direction: column;
  margin-bottom: 30px;
}

.ai-answer {
  align-self: flex-start;
  background: #ffffff;
  width: 75%;
  border-radius: 12px;
  padding: 20px;
  margin: 10px 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: relative;
  border: 1px solid #e2e8f0;
}

.ai-answer::after {
  content: "";
  position: absolute;
  left: -10px;
  top: 20px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: #ffffff;
}

.user-question {
  align-self: flex-end;
  background: #3b82f6;
  color: white;
  width: 75%;
  border-radius: 12px;
  padding: 20px;
  margin: 10px 0;
  position: relative;
}

.user-question::after {
  content: "";
  position: absolute;
  right: -10px;
  top: 20px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-left-color: #3b82f6;
}

.question-header,
.answer-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  font-size: 0.9em;
}

.user-icon,
.ai-icon {
  font-weight: 500;
}

.time {
  opacity: 0.8;
  font-size: 0.9em;
}

.answer-content {
  line-height: 1.7;
  color: #475569;
}

.keywords {
  margin-top: 15px;
  padding-top: 10px;
  border-top: 1px dashed #e2e8f0;
}

.keyword-tag {
  display: inline-block;
  background: #e0f2fe;
  color: #0ea5e9;
  padding: 6px 12px;
  margin: 5px;
  border-radius: 20px;
  font-size: 0.85em;
}


.input-area {
  position: relative;
  margin-top: 30px;
  width: 100%;
  display: flex;
  justify-content: space-between; /* 水平分布，元素之间的间距自动调整 */
  align-items: flex-end; /* 垂直对齐到底部 */
}

textarea {
  width: 56%; /* 设置textarea的宽度为容器的45% */
  height: 142px;
  padding: 20px;
  border: 2px solid #cbd5e1;
  border-radius: 12px;
  resize: none;
  font-size: 16px;
  transition: all 0.3s;
}

textarea:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.el-upload {
  width: 45%; /* 设置上传组件的宽度为容器的45% */
  display: flex;
  justify-content: center;
}

.submit-btn {
  background: #3b82f6;
  color: white;
  margin-bottom: 60px;
  padding: 12px 30px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s;
}

.submit-btn:disabled {
  background: #94a3b8;
  cursor: not-allowed;
}


.export-btn {
  background: #10b981;
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 0.9em;
  transition: all 0.3s;
}

.export-btn:hover {
  background: #059669;
}

@media (max-width: 768px) {
  .ai-answer,
  .user-question {
    width: 90%;
  }

  .ai-answer::after,
  .user-question::after {
    display: none;
  }

  .qa-history {
    padding: 10px;
  }
}


</style>