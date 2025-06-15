<template>
  <div style="width: 100%; height: 50vh">
    <div
      class="container mx-auto px-4 py-8"
      style="
        margin-left: 50px;
        width: 100%;
        height: 80vh;
        overflow: auto;
        margin: 0 auto;
      "
    >
      <h1 class="text-3xl font-bold text-gray-800 mb-6 text-center">
        收藏列表
      </h1>

      <div
        v-for="item in displayedItems"
        :key="item.id"
        class="bg-white rounded-xl border border-gray-200 shadow-md p-6 mb-6 transform transition-all duration-300 hover:shadow-xl hover:border-blue-300 hover:-translate-y-1"
        style="margin-top: 30px; padding: 10px; cursor: pointer; border-top: 3px solid #282727;border-bottom: 3px solid #282727; border-left: 3px solid #282727; "



      >
        <div class="flex items-center mb-4">
          <div
            class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-500 mr-3"
          >
            <i class="fa fa-file-text-o"></i>
          </div>
          <h2 class="text-xl font-semibold text-gray-900 mb-0">
            {{ item.title }}
          </h2>
        </div>
        <p class="text-gray-600 mb-4 leading-relaxed line-clamp-3">

          <vue-markdown>
            {{ item.content }}
          </vue-markdown>

        </p>
        <div class="flex justify-between items-center">


          <el-button type="primary" @click="showFullContent(item)" plain>阅读更多</el-button>

          <el-button type="danger" @click="handleDelete(item)" >删除 <i class="el-icon-delete"></i></el-button>
        </div>

      </div>

      <div v-if="items.length > 3" class="mt-8 text-center">
        <button
          class="bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-medium py-2.5 px-8 rounded-lg transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5 focus:ring-2 focus:ring-blue-400 focus:ring-opacity-50"
          @click="loadMore"
        >
          <i class="fa fa-plus-circle mr-2"></i>加载更多文章
        </button>
      </div>

      <!-- 使用el-dialog替换原有弹窗 -->
      <el-dialog
        :title="currentItem ? currentItem.title : ''"
        :visible.sync="showModal"
        width="40%"
        top="5vh"
        custom-class="article-dialog"
      >
        <div style="width: 100%; text-align: center; font-size: 30px">
          标题：{{ currentItem ? currentItem.title : "" }}
        </div>
        <div class="prose max-w-none mb-8">
          <p class="text-gray-700 whitespace-pre-line">

            <vue-markdown>
              {{ currentItem ? currentItem.content : "" }}
            </vue-markdown>


          </p>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="printToPDF(currentItem)" type="primary">
            <i class="fa fa-file-pdf-o mr-2"></i>打印为PDF
          </el-button>
          <el-button @click="closeModal">关闭</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
  
  <script>
import request from "@/axios/request";
import VueMarkdown from "vue-markdown";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";

export default {
  name: "GGGhhhHH",
  components: {
    'vue-markdown': VueMarkdown
  },
  data() {
    return {
      items: [

      ],
      displayCount: 10,
      showModal: false,
      currentItem: null,
      temId:2,

    };
  },

  async created() {
    let username = localStorage.getItem("username")
    const response = await request.get("/collection/list", {
      params: {username: username}
    });
    console.log(response)
    let data = response.data
    for (let i = 0; i < data.length; i++) {

      this.items.push({
        id:this.temId++,
        title: data[i].title,
        content: data[i].content
      })

    }



  },


  computed: {
    displayedItems() {
      return this.items.slice(0, this.displayCount);
    },
  },
  methods: {
    async handleDelete(item) {


      const response = await request.delete("/collection/delete", {
        params: {title: item.title, username: localStorage.getItem("username"),}
      })

      console.log(response)

      location.reload();









    },

    showFullContent(item) {
      this.currentItem = item;
      this.showModal = true;
    },
    showDetail(item) {
      this.currentItem = item;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    loadMore() {
      this.displayCount = Math.min(this.displayCount + 3, this.items.length);
    },
    async printToPDF(message) {
      // 创建一个新的窗口或iframe来打印

      // 创建一个隐藏容器渲染 HTML
      const container = document.createElement('div');
      container.style.position = 'fixed';
      container.style.left = '-9999px';
      container.style.width = '800px';  // 宽度尽量大一点，提升分辨率
      container.style.fontFamily = '宋体, SimSun, serif';
      container.innerHTML = message.content;
      document.body.appendChild(container);

      // 转换为高分辨率 canvas
      const canvas = await html2canvas(container, {scale: 2});
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
  },
};
</script>
  
  <style scoped>
.container {
  max-width: 1200px;
}

/* 添加平滑过渡效果 */
.container {
  animation: fadeIn 0.6s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 优化阅读体验的排版 */
h1 {
  letter-spacing: -0.025em;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

h2 {
  letter-spacing: -0.015em;
}

p {
  line-height: 1.625;
}

/* 自定义按钮悬停效果 */
button {
  outline: none;
}

button:focus {
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.5);
}

/* 列表项动画 */
div[class*="bg-white rounded-xl"] {
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

/* 三行文本限制样式 */
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 自定义el-dialog样式 */
.article-dialog {
  border-radius: 12px;
}

.article-dialog .el-dialog__body {
  padding: 20px 25px;
  color: #606266;
}

.article-dialog .el-dialog__header {
  border-bottom: 1px solid #e8e8e8;
  padding: 15px 20px;
  text-align: center;
}

.article-dialog .el-dialog__title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #303133;
}

.article-dialog .el-dialog__footer {
  padding: 15px 20px;
  text-align: center;
  border-top: 1px solid #e8e8e8;
}
</style>