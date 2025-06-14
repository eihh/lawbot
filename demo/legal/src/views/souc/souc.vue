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
        @click="showDetail(item)"


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
          <el-button @click="printToPDF" type="primary">
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

export default {
  name: "GGGhhhHH",
  components: {
    'vue-markdown': VueMarkdown
  },
  data() {
    return {
      items: [
        {
          id: 1,
          title: "JavaScript异步编程",
          content:
            "JavaScript的Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。异步特性是其强大之处之一，但也常常是开发者困惑的来源。Promise、async/await等特性使得异步代码更加清晰易读。理解事件循环机制对于掌握JavaScript异步编程至关重要。当处理多个异步操作时，我们可以使用Promise.all来并行执行多个Promise，或者使用Promise.race来处理竞争条件。合理使用这些技术可以大大提高应用程序的性能和用户体验。",
        },

      ],
      displayCount: 10,
      showModal: false,
      currentItem: null,
    };
  },

  async created() {
    let username = localStorage.getItem("username")
    const response = await request.get("/collection/list", {
      params: {username: username}
    });
    console.log(response)
    this.items = response.data;

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

      let username = localStorage.getItem("username")
      const response2 = await request.get("/collection/list", {
        params: {username: username}
      });
      console.log(response2)
      this.items = response.data;



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
    printToPDF() {
      // 创建一个新的窗口或iframe来打印
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